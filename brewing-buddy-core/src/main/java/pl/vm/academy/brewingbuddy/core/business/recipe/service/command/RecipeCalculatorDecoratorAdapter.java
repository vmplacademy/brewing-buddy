package pl.vm.academy.brewingbuddy.core.business.recipe.service.command;import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.command.CalculateOverallAmountOfMaltInKgCommand;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.command.CalculateWaterRequiredForMashingInLitersCommand;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.command.RecipeCalculatorCommandExecutor;

import java.math.BigDecimal;
import java.util.List;

public record RecipeCalculatorDecoratorAdapter(
        RecipeMapper recipeMapper
) {
    public RecipeCalculatedParametersDto calculateParametersCommand(Recipe recipe) {
        return new RecipeCalculatorCommandExecutor(
                List.of(
                        new CalculateWaterRequiredForMashingInLitersCommand(),
                        new CalculateOverallAmountOfMaltInKgCommand(),
                        this::calculateWaterRequiredForMashingInLiters
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
