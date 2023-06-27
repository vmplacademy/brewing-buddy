package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;

import java.util.List;
import java.util.UUID;

public interface RecipeService {

    RecipeDto getRecipeById(UUID recipeId);

    List<RecipeDto> getAllPublicRecipes();

    List<RecipeDto> getAllRecipes();

    RecipeDto createRecipe(RecipeDto recipeDto);

    RecipeDto updateRecipe(RecipeDto recipeDto);

    void deleteRecipe(UUID id);

    RecipeHopDto addHopToRecipe (RecipeHopDto recipeHopDto);

}
