package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import pl.vm.academy.brewingbuddy.core.business.ingredient.service.IngredientFacade;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeCalculatedParameter;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeHop;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeMalt;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeMaltRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.utils.HopUtilisation;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
public class RecipeParametersCalculatorAdapter implements RecipeParametersCalculator{

    private final RecipeRepository recipeRepository;
    private final RecipeMaltRepository recipeMaltRepository;
    private final IngredientFacade ingredientFacade;
    private final HopUtilisation hopUtilisation;


    @Override
    public RecipeCalculatedParametersDto calculateParameters(RecipeDetailedDto recipeDetailedDto) {
        return null;
    }

    private void calculateOverallAmountOfMaltInKg (Recipe recipe, RecipeDetailedDto recipeDetailedDto) {
        BigDecimal overallAmountOfMalt = BigDecimal.valueOf(0);

        for (RecipeMaltDto recipeMaltDto : recipeDetailedDto.recipeMalts()) {
            overallAmountOfMalt.add(recipeMaltDto.maltAmountInKilos());
        }
        recipe.getRecipeCalculatedParameter().setOverallAmountOfMaltInKg(overallAmountOfMalt);
    }

    private void calculateWaterRequiredForMashingInLiters(Recipe recipe, RecipeDetailedDto recipeDetailedDto) {
        BigDecimal overallAmountOfMalt = recipe.getRecipeCalculatedParameter().getOverallAmountOfMaltInKg();

        recipe.getRecipeCalculatedParameter()
                .setWaterRequiredForMashingInLiters(overallAmountOfMalt.multiply(recipeDetailedDto.mashingFactorInLitersPerKg()));
    }

    private void calculateWaterRequiredForSpargingInLiters(Recipe recipe, RecipeDetailedDto recipeDetailedDto) {
        BigDecimal overallAmountOfMalt = recipe.getRecipeCalculatedParameter().getOverallAmountOfMaltInKg();

        BigDecimal hotWort =  recipe.getRecipeCalculatedParameter().getAmountOfHotWort();
        BigDecimal waterThatLeftAfterFiltrationInLiters = overallAmountOfMalt.multiply(BigDecimal.valueOf(0.7));
        BigDecimal waterLostDuringBoilingInLiter = hotWort.divide(BigDecimal.valueOf(0.85)).subtract(hotWort);
        BigDecimal waterForMashing = recipe.getRecipeCalculatedParameter().getWaterRequiredForMashingInLiters();

        BigDecimal waterForSparging = hotWort.add(waterThatLeftAfterFiltrationInLiters).add(waterLostDuringBoilingInLiter).subtract(waterForMashing);

        if (waterForSparging.compareTo(BigDecimal.valueOf(0)) <= 0) {
            recipe.getRecipeCalculatedParameter().setWaterRequiredForSpargingInLiters(BigDecimal.valueOf(0));
        } else {
            recipe.getRecipeCalculatedParameter().setWaterRequiredForSpargingInLiters(waterForSparging);
        }
    }

    private void calculateWaterRequiredForWholeProcessInLiters(Recipe recipe) {
        recipe.getRecipeCalculatedParameter().
                setWaterRequiredForWholeProcessInLiters(recipe.getRecipeCalculatedParameter().getWaterRequiredForSpargingInLiters().
                        add(recipe.getRecipeCalculatedParameter().getWaterRequiredForMashingInLiters()));
    }

    private void calculateAmountOfWaterBeforeBoilingInLiters(Recipe recipe, RecipeDetailedDto recipeDetailedDto) {
        BigDecimal overallAmountOfMalt = recipe.getRecipeCalculatedParameter().getOverallAmountOfMaltInKg();

        BigDecimal waterThatLeftAfterFiltrationInLiters = overallAmountOfMalt.multiply(BigDecimal.valueOf(0.7));

        recipe.getRecipeCalculatedParameter().setAmountOfWaterBeforeBoilingInLiters(
                recipe.getRecipeCalculatedParameter().getWaterRequiredForWholeProcessInLiters().subtract(waterThatLeftAfterFiltrationInLiters));
    }
    private void calculateTheoreticalExtract (Recipe recipe, RecipeDetailedDto recipeDetailedDto) {
        BigDecimal theoreticalExtractInGrams = BigDecimal.valueOf(0);

        if (!CollectionUtils.isEmpty(recipeDetailedDto.recipeMalts())) {
            for (RecipeMaltDto recipeMaltDto : recipeDetailedDto.recipeMalts()) {
                theoreticalExtractInGrams.add(recipeMaltDto.maltAmountInKilos().
                        multiply(ingredientFacade.getMaltById(recipeMaltDto.maltId()).extractionRateInPercentage().multiply(BigDecimal.valueOf(10))));
            }
        }
        recipe.getRecipeCalculatedParameter().setTheoreticalExtractInGrams(theoreticalExtractInGrams);
    }

    private void calculateRealExtract (Recipe recipe, RecipeDetailedDto recipeDetailedDto) {
        recipe.getRecipeCalculatedParameter().setRealExtractInGrams(recipe.getRecipeCalculatedParameter().getTheoreticalExtractInGrams().
                multiply(recipe.getMashingPerformanceInPercentage()));
    }

