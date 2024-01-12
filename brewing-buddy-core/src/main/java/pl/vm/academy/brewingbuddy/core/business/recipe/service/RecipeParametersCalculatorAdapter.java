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
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
public class RecipeParametersCalculatorAdapter implements RecipeParametersCalculator{
    private final IngredientFacade ingredientFacade;
    private final HopUtilisation hopUtilisation;


    @Override
    public Recipe calculateParameters(Recipe recipe) {

        if (!requiredParametersWereSet(recipe)) {
            return recipe;
        }

        calculateOverallAmountOfMaltInKg (recipe);

        calculateAmountOfHotWort(recipe);

        calculateWaterRequiredForMashingInLiters(recipe);

        calculateWaterRequiredForSpargingInLiters(recipe);

        calculateWaterRequiredForWholeProcessInLiters(recipe);

        calculateAmountOfWaterBeforeBoilingInLiters(recipe);

        calculateTheoreticalExtract(recipe);

        calculateRealExtract(recipe);

        calculateWortWeightInGrams(recipe);

        calculateExtractBeforeBoilingInPercentage(recipe);

        calculateCalculatedIbu(recipe);

        calculateCalculatedColourEBC(recipe);

        calculateCalculatedExtractInPercentage(recipe);

        calculateEstimatedAmountOfAlcoholAfterFermentation(recipe);

        return recipe;
    }

    private boolean requiredParametersWereSet(Recipe recipe) {

        if (recipe.getExpectedAmountOfBeerInLiters().compareTo(BigDecimal.valueOf(0)) == -1) {
            return false;
        }

        if (recipe.getMashingPerformanceInPercentage().compareTo(BigDecimal.valueOf(0)) == -1
                && recipe.getMashingPerformanceInPercentage().compareTo(BigDecimal.valueOf(100)) == 1 )   {
            return false;
        }

        if (BigDecimal.valueOf(recipe.getBoilingProcessTime().toMinutesPart()).compareTo(BigDecimal.valueOf(0)) == -1) {
            return false;
        }

        if (recipe.getWaterEvaporationInPercentagePerHour().compareTo(BigDecimal.valueOf(0)) == -1
                && recipe.getWaterEvaporationInPercentagePerHour().compareTo(BigDecimal.valueOf(100)) == 1 )   {
            return false;
        }

        if (recipe.getBoilingProcessLossInPercentage().compareTo(BigDecimal.valueOf(0)) == -1
                && recipe.getBoilingProcessLossInPercentage().compareTo(BigDecimal.valueOf(100)) == 1 )   {
            return false;
        }

        if (recipe.getFermentationProcessLossInPercentage().compareTo(BigDecimal.valueOf(0)) == -1
                && recipe.getFermentationProcessLossInPercentage().compareTo(BigDecimal.valueOf(100)) == 1 )   {
            return false;
        }

        if (recipe.getMashingFactorInLitersPerKg().compareTo(BigDecimal.valueOf(0)) == -1
                && recipe.getMashingFactorInLitersPerKg().compareTo(BigDecimal.valueOf(100)) == 1 )   {
            return false;
        }

        if (recipe.getRecipeMalts().isEmpty() && recipe.getRecipeHops().isEmpty()) {
            return false;
        }

        return true;
    }

    private void calculateOverallAmountOfMaltInKg (Recipe recipe) {
        BigDecimal overallAmountOfMalt = BigDecimal.valueOf(0);

        for (RecipeMalt recipeMalt : recipe.getRecipeMalts()) {
            overallAmountOfMalt = overallAmountOfMalt.add(recipeMalt.getMaltAmountInKilos());
        }
        recipe.getRecipeCalculatedParameter().setOverallAmountOfMaltInKg(overallAmountOfMalt);
    }

    private void calculateWaterRequiredForMashingInLiters(Recipe recipe) {
        BigDecimal overallAmountOfMalt = recipe.getRecipeCalculatedParameter().getOverallAmountOfMaltInKg();

        recipe.getRecipeCalculatedParameter()
                .setWaterRequiredForMashingInLiters(overallAmountOfMalt.multiply(recipe.getMashingFactorInLitersPerKg()));
    }

