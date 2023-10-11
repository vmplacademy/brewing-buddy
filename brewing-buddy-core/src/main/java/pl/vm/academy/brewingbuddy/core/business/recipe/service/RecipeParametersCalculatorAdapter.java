package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import lombok.RequiredArgsConstructor;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeMaltRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;

import java.math.BigDecimal;
import java.util.Set;

@RequiredArgsConstructor
public class RecipeParametersCalculatorAdapter implements RecipeParametersCalculator{

    private final RecipeRepository recipeRepository;
    private final RecipeMaltRepository recipeMaltRepository;


    @Override
    public RecipeCalculatedParametersDto calculateParameters(RecipeDetailedDto recipeDetailedDto) {
        return null;
    }

    private void calculateOverallAmountOfMaltInKg (Recipe recipe, RecipeDetailedDto recipeDetailedDto) {
        BigDecimal overallAmountOfMalt = BigDecimal.valueOf(0);

        for (RecipeMaltDto recipeMaltDto : recipeDetailedDto.recipeMaltDtoSet()) {
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

    private void calculateExtractBeforeBoilingInPercentage(RecipeDetailedDto recipeDetailedDto) {

    }

    private void calculateAmountOfHotWort(Recipe recipe, RecipeDetailedDto recipeDetailedDto) {
        recipe.getRecipeCalculatedParameter().setAmountOfHotWort(recipeDetailedDto.expectedAmountOfBeerInLiters());
    }


    private void calculateCalculatedIbu(RecipeDetailedDto recipeDetailedDto) {

    }

    private void calculateCalculatedColourEBC(RecipeDetailedDto recipeDetailedDto) {

    }

    private void calculateCalculatedExtractInPercentage(RecipeDetailedDto recipeDetailedDto) {

    }

    private void calculateEstimatedAmountOfAlcoholAfterFermentation(RecipeDetailedDto recipeDetailedDto) {

    }

}
