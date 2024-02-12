package pl.vm.academy.brewingbuddy.core.business.recipe.domain.service;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeBasicDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeSimpleDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeYeastDto;

import java.util.List;
import java.util.UUID;

public interface RecipeFacade {

    /**
     * Returns Dto of created Recipe
     *
     * @param recipeSimpleDto  Recipe simple dto
     * @return RecipeDetailedDto
     */
    RecipeDetailedDto createRecipe (RecipeSimpleDto recipeSimpleDto);

    /**
     * Returns Dto of calculated parameters of changed recipe
     *
     * @param recipeSimpleDto Recipe simple dto
     * @return RecipeCalculatedParametersDto
     */
    RecipeCalculatedParametersDto updateRecipe (RecipeSimpleDto recipeSimpleDto) ;

    /**
     * Returns list of all Recipes
     *
     * @return List<RecipeDetailedDto>
     */
    List<RecipeBasicDto> getAllRecipes();

    /**
     * Returns list of all public Recipes
     *
     * @return List<RecipeDetailedDto>
     */
    List<RecipeBasicDto> getAllPublicRecipes ();

    /**
     * Deletes Recipe with given id
     *
     * @param recipeId id of recipe
     */
    void deleteRecipe(UUID recipeId);

    /**
     * Returns Dto of Recipe with given id
     *
     * @param recipeId id of recipe
     * @return RecipeDetailedDto
     */
    RecipeDetailedDto getRecipeById(UUID recipeId);
    /**
     * Adds given hop to Recipe and returns Recipe Dto
     *
     * @param recipeHopDto  Dto with recipe id and hop
     * @return RecipeDetailedDto
     */
    RecipeDetailedDto addHopToRecipe (RecipeHopDto recipeHopDto);
    /**
     * Adds given malt to Recipe and returns Recipe Dto
     *
     * @param recipeMaltDto  Dto with recipe id and malt
     * @return RecipeDetailedDto
     */
    RecipeDetailedDto addMaltToRecipe (RecipeMaltDto recipeMaltDto);

    /**
     * Adds given extra ingredient to Recipe and returns Recipe Dto
     *
     * @param recipeExtraIngredientDto  Dto with recipe id and extra ingredient
     * @return RecipeDetailedDto
     */
    RecipeDetailedDto addRecipeExtraIngredientToRecipe (RecipeExtraIngredientDto recipeExtraIngredientDto);

    /**
     * Adds given yeast to Recipe and returns Recipe Dto
     *
     * @param recipeYeastDto  Dto with recipe id and yeast
     * @return RecipeDetailedDto
     */
    RecipeDetailedDto addYeastToRecipe (RecipeYeastDto recipeYeastDto);
}