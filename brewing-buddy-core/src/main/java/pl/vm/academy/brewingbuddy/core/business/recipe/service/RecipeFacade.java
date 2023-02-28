package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;

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

}
