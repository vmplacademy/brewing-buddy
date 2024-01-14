package pl.vm.academy.brewingbuddy.core.business.recipe.service.command;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record CalculateExtractBeforeBoilingInPercentageCommand() implements RecipeCalculatorCommand {
    @Override
    public RecipeDetailedDto execute(RecipeDetailedDto recipe) {
        BigDecimal extractBeforeBoilingInPercentage;
        BigDecimal amountOfHotWort = recipe.recipeCalculatedParametersDto().amountOfHotWort();
        BigDecimal wortWeightInGrams = recipe.recipeCalculatedParametersDto().wortWeightInGrams();
        // 15%
        BigDecimal waterEvaporatedDuringMashing = amountOfHotWort.divide(BigDecimal.valueOf(0.85), 2, RoundingMode.FLOOR).subtract(amountOfHotWort);

        extractBeforeBoilingInPercentage = recipe.recipeCalculatedParametersDto().realExtractInGrams()
                .divide(wortWeightInGrams.divide(BigDecimal.valueOf(1000), 2, RoundingMode.FLOOR).add(waterEvaporatedDuringMashing), 2, RoundingMode.FLOOR)
                .divide(BigDecimal.valueOf(10), 2, RoundingMode.FLOOR);

        RecipeCalculatedParametersDto recipeCalculatedParametersDto
                = recipe.recipeCalculatedParametersDto().withExtractBeforeBoilingInPercentage(extractBeforeBoilingInPercentage);

        return recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto);
    }
}
