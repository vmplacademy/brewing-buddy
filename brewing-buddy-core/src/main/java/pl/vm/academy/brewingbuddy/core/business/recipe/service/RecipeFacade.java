package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;

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
    public RecipeDto getRecipeById(UUID recipeId);

    RecipeDto addHopToRecipe (RecipeHopDto recipeHopDto);

    List <RecipeHopDto> getAllRecipeHopFromRecipe (UUID recipeId);

}
