package pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator.command;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;

@FunctionalInterface
public interface RecipeCalculatorCommand {

    RecipeDetailedDto execute(final RecipeDetailedDto recipe);
}
