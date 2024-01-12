package pl.vm.academy.brewingbuddy.core.business.recipe.service.command;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;

import java.util.List;

public record RecipeCalculatorCommandExecutor(
        List<RecipeCalculatorCommand> commands
) {
    public RecipeCalculatedParametersDto executeAll(RecipeDetailedDto recipe) {
        RecipeDetailedDto updatedRecipe = recipe;

        for (RecipeCalculatorCommand command : commands) {
            updatedRecipe = command.execute(updatedRecipe);
        }

        return updatedRecipe.recipeCalculatedParametersDto();
    }
}
