package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;

public interface RecipeParametersCalculator {
    RecipeCalculatedParametersDto calculateParameters (RecipeDetailedDto recipeDetailedDto);
}