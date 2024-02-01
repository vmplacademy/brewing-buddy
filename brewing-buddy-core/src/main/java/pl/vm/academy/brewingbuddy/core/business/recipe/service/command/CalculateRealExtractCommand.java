package pl.vm.academy.brewingbuddy.core.business.recipe.service.command;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record CalculateRealExtractCommand() implements RecipeCalculatorCommand{
    @Override
    public RecipeDetailedDto execute(RecipeDetailedDto recipe) {
        BigDecimal realExtract = recipe.recipeCalculatedParametersDto().theoreticalExtractInGrams().
                multiply(recipe.mashingPerformanceInPercentage().divide(BigDecimal.valueOf(100), 2, RoundingMode.FLOOR));

        RecipeCalculatedParametersDto recipeCalculatedParametersDto
                = recipe.recipeCalculatedParametersDto().withRealExtractInGrams(realExtract);

        return recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto);
    }
}
