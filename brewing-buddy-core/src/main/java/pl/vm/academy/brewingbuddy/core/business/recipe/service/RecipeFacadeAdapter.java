package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;


import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
public class RecipeFacadeAdapter implements RecipeFacade{
    RecipeService recipeService;
    public RecipeFacadeAdapter(RecipeService recipeService) {
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

    public void deleteRecipe(UUID recipeId) {
        recipeService.deleteRecipe(recipeId);
    }

    public RecipeDto getRecipeById(UUID recipeId) {
        return recipeService.getRecipeById(recipeId);
    }

    public RecipeHopDto addHopToRecipe(RecipeHopDto recipeHopDto){
        return recipeService.addHopToRecipe(recipeHopDto);
    }

}
