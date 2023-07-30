package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Transactional
public class RecipeFacadeAdapter implements RecipeFacade{
    RecipeService recipeService;
    RecipeIngredientService recipeIngredientService;
    public RecipeFacadeAdapter(RecipeService recipeService, RecipeIngredientService recipeIngredientService) {
        this.recipeService = recipeService;
        this.recipeIngredientService = recipeIngredientService;
    }

    public RecipeDetailedDto createRecipe (RecipeSimpleDto recipeSimpleDto) {
        return recipeService.createRecipe(recipeSimpleDto);
    }

    public RecipeCalculatedParametersDto updateRecipe (RecipeSimpleDto recipeSimpleDto) {
        return recipeService.updateRecipe(recipeSimpleDto);
    }

    public List<RecipeDetailedDto> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    public List<RecipeDetailedDto> getAllPublicRecipes () {
        return recipeService.getAllPublicRecipes();
    }

    public void deleteRecipe(UUID recipeId) {
        recipeService.deleteRecipe(recipeId);
    }

    public RecipeDetailedDto getRecipeById(UUID recipeId) {
        return recipeService.getRecipeById(recipeId);
    }

    public RecipeDetailedDto addHopToRecipe(RecipeHopDto recipeHopDto){
        return recipeIngredientService.addHopToRecipe(recipeHopDto);
    }

    @Override
    public Set<RecipeHopDto> getAllRecipeHopFromRecipe(UUID recipeId) {
        return recipeIngredientService.getAllRecipeHopFromRecipe(recipeId);
    }

    @Override
    public RecipeDetailedDto addMaltToRecipe(RecipeMaltDto recipeMaltDto) {
        return recipeIngredientService.addMaltToRecipe(recipeMaltDto);
    }

    @Override
    public Set<RecipeMaltDto> getAllRecipeMaltsFromRecipe(UUID recipeId) {
        return recipeIngredientService.getAllRecipeMaltsFromRecipe(recipeId);
    }

    @Override
    public RecipeDetailedDto addRecipeExtraIngredientToRecipe(RecipeExtraIngredientDto recipeExtraIngredientDto) {
        return recipeIngredientService.addExtraIngredientToRecipe(recipeExtraIngredientDto);
    }

    @Override
    public Set<RecipeExtraIngredientDto> getAllRecipeExtraIngredientsFromRecipe(UUID recipeId) {
        return recipeIngredientService.getAllRecipeExtraIngredientsFromRecipe(recipeId);
    }

    @Override
    public RecipeDetailedDto addYeastToRecipe(RecipeYeastDto recipeYeastDto) {
        return recipeIngredientService.addYeastToRecipe(recipeYeastDto);
    }

}
