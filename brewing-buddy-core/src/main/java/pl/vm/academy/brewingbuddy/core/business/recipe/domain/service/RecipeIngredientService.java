package pl.vm.academy.brewingbuddy.core.business.recipe.domain.service;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeYeastDto;

public interface RecipeIngredientService {

    /**
     * Adds given hop to Recipe and returns RecipeDetailedDto
     *
     * @param recipeHopDto
     * @return RecipeDetailedDto
     */
    RecipeDetailedDto addHopToRecipe (RecipeHopDto recipeHopDto);

    /**
     * Adds given malt to Recipe and returns RecipeDetailedDto
     *
     * @param recipeMaltDto
     * @return RecipeDetailedDto
     */
    RecipeDetailedDto addMaltToRecipe (RecipeMaltDto recipeMaltDto);

    /**
     * Adds given extra ingredient to Recipe and returns RecipeDetailedDto
     *
     * @param recipeExtraIngredientDto
     * @return RecipeDetailedDto
     */
    RecipeDetailedDto addExtraIngredientToRecipe (RecipeExtraIngredientDto recipeExtraIngredientDto);

    /**
     * Adds given yeast to Recipe and returns RecipeDetailedDto
     *
     * @param recipeYeastDto
     * @return RecipeDetailedDto
     */
    RecipeDetailedDto addYeastToRecipe (RecipeYeastDto recipeYeastDto);
}
