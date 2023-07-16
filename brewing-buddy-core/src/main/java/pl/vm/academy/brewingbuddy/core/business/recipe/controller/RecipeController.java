package pl.vm.academy.brewingbuddy.core.business.recipe.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeYeastDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.RecipeFacadeAdapter;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipes")
public class RecipeController {
   private final RecipeFacadeAdapter recipeFacadeAdapter;

    @PostMapping
    public RecipeDto createRecipe(@Valid @RequestBody RecipeDto recipeDto) {
        return recipeFacadeAdapter.createRecipe(recipeDto);
    }

    @PutMapping
    public RecipeDto updateRecipe(@Valid @RequestBody RecipeDto recipeDto) {
        return recipeFacadeAdapter.updateRecipe(recipeDto);
    }

    @GetMapping
    public List<RecipeDto> getAllRecipes() {
        return recipeFacadeAdapter.getAllRecipes();
    }

    @GetMapping("/{id}")
    public RecipeDto getRecipeById(@PathVariable UUID id) {
        return recipeFacadeAdapter.getRecipeById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe (@PathVariable UUID id) {
        recipeFacadeAdapter.deleteRecipe(id);
    }

    @PostMapping("/hops")
    public RecipeDto addHopToRecipe(@Valid @RequestBody RecipeHopDto recipeHopDto) {
        return recipeFacadeAdapter.addHopToRecipe(recipeHopDto);
    }

    @GetMapping("/hops/{recipeId}")
    public List <RecipeHopDto> getAllRecipeHops (@PathVariable UUID recipeId) {
        return recipeFacadeAdapter.getAllRecipeHopFromRecipe(recipeId);
    }

    @PostMapping("/malts")
    public RecipeDto addMaltToRecipe (@Valid @RequestBody RecipeMaltDto recipeMaltDto) {
        return recipeFacadeAdapter.addMaltToRecipe(recipeMaltDto);
    }

    @GetMapping("/malts/{recipeId}")
    public List <RecipeMaltDto> getAllRecipeMalts (@PathVariable UUID recipeId) {
        return recipeFacadeAdapter.getAllRecipeMaltsFromRecipe(recipeId);
    }

    @PostMapping("/extra-ingredients")
    public RecipeDto addExtraIngredientToRecipe (@Valid @RequestBody RecipeExtraIngredientDto recipeExtraIngredientDto) {
        return recipeFacadeAdapter.addRecipeExtraIngredientToRecipe(recipeExtraIngredientDto);
    }

    @GetMapping("/extra-ingredients/{recipeId}")
    public List<RecipeExtraIngredientDto> getAllRecipeExtraIngredients (@PathVariable UUID recipeId) {
        return recipeFacadeAdapter.getAllRecipeExtraIngredientsFromRecipe(recipeId);
    }

    @PostMapping("/yeast")
    public RecipeDto addYeastToRecipe (@Valid @RequestBody RecipeYeastDto recipeYeastDto) {
        return recipeFacadeAdapter.addYeastToRecipe(recipeYeastDto);
    }
}