package pl.vm.academy.brewingbuddy.core.business.recipe.service.command;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record CalculateCalculatedExtractInPercentageCommand() implements RecipeCalculatorCommand {
    @Override
    public RecipeDetailedDto execute(RecipeDetailedDto recipe) {
        BigDecimal realExtract = recipe.recipeCalculatedParametersDto().realExtractInGrams();
        BigDecimal wortWeight = recipe.recipeCalculatedParametersDto().wortWeightInGrams();

        BigDecimal calculatedExtractInPercentage = (realExtract
                .divide(wortWeight, 2, RoundingMode.FLOOR)
                .multiply(BigDecimal.valueOf(100)));

        RecipeCalculatedParametersDto recipeCalculatedParametersDto
                = recipe.recipeCalculatedParametersDto().withCalculatedExtractInPercentage(calculatedExtractInPercentage);

        return recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto);
    }
}
