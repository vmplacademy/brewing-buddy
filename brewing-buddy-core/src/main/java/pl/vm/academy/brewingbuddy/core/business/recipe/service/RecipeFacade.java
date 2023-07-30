package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeSimpleDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeYeastDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RecipeFacade {

    /**
     * @param recipeSimpleDto
     * @return RecipeDto
     */
    public RecipeDetailedDto createRecipe (RecipeSimpleDto recipeSimpleDto);

    /**
     * @param recipeSimpleDto
     * @return RecipeDto
     */
    public RecipeCalculatedParametersDto updateRecipe (RecipeSimpleDto recipeSimpleDto) ;

    /**
     * @return List<RecipeDto>
     */
    public List<RecipeDetailedDto> getAllRecipes();

    /**
     * @return List<RecipeDto>
     */
    public List<RecipeDetailedDto> getAllPublicRecipes ();

    /**
     * @param recipeId
     */
    public void deleteRecipe(UUID recipeId);

    /**
     * @return Recipe
     * @param recipeId
     */
    RecipeDetailedDto getRecipeById(UUID recipeId);

    RecipeDetailedDto addHopToRecipe (RecipeHopDto recipeHopDto);

    Set<RecipeHopDto> getAllRecipeHopFromRecipe (UUID recipeId);
    RecipeDetailedDto addMaltToRecipe (RecipeMaltDto recipeMaltDto);
    Set <RecipeMaltDto> getAllRecipeMaltsFromRecipe (UUID recipeId);
    RecipeDetailedDto addRecipeExtraIngredientToRecipe (RecipeExtraIngredientDto recipeExtraIngredientDto);
    Set<RecipeExtraIngredientDto> getAllRecipeExtraIngredientsFromRecipe(UUID recipeId);
    RecipeDetailedDto addYeastToRecipe (RecipeYeastDto recipeYeastDto);

}
