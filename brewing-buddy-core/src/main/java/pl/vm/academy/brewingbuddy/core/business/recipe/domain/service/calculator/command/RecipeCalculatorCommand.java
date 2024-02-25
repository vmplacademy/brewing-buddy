package pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator.command;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;

@FunctionalInterface
public interface RecipeCalculatorCommand {

    /**
     * Returns detailed Dto of calculated parameters of recipe
     *
     * @param recipe RecipeDetailedDto simple dto
     * @return RecipeDetailedDto
     */
    RecipeDetailedDto execute(final RecipeDetailedDto recipe);
}
