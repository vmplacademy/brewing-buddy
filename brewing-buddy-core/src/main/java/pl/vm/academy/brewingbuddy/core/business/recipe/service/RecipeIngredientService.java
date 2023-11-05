package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeYeastDto;

public interface RecipeIngredientService {

    /**
     *
     * @param recipeHopDto
     * @return RecipeDetailedDto
     */
    RecipeDetailedDto addHopToRecipe (RecipeHopDto recipeHopDto);

    /**
     *
     * @param recipeMaltDto
     * @return RecipeDetailedDto
     */
    RecipeDetailedDto addMaltToRecipe (RecipeMaltDto recipeMaltDto);

    /**
     *
     * @param recipeExtraIngredientDto
     * @return RecipeDetailedDto
     */
    RecipeDetailedDto addExtraIngredientToRecipe (RecipeExtraIngredientDto recipeExtraIngredientDto);

    /**
     *
     * @param recipeYeastDto
     * @return RecipeDetailedDto
     */
    RecipeDetailedDto addYeastToRecipe (RecipeYeastDto recipeYeastDto);
}
