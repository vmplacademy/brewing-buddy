package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeYeastDto;

import java.util.List;
import java.util.UUID;

public interface RecipeFacade {

    /**
     * @param recipeDto
     * @return RecipeDto
     */
    public RecipeDto createRecipe (RecipeDto recipeDto);

    /**
     * @param recipeDto
     * @return RecipeDto
     */
    public RecipeDto updateRecipe (RecipeDto recipeDto) ;

    /**
     * @return List<RecipeDto>
     */
    public List<RecipeDto> getAllRecipes();

    /**
     * @return List<RecipeDto>
     */
    public List<RecipeDto> getAllPublicRecipes ();

    /**
     * @param recipeId
     */
    public void deleteRecipe(UUID recipeId);

    /**
     * @return Recipe
     * @param recipeId
     */
    RecipeDto getRecipeById(UUID recipeId);

    RecipeDto addHopToRecipe (RecipeHopDto recipeHopDto);

    List <RecipeHopDto> getAllRecipeHopFromRecipe (UUID recipeId);
    RecipeDto addMaltToRecipe (RecipeMaltDto recipeMaltDto);
    List <RecipeMaltDto> getAllRecipeMaltsFromRecipe (UUID recipeId);
    RecipeDto addRecipeExtraIngredientToRecipe (RecipeExtraIngredientDto recipeExtraIngredientDto);
    List<RecipeExtraIngredientDto> getAllRecipeExtraIngredientsFromRecipe(UUID recipeId);
    RecipeDto addYeastToRecipe (RecipeYeastDto recipeYeastDto);

}
