package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;

public interface RecipeParametersCalculator {
    Recipe calculateParameters (Recipe recipe);
}