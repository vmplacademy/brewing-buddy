package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeYeastDto;


import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
public class RecipeFacadeAdapter implements RecipeFacade{
    RecipeService recipeService;
    RecipeIngredientService recipeIngredientService;
    public RecipeFacadeAdapter(RecipeService recipeService, RecipeIngredientService recipeIngredientService) {
        this.recipeService = recipeService;
        this.recipeIngredientService = recipeIngredientService;
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

    public RecipeDto addHopToRecipe(RecipeHopDto recipeHopDto){
        return recipeIngredientService.addHopToRecipe(recipeHopDto);
    }

    @Override
    public List<RecipeHopDto> getAllRecipeHopFromRecipe(UUID recipeId) {
        return recipeIngredientService.getAllRecipeHopFromRecipe(recipeId);
    }

    @Override
    public RecipeDto addMaltToRecipe(RecipeMaltDto recipeMaltDto) {
        return recipeIngredientService.addMaltToRecipe(recipeMaltDto);
    }

    @Override
    public List<RecipeMaltDto> getAllRecipeMaltsFromRecipe(UUID recipeId) {
        return recipeIngredientService.getAllRecipeMaltsFromRecipe(recipeId);
    }

    @Override
    public RecipeDto addRecipeExtraIngredientToRecipe(RecipeExtraIngredientDto recipeExtraIngredientDto) {
        return recipeIngredientService.addExtraIngredientToRecipe(recipeExtraIngredientDto);
    }

    @Override
    public List<RecipeExtraIngredientDto> getAllRecipeExtraIngredientsFromRecipe(UUID recipeId) {
        return recipeIngredientService.getAllRecipeExtraIngredientsFromRecipe(recipeId);
    }

    @Override
    public RecipeDto addYeastToRecipe(RecipeYeastDto recipeYeastDto) {
        return recipeIngredientService.addYeastToRecipe(recipeYeastDto);
    }

}
