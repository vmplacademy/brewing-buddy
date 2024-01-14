package pl.vm.academy.brewingbuddy.core.business.recipe.service.command;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;

import java.math.BigDecimal;

public record CalculateWaterRequiredForWholeProcessInLitersCommand() implements RecipeCalculatorCommand {
    @Override
    public RecipeDetailedDto execute(RecipeDetailedDto recipe) {
        BigDecimal waterRequiredForWholeProcessInLiters = recipe.recipeCalculatedParametersDto().waterRequiredForSpargingInLiters()
                .add(recipe.recipeCalculatedParametersDto().waterRequiredForMashingInLiters());

        RecipeCalculatedParametersDto recipeCalculatedParametersDto
                = recipe.recipeCalculatedParametersDto().withWaterRequiredForWholeProcessInLiters(waterRequiredForWholeProcessInLiters);

        return recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto);
    }
}
