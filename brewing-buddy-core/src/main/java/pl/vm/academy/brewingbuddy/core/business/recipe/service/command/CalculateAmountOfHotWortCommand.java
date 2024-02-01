package pl.vm.academy.brewingbuddy.core.business.recipe.service.command;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record CalculateAmountOfHotWortCommand() implements RecipeCalculatorCommand {
    @Override
    public RecipeDetailedDto execute(RecipeDetailedDto recipe) {

        BigDecimal amountOfHotWort =  recipe.expectedAmountOfBeerInLiters()
                .divide(BigDecimal.valueOf(0.96)
                        .subtract(recipe.fermentationProcessLossInPercentage()
                                .divide(BigDecimal.valueOf(100), 2, RoundingMode.FLOOR )), 2, RoundingMode.FLOOR);

        RecipeCalculatedParametersDto recipeCalculatedParametersDto
                = recipe.recipeCalculatedParametersDto().withAmountOfHotWort(amountOfHotWort);

        return recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto);

    }
}
