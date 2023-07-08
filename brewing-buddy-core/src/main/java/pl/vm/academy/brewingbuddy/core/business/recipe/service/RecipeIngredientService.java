package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;

import java.util.List;
import java.util.UUID;

public interface RecipeIngredientService {

    RecipeDto addHopToRecipe (RecipeHopDto recipeHopDto);
    List <RecipeHopDto> getAllRecipeHopFromRecipe (UUID recipeId);
}
