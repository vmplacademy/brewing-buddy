package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeYeastDto;

import java.util.Set;
import java.util.UUID;

public interface RecipeIngredientService {

    RecipeDetailedDto addHopToRecipe (RecipeHopDto recipeHopDto);
    Set<RecipeHopDto> getAllRecipeHopFromRecipe (UUID recipeId);
    RecipeDetailedDto addMaltToRecipe (RecipeMaltDto recipeMaltDto);
    Set <RecipeMaltDto> getAllRecipeMaltsFromRecipe (UUID recipeId);
    RecipeDetailedDto addExtraIngredientToRecipe (RecipeExtraIngredientDto recipeExtraIngredientDto);
    Set <RecipeExtraIngredientDto> getAllRecipeExtraIngredientsFromRecipe (UUID recipeId);
    RecipeDetailedDto addYeastToRecipe (RecipeYeastDto recipeYeastDto);
}
