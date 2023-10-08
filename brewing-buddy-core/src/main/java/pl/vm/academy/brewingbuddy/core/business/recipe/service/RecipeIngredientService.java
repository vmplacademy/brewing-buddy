package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeYeastDto;

import java.util.Set;
import java.util.UUID;

public interface RecipeIngredientService {

    /**
     *
     * @param recipeHopDto
     * @return RecipeDetailedDto
     */
    RecipeDetailedDto addHopToRecipe (RecipeHopDto recipeHopDto);

    /**
     *
     * @param recipeId
     * @return Set<RecipeHopDto>
     */
    Set<RecipeHopDto> getAllRecipeHopFromRecipe (UUID recipeId);

    /**
     *
     * @param recipeMaltDto
     * @return RecipeDetailedDto
     */
    RecipeDetailedDto addMaltToRecipe (RecipeMaltDto recipeMaltDto);

    /**
     *
     * @param recipeId
     * @return Set <RecipeMaltDto>
     */
    Set <RecipeMaltDto> getAllRecipeMaltsFromRecipe (UUID recipeId);

    /**
     *
     * @param recipeExtraIngredientDto
     * @return RecipeDetailedDto
     */
    RecipeDetailedDto addExtraIngredientToRecipe (RecipeExtraIngredientDto recipeExtraIngredientDto);

    /**
     *
     * @param recipeId
     * @return Set <RecipeExtraIngredientDto>
     */
    Set <RecipeExtraIngredientDto> getAllRecipeExtraIngredientsFromRecipe (UUID recipeId);

    /**
     *
     * @param recipeYeastDto
     * @return RecipeDetailedDto
     */
    RecipeDetailedDto addYeastToRecipe (RecipeYeastDto recipeYeastDto);
}
