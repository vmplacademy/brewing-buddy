package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;

import java.util.List;
import java.util.UUID;

public interface RecipeIngredientService {

    RecipeDto addHopToRecipe (RecipeHopDto recipeHopDto);
    List <RecipeHopDto> getAllRecipeHopFromRecipe (UUID recipeId);
    RecipeDto addMaltToRecipe (RecipeMaltDto recipeMaltDto);
    List <RecipeMaltDto> getAllRecipeMaltsFromRecipe (UUID recipeId);
    RecipeDto addExtraIngredientToRecipe (RecipeExtraIngredientDto recipeExtraIngredientDto);
    List <RecipeExtraIngredientDto> getAllRecipeExtraIngredientsFromRecipe (UUID recipeId);
}
