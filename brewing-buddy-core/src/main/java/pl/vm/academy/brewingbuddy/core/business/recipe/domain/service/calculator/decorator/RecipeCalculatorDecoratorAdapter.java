package pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator.decorator;

import java.util.function.Predicate;
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

        Predicate<BigDecimal> lesserEqualZero = i -> (i.compareTo(BigDecimal.valueOf(0)) <= 0);
        Predicate<BigDecimal> greaterEqualOneHundred = i -> (i.compareTo(BigDecimal.valueOf(100)) >= 0);
        Predicate<BigDecimal> greaterEqualTen = i -> (i.compareTo(BigDecimal.valueOf(10)) >= 0);

        if (lesserEqualZero.test(recipe.getExpectedAmountOfBeerInLiters())) {
            return false;
        }

        if (lesserEqualZero.test(recipe.getMashingPerformanceInPercentage())
                || greaterEqualOneHundred.test(recipe.getMashingPerformanceInPercentage())) {
            return false;
        }

        if (lesserEqualZero.test(BigDecimal.valueOf(recipe.getBoilingProcessTime().toMinutes()))) {
            return false;
        }

        if (lesserEqualZero.test(recipe.getWaterEvaporationInPercentagePerHour())
                || greaterEqualOneHundred.test(recipe.getWaterEvaporationInPercentagePerHour())) {
            return false;
        }

        if (lesserEqualZero.test(recipe.getBoilingProcessLossInPercentage())
                || greaterEqualOneHundred.test(recipe.getBoilingProcessLossInPercentage())) {
            return false;
        }

        if (lesserEqualZero.test(recipe.getFermentationProcessLossInPercentage())
                || greaterEqualOneHundred.test(recipe.getFermentationProcessLossInPercentage())) {
            return false;
        }

        if (lesserEqualZero.test(recipe.getMashingFactorInLitersPerKg())
                || greaterEqualTen.test(recipe.getMashingFactorInLitersPerKg()))   {
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