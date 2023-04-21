package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import org.springframework.http.HttpStatus;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;

import java.util.List;
import java.util.UUID;

public interface RecipeService {

    RecipeDto getRecipeById(UUID recipeId);

    List<RecipeDto> getAllPublicRecipes();

    List<RecipeDto> getAllRecipes();

    RecipeDto createRecipe(RecipeDto recipeDto);

    RecipeDto updateRecipe(RecipeDto recipeDto);

    HttpStatus deleteRecipe(RecipeDto recipeDto);
}
