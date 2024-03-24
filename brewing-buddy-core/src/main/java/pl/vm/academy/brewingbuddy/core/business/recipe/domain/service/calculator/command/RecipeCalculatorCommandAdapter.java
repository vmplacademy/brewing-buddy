package pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator.command;

import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.service.IngredientFacade;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.utils.HopUtilisation;

import java.math.BigDecimal;
import java.util.List;

public record RecipeCalculatorCommandAdapter(
        RecipeMapper recipeMapper,
        IngredientFacade ingredientFacade,
        HopUtilisation hopUtilisation
) implements RecipeCalculatorCommandInterface {
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