    private void calculateWaterRequiredForSpargingInLiters(Recipe recipe) {
        BigDecimal overallAmountOfMalt = recipe.getRecipeCalculatedParameter().getOverallAmountOfMaltInKg();

        BigDecimal hotWort =  recipe.getRecipeCalculatedParameter().getAmountOfHotWort();

        BigDecimal waterThatLeftAfterFiltrationInLiters = overallAmountOfMalt.multiply(BigDecimal.valueOf(0.7));

        BigDecimal waterLostDuringBoilingInLiter = hotWort.divide(BigDecimal.valueOf(1).subtract(recipe.getBoilingProcessLossInPercentage()
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.FLOOR)), 2, RoundingMode.FLOOR).subtract(hotWort);

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

    private void calculateAmountOfWaterBeforeBoilingInLiters(Recipe recipe) {
        BigDecimal overallAmountOfMalt = recipe.getRecipeCalculatedParameter().getOverallAmountOfMaltInKg();

        BigDecimal waterThatLeftAfterFiltrationInLiters = overallAmountOfMalt.multiply(BigDecimal.valueOf(0.7));

        recipe.getRecipeCalculatedParameter().setAmountOfWaterBeforeBoilingInLiters(
                recipe.getRecipeCalculatedParameter().getWaterRequiredForWholeProcessInLiters().subtract(waterThatLeftAfterFiltrationInLiters));
    }
    private void calculateTheoreticalExtract (Recipe recipe) {
        BigDecimal theoreticalExtractInGrams = BigDecimal.valueOf(0);

        if (!CollectionUtils.isEmpty(recipe.getRecipeMalts())) {
            for (RecipeMalt recipeMalt : recipe.getRecipeMalts()) {
                theoreticalExtractInGrams = theoreticalExtractInGrams.add(recipeMalt.getMaltAmountInKilos().
                        multiply(ingredientFacade.getMaltById(recipeMalt.getMaltId()).extractionRateInPercentage().multiply(BigDecimal.valueOf(10))));
            }
        }
        recipe.getRecipeCalculatedParameter().setTheoreticalExtractInGrams(theoreticalExtractInGrams);
    }

    private void calculateRealExtract (Recipe recipe) {
        recipe.getRecipeCalculatedParameter().setRealExtractInGrams(recipe.getRecipeCalculatedParameter().getTheoreticalExtractInGrams().
                multiply(recipe.getMashingPerformanceInPercentage().divide(BigDecimal.valueOf(100), 2, RoundingMode.FLOOR)));
    }

    private void calculateAmountOfHotWort(Recipe recipe) {
        recipe.getRecipeCalculatedParameter().setAmountOfHotWort(recipe.getExpectedAmountOfBeerInLiters()
                .divide(BigDecimal.valueOf(0.96)
                        .subtract(recipe.getFermentationProcessLossInPercentage()
                                .divide(BigDecimal.valueOf(100), 2, RoundingMode.FLOOR )), 2, RoundingMode.FLOOR));
    }

    private void calculateWortWeightInGrams (Recipe recipe) {
        BigDecimal amountOfHotWort = recipe.getRecipeCalculatedParameter().getAmountOfHotWort();
        BigDecimal extractInMilliliters = recipe.getRecipeCalculatedParameter().getRealExtractInGrams().divide(BigDecimal.valueOf(1.587), 2, RoundingMode.FLOOR);
        BigDecimal amountOfWaterInWortInMilliliters = amountOfHotWort.multiply(BigDecimal.valueOf(1000)).subtract(extractInMilliliters);

        recipe.getRecipeCalculatedParameter().setWortWeightInGrams(amountOfWaterInWortInMilliliters.add(recipe.getRecipeCalculatedParameter().getRealExtractInGrams()));
    }

    private void calculateExtractBeforeBoilingInPercentage(Recipe recipe) {
        BigDecimal extractBeforeBoilingInPercentage;
        BigDecimal amountOfHotWort = recipe.getRecipeCalculatedParameter().getAmountOfHotWort();
        BigDecimal wortWeightInGrams = recipe.getRecipeCalculatedParameter().getWortWeightInGrams();
        // 15%
        BigDecimal waterEvaporatedDuringMashing = amountOfHotWort.divide(BigDecimal.valueOf(0.85), 2, RoundingMode.FLOOR).subtract(amountOfHotWort);

        extractBeforeBoilingInPercentage = recipe.getRecipeCalculatedParameter().getRealExtractInGrams()
                .divide(wortWeightInGrams.divide(BigDecimal.valueOf(1000), 2, RoundingMode.FLOOR).add(waterEvaporatedDuringMashing), 2, RoundingMode.FLOOR)
                .divide(BigDecimal.valueOf(10), 2, RoundingMode.FLOOR);

        recipe.getRecipeCalculatedParameter().setExtractBeforeBoilingInPercentage(extractBeforeBoilingInPercentage);
    }


