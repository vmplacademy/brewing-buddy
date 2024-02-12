package pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator.command;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;

import java.math.BigDecimal;

public record CalculateAmountOfWaterBeforeBoilingInLitersCommand() implements RecipeCalculatorCommand{
    @Override
    public RecipeDetailedDto execute(RecipeDetailedDto recipe) {

        BigDecimal amountOfWaterBeforeBoilingInLiters
                = recipe.recipeCalculatedParametersDto().waterRequiredForWholeProcessInLiters()
                .subtract(recipe.recipeCalculatedParametersDto().overallAmountOfMaltInKg().multiply(BigDecimal.valueOf(0.7)));

        RecipeCalculatedParametersDto recipeCalculatedParametersDto
                = recipe.recipeCalculatedParametersDto().withAmountOfWaterBeforeBoilingInLiters(amountOfWaterBeforeBoilingInLiters);

        return recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto);
    }
}
