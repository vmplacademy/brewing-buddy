package pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator.command;

import pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator.CalculatorConstants;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record CalculateWortWeightInGramsCommand() implements RecipeCalculatorCommand {
    @Override
    public RecipeDetailedDto execute(RecipeDetailedDto recipe) {
        BigDecimal amountOfHotWort = recipe.recipeCalculatedParametersDto().amountOfHotWort();
        BigDecimal extractInMilliliters = recipe.recipeCalculatedParametersDto()
            .realExtractInGrams().divide(CalculatorConstants.EXTRACT_GRAMS_MLITERS_RATIO, 2, RoundingMode.FLOOR);
        BigDecimal amountOfWaterInWortInMilliliters = amountOfHotWort.multiply(
            BigDecimal.valueOf(1000)).subtract(extractInMilliliters);

        BigDecimal wortWeight =  amountOfWaterInWortInMilliliters.add(recipe.recipeCalculatedParametersDto().realExtractInGrams());

        RecipeCalculatedParametersDto recipeCalculatedParametersDto
                = recipe.recipeCalculatedParametersDto().withWortWeightInGrams(wortWeight);

        return recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto);
    }
}