    private void calculateAmountOfHotWort(Recipe recipe, RecipeDetailedDto recipeDetailedDto) {
        RecipeCalculatedParameter calcParam = recipe.getRecipeCalculatedParameter();

        calcParam.setAmountOfHotWort(calcParam.getWaterRequiredForWholeProcessInLiters().
                subtract(calcParam.getOverallAmountOfMaltInKg().multiply(BigDecimal.valueOf(0.7))));
    }

    private void calculateWortWeightInGrams (Recipe recipe) {
        BigDecimal amountOfHotWort = recipe.getRecipeCalculatedParameter().getAmountOfHotWort();
        BigDecimal extractInMilliliters = recipe.getRecipeCalculatedParameter().getRealExtractInGrams().multiply(BigDecimal.valueOf(1.587));
        BigDecimal amountOfWaterInWortInMilliliters = amountOfHotWort.multiply(BigDecimal.valueOf(1000)).subtract(extractInMilliliters);

        recipe.getRecipeCalculatedParameter().setWortWeightInGrams(amountOfWaterInWortInMilliliters.add(recipe.getRecipeCalculatedParameter().getRealExtractInGrams()));
    }

    private void calculateExtractBeforeBoilingInPercentage(Recipe recipe, RecipeDetailedDto recipeDetailedDto) {
        BigDecimal extractBeforeBoilingInPercentage;
        BigDecimal amountOfHotWort = recipe.getRecipeCalculatedParameter().getAmountOfHotWort();
        BigDecimal wortWeightInGrams = recipe.getRecipeCalculatedParameter().getWortWeightInGrams();
        // 15%
        BigDecimal waterEvaporatedDuringMashing = amountOfHotWort.divide(BigDecimal.valueOf(0.85)).subtract(amountOfHotWort);

        extractBeforeBoilingInPercentage = recipe.getRecipeCalculatedParameter().getRealExtractInGrams()
                .divide(wortWeightInGrams.divide(BigDecimal.valueOf(1000)).add(waterEvaporatedDuringMashing))
                .divide(BigDecimal.valueOf(10));

        recipe.getRecipeCalculatedParameter().setExtractBeforeBoilingInPercentage(extractBeforeBoilingInPercentage);
    }


    private void calculateCalculatedIbu(Recipe recipe, RecipeDetailedDto recipeDetailedDto) {

        if (!CollectionUtils.isEmpty(recipe.getRecipeHops())) {

            BigDecimal ibu = BigDecimal.valueOf(0);

            for (RecipeHop recipeHop : recipe.getRecipeHops()) {
                ibu.add(recipeHop.getHopAmountInGrams()
                        .multiply(ingredientFacade.getHopById(recipeHop.getHopId()).alfaAcidInPercentage())
                        .multiply(hopUtilisation.getHopUtilisation(recipeHop.getBoilingTimeInMinutes().toMinutesPart()))
                        .divide(recipe.getRecipeCalculatedParameter().getAmountOfHotWort()));
            }
            recipe.getRecipeCalculatedParameter().setCalculatedIbu(ibu);
        }
    }

    private void calculateCalculatedColourEBC(Recipe recipe, RecipeDetailedDto recipeDetailedDto) {

        if (!CollectionUtils.isEmpty(recipe.getRecipeMalts())) {
            BigDecimal sumEbc = BigDecimal.valueOf(0);
            BigDecimal overallAmountOfMaltInKg = recipe.getRecipeCalculatedParameter().getOverallAmountOfMaltInKg();
            for (RecipeMalt recipeMalt : recipe.getRecipeMalts()) {
                sumEbc.add(overallAmountOfMaltInKg
                        .multiply(BigDecimal.valueOf(2.20462262184878))
                        .multiply(ingredientFacade.getMaltById(recipeMalt.getMaltId()).meanColorInEbcScale())
                        .divide(BigDecimal.valueOf(1.97)));
            }

            recipe.getRecipeCalculatedParameter().setCalculatedColourEBC(
                    BigDecimal.valueOf(Math.pow(sumEbc.divide(overallAmountOfMaltInKg.divide(BigDecimal.valueOf(3.78541178))).doubleValue(), 0.6859))
                            .multiply(BigDecimal.valueOf(1.4922))
                            .multiply(BigDecimal.valueOf(1.97)));
        }
    }

    private void calculateCalculatedExtractInPercentage(Recipe recipe, RecipeDetailedDto recipeDetailedDto) {
        BigDecimal realExtract = recipe.getRecipeCalculatedParameter().getRealExtractInGrams();
        BigDecimal wortWeight = recipe.getRecipeCalculatedParameter().getWortWeightInGrams();

        recipe.getRecipeCalculatedParameter().setCalculatedExtractInPercentage(realExtract.divide(wortWeight).divide(BigDecimal.valueOf(100)));
    }

    private void calculateEstimatedAmountOfAlcoholAfterFermentation(Recipe recipe, RecipeDetailedDto recipeDetailedDto) {
        BigDecimal alcoholInLiters = recipe.getRecipeCalculatedParameter().getRealExtractInGrams()
                .divide(BigDecimal.valueOf(1000))
                .multiply(BigDecimal.valueOf(0.6));

        recipe.getRecipeCalculatedParameter().setEstimatedAmountOfAlcoholAfterFermentation(
                alcoholInLiters
                        .divide(recipe.getExpectedAmountOfBeerInLiters())
                        .multiply(BigDecimal.valueOf(100)));
    }
}
