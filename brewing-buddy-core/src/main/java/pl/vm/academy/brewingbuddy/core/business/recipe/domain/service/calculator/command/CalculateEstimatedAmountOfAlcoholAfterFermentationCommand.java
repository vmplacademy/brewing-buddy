package pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator.command;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record CalculateEstimatedAmountOfAlcoholAfterFermentationCommand() implements RecipeCalculatorCommand {
    @Override
    public RecipeDetailedDto execute(RecipeDetailedDto recipe) {
        RecipeCalculatedParametersDto calcParam = recipe.recipeCalculatedParametersDto();
        BigDecimal fermentationFactor = BigDecimal.valueOf(0.8);
        BigDecimal startExtract = calcParam.calculatedExtractInPercentage();
        BigDecimal endExtract = startExtract.multiply(BigDecimal.valueOf(1).subtract(fermentationFactor));
        BigDecimal refermentationFactor = BigDecimal.valueOf(0.4);

        BigDecimal estimatedAmountOfAlcoholAfterFermentation = ((startExtract.subtract(endExtract))
                .divide(BigDecimal.valueOf(1.938), 2, RoundingMode.FLOOR)
                .add(refermentationFactor));

        RecipeCalculatedParametersDto recipeCalculatedParametersDto
                = recipe.recipeCalculatedParametersDto().withEstimatedAmountOfAlcoholAfterFermentation(estimatedAmountOfAlcoholAfterFermentation);

        return recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto);
    }
}
