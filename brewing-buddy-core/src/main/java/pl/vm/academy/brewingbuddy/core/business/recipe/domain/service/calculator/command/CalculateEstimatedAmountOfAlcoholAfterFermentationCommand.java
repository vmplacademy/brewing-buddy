package pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator.command;

import pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator.CalculatorConstants;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record CalculateEstimatedAmountOfAlcoholAfterFermentationCommand() implements RecipeCalculatorCommand {
    @Override
    public RecipeDetailedDto execute(RecipeDetailedDto recipe) {
        RecipeCalculatedParametersDto calcParam = recipe.recipeCalculatedParametersDto();
        BigDecimal startExtract = calcParam.calculatedExtractInPercentage();
        BigDecimal endExtract = startExtract.multiply(BigDecimal.valueOf(1).subtract(
            CalculatorConstants.FERMENTATION_FACTOR));

        BigDecimal estimatedAmountOfAlcoholAfterFermentation = ((startExtract.subtract(endExtract))
                .divide(CalculatorConstants.BLG_ABV_FACTOR, 2, RoundingMode.FLOOR)
                .add(CalculatorConstants.REFERMENTATION_FACTOR));

        RecipeCalculatedParametersDto recipeCalculatedParametersDto
                = recipe.recipeCalculatedParametersDto().withEstimatedAmountOfAlcoholAfterFermentation(estimatedAmountOfAlcoholAfterFermentation);

        return recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto);
    }
}
