package pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator.decorator;

import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.service.IngredientFacade;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.repository.RecipeRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.utils.HopUtilisation;

import java.math.BigDecimal;

public record RecipeCalculatorDecoratorAdapter(
        RecipeRepository recipeRepository,
        RecipeMapper recipeMapper,
        IngredientFacade ingredientFacade,
        HopUtilisation hopUtilisation
) {
    public RecipeCalculatorDecoratorAdapter(
            RecipeRepository recipeRepository,
            RecipeMapper recipeMapper,
            IngredientFacade ingredientFacade,
            HopUtilisation hopUtilisation
    ) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
        this.ingredientFacade = ingredientFacade;
        this.hopUtilisation = hopUtilisation;
    }

    public RecipeCalculatedParametersDto calculateAllParameters(Recipe recipe) {

        if (!requiredParametersWereSet(recipe) || maltsIsEmpty(recipe)) {
            return RecipeCalculatedParametersDto.builder().build();
        }

        RecipeDetailedDto recipeDetailedDto = recipeMapper.mapRecipeToDetailedDto(recipe);

        return new RecipeCalculatorDecorator(recipeDetailedDto, ingredientFacade, hopUtilisation)
                .calculateOverallAmountOfMaltInKg()
                .calculateAmountOfHotWort()
                .calculateWaterRequiredForMashingInLiters()
                .calculateWaterRequiredForSpargingInLiters()
                .calculateWaterRequiredForWholeProcessInLiters()
                .calculateAmountOfWaterBeforeBoilingInLiters()
                .calculateTheoreticalExtract()
                .calculateRealExtract()
                .calculateWortWeightInGrams()
                .calculateExtractBeforeBoilingInPercentage()
                .calculateCalculatedIbu()
                .calculateCalculatedColourEBC()
                .calculateCalculatedExtractInPercentage()
                .calculateEstimatedAmountOfAlcoholAfterFermentation()
                .getCalculatedParameters();
    }

    private boolean requiredParametersWereSet(Recipe recipe) {

        if (recipe.getExpectedAmountOfBeerInLiters().compareTo(BigDecimal.valueOf(0)) <= 0) {
            return false;
        }

        if (recipe.getMashingPerformanceInPercentage().compareTo(BigDecimal.valueOf(0)) <= 0
                || recipe.getMashingPerformanceInPercentage().compareTo(BigDecimal.valueOf(100)) >= 0 )   {
            return false;
        }

        if (BigDecimal.valueOf(recipe.getBoilingProcessTime().toMinutes()).compareTo(BigDecimal.valueOf(0)) <= 0) {
            return false;
        }

        if (recipe.getWaterEvaporationInPercentagePerHour().compareTo(BigDecimal.valueOf(0)) <= 0
                || recipe.getWaterEvaporationInPercentagePerHour().compareTo(BigDecimal.valueOf(100)) >= 0 )   {
            return false;
        }

        if (recipe.getBoilingProcessLossInPercentage().compareTo(BigDecimal.valueOf(0)) <= 0
                || recipe.getBoilingProcessLossInPercentage().compareTo(BigDecimal.valueOf(100)) >= 0 )   {
            return false;
        }

        if (recipe.getFermentationProcessLossInPercentage().compareTo(BigDecimal.valueOf(0)) <= 0
                || recipe.getFermentationProcessLossInPercentage().compareTo(BigDecimal.valueOf(100)) >= 0 )   {
            return false;
        }

        if (recipe.getMashingFactorInLitersPerKg().compareTo(BigDecimal.valueOf(0)) <= 0
                || recipe.getMashingFactorInLitersPerKg().compareTo(BigDecimal.valueOf(10)) >= 0 )   {
            return false;
        }

        if (recipe.getRecipeMalts().isEmpty() && recipe.getRecipeHops().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean maltsIsEmpty (Recipe recipe) {
        return recipe.getRecipeMalts().isEmpty();
    }
}