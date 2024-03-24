package pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator.command;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;

import java.math.BigDecimal;

public record CalculateWaterRequiredForMashingInLitersCommand(
) implements RecipeCalculatorCommand {

    @Override
    public RecipeDetailedDto execute(RecipeDetailedDto recipe) {
        BigDecimal overallAmountOfMalt = recipe.recipeCalculatedParametersDto().overallAmountOfMaltInKg();

        BigDecimal waterRequired = overallAmountOfMalt.multiply(recipe.mashingFactorInLitersPerKg());

        RecipeCalculatedParametersDto recipeCalculatedParametersDto = recipe.recipeCalculatedParametersDto()
                .withWaterRequiredForMashingInLiters(waterRequired);

        return recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto);
    }
}