    private void calculateCalculatedIbu(Recipe recipe) {

        if (!CollectionUtils.isEmpty(recipe.getRecipeHops())) {

            BigDecimal ibu = BigDecimal.valueOf(0);

            for (RecipeHop recipeHop : recipe.getRecipeHops()) {
                ibu = ibu.add(recipeHop.getHopAmountInGrams()
                        .multiply(ingredientFacade.getHopById(recipeHop.getHopId()).alfaAcidInPercentage())
                        .multiply(hopUtilisation.getHopUtilisation((int)recipeHop.getBoilingTimeInMinutes().toMinutes()))
                        .divide(recipe.getRecipeCalculatedParameter().getAmountOfHotWort(), 2, RoundingMode.FLOOR)
                        .divide(BigDecimal.valueOf(10), 2, RoundingMode.FLOOR));
            }
            recipe.getRecipeCalculatedParameter().setCalculatedIbu(ibu);
        }
    }

    private void calculateCalculatedColourEBC(Recipe recipe) {

        if (!CollectionUtils.isEmpty(recipe.getRecipeMalts())) {
            BigDecimal sumEbc = BigDecimal.valueOf(0);
            BigDecimal overallAmountOfMaltInKg = recipe.getRecipeCalculatedParameter().getOverallAmountOfMaltInKg();
            for (RecipeMalt recipeMalt : recipe.getRecipeMalts()) {
                sumEbc = sumEbc.add(overallAmountOfMaltInKg
                        .multiply(BigDecimal.valueOf(2.20462262184878))
                        .multiply(ingredientFacade.getMaltById(recipeMalt.getMaltId()).meanColorInEbcScale())
                        .divide(BigDecimal.valueOf(1.97), 2, RoundingMode.FLOOR));
            }

            BigDecimal helpBD = BigDecimal.valueOf(Math.pow(sumEbc
                    .divide(recipe.getRecipeCalculatedParameter().getAmountOfHotWort()
                            .divide(BigDecimal.valueOf(3.78541178), RoundingMode.FLOOR),5, RoundingMode.FLOOR).doubleValue(), 0.6859));

            recipe.getRecipeCalculatedParameter().setCalculatedColourEBC(BigDecimal.valueOf(1.97)
                    .multiply(BigDecimal.valueOf(1.4922)).multiply(helpBD));
        }
    }

    private void calculateCalculatedExtractInPercentage(Recipe recipe) {
        BigDecimal realExtract = recipe.getRecipeCalculatedParameter().getRealExtractInGrams();
        BigDecimal wortWeight = recipe.getRecipeCalculatedParameter().getWortWeightInGrams();

        recipe.getRecipeCalculatedParameter().setCalculatedExtractInPercentage(realExtract
                .divide(wortWeight, 2, RoundingMode.FLOOR)
                .multiply(BigDecimal.valueOf(100)));
    }

    private void calculateEstimatedAmountOfAlcoholAfterFermentation(Recipe recipe) {

        RecipeCalculatedParameter calcParam = recipe.getRecipeCalculatedParameter();
        BigDecimal fermentationFactor = BigDecimal.valueOf(0.8);
        BigDecimal startExtract = calcParam.getCalculatedExtractInPercentage();
        BigDecimal endExtract = startExtract.multiply(BigDecimal.valueOf(1).subtract(fermentationFactor));
        BigDecimal refermentationFactor = BigDecimal.valueOf(0.4);

        recipe.getRecipeCalculatedParameter().setEstimatedAmountOfAlcoholAfterFermentation((startExtract.subtract(endExtract))
                .divide(BigDecimal.valueOf(1.938), 2, RoundingMode.FLOOR)
                .add(refermentationFactor));
    }
}
