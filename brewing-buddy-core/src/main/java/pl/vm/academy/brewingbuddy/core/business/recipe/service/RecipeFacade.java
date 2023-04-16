package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class RecipeFacade {
    RecipeRepository recipeRepository;
    RecipeService recipeService;
    public RecipeFacade(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    public RecipeDto createRecipe (RecipeDto recipeDto) {
        return recipeService.createRecipe(recipeDto);
    }

    public RecipeDto updateRecipe (RecipeDto recipeDto) {
        return recipeService.updateRecipe(recipeDto);
    }

    public List<RecipeDto> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    public List<RecipeDto> getAllPublicRecipes () {
        return recipeService.getAllPublicRecipes();
    }

    public HttpStatus deleteRecipe (RecipeDto recipeDto) {
        return recipeService.deleteRecipe(recipeDto);
    }

    public RecipeDto getRecipeById(RecipeDto recipeDto) {
        return recipeService.getRecipeById(recipeDto);
    }

}
