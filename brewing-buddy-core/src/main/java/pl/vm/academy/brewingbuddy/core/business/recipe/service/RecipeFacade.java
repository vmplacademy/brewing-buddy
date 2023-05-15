package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
public class RecipeFacade {
    RecipeRepository recipeRepository;
    RecipeService recipeService;
    public RecipeFacade(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * @param recipeDto
     * @return RecipeDto
     */
    public RecipeDto createRecipe (RecipeDto recipeDto) {
        return recipeService.createRecipe(recipeDto);
    }

    /**
     * @param recipeDto
     * @return RecipeDto
     */
    public RecipeDto updateRecipe (RecipeDto recipeDto) {
        return recipeService.updateRecipe(recipeDto);
    }

    /**
     * @return List<RecipeDto>
     */
    public List<RecipeDto> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    /**
     * @return List<RecipeDto>
     */
    public List<RecipeDto> getAllPublicRecipes () {
        return recipeService.getAllPublicRecipes();
    }

    /**
     * @param recipeDto
     */
    public void deleteRecipe(UUID id) {
        recipeService.deleteRecipe(id);
    }

    /**
     * @param recipeId
     * @return Recipe
     */
    public RecipeDto getRecipeById(UUID recipeId) {
        return recipeService.getRecipeById(recipeId);
    }

}
