package pl.vm.academy.brewingbuddy.core.business.recipe.service.command;

import pl.vm.academy.brewingbuddy.core.business.ingredient.service.IngredientFacade;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.utils.HopUtilisation;

import java.math.BigDecimal;
import java.util.List;

public record RecipeCalculatorCommandAdapter(
        RecipeMapper recipeMapper,
        IngredientFacade ingredientFacade,
        HopUtilisation hopUtilisation
) {
    public RecipeCalculatedParametersDto calculateParametersCommand(Recipe recipe) {
        return new RecipeCalculatorCommandExecutor(
                List.of(
                        new CalculateOverallAmountOfMaltInKgCommand(),
                        new CalculateAmountOfHotWortCommand(),
                        new CalculateWaterRequiredForMashingInLitersCommand(),
                        new CalculateWaterRequiredForSpargingInLitersCommand(),
                        new CalculateWaterRequiredForWholeProcessInLitersCommand(),
                        new CalculateAmountOfWaterBeforeBoilingInLitersCommand(),
                        new CalculateTheoreticalExtractCommand(ingredientFacade),
                        new CalculateRealExtractCommand(),
                        new CalculateWortWeightInGramsCommand(),
                        new CalculateExtractBeforeBoilingInPercentageCommand(),
                        new CalculateCalculatedIbuCommand(ingredientFacade, hopUtilisation),
                        new CalculateCalculatedColourEBCCommand(ingredientFacade),
                        new CalculateCalculatedExtractInPercentageCommand(),
                        new CalculateEstimatedAmountOfAlcoholAfterFermentationCommand()
                        //this::calculateWaterRequiredForMashingInLiters
                ))
                .executeAll(recipeMapper.mapRecipeToDetailedDto(recipe));
    }

    public RecipeDetailedDto calculateWaterRequiredForMashingInLiters(RecipeDetailedDto recipe) {
        BigDecimal overallAmountOfMalt = recipe.recipeCalculatedParametersDto().overallAmountOfMaltInKg();

        BigDecimal waterRequired = overallAmountOfMalt.multiply(recipe.mashingFactorInLitersPerKg());

        RecipeCalculatedParametersDto recipeCalculatedParametersDto = recipe.recipeCalculatedParametersDto()
                .withWaterRequiredForMashingInLiters(waterRequired);

        return recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto);
    }
}
