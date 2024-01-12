package pl.vm.academy.brewingbuddy.core.business.recipe.service.command;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;

import java.math.BigDecimal;

public record CalculateOverallAmountOfMaltInKgCommand() implements RecipeCalculatorCommand {

    @Override
    public RecipeDetailedDto execute(RecipeDetailedDto recipe) {
        BigDecimal overallAmountOfMalt = BigDecimal.valueOf(0);

        for (RecipeMaltDto recipeMalt : recipe.recipeMalts()) {
            overallAmountOfMalt = overallAmountOfMalt.add(recipeMalt.maltAmountInKilos());
        }

        RecipeCalculatedParametersDto recipeCalculatedParametersDto = recipe.recipeCalculatedParametersDto()
                .withOverallAmountOfMaltInKg(overallAmountOfMalt);

        return recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto);
    }
}
