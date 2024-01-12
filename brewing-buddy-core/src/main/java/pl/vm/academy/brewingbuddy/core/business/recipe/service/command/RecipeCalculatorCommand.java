package pl.vm.academy.brewingbuddy.core.business.recipe.service.command;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;

@FunctionalInterface
public interface RecipeCalculatorCommand {

    RecipeDetailedDto execute(final RecipeDetailedDto recipe);
}
