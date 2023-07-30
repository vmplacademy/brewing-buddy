package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeSimpleDto;

import java.util.List;
import java.util.UUID;

public interface RecipeService {

    RecipeDetailedDto getRecipeById(UUID recipeId);

    List<RecipeDetailedDto> getAllPublicRecipes();

    List<RecipeDetailedDto> getAllRecipes();

    RecipeDetailedDto createRecipe(RecipeSimpleDto recipeSimpleDto);

    RecipeCalculatedParametersDto updateRecipe(RecipeSimpleDto recipeSimpleDto);

    void deleteRecipe(UUID id);

}
