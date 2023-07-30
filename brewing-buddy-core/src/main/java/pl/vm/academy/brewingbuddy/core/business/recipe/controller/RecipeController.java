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
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeSimpleDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeYeastDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.RecipeFacadeAdapter;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipes")
public class RecipeController {
   private final RecipeFacadeAdapter recipeFacadeAdapter;

    @PostMapping
    public RecipeDetailedDto createRecipe(@Valid @RequestBody RecipeSimpleDto recipeSimpleDto) {
        return recipeFacadeAdapter.createRecipe(recipeSimpleDto);
    }

    @PutMapping
    public RecipeCalculatedParametersDto updateRecipe(@Valid @RequestBody RecipeSimpleDto recipeSimpleDto) {
        return recipeFacadeAdapter.updateRecipe(recipeSimpleDto);
    }

    @GetMapping
    public List<RecipeDetailedDto> getAllRecipes() {
        return recipeFacadeAdapter.getAllRecipes();
    }

    @GetMapping("/{id}")
    public RecipeDetailedDto getRecipeById(@PathVariable UUID id) {
        return recipeFacadeAdapter.getRecipeById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe (@PathVariable UUID id) {
        recipeFacadeAdapter.deleteRecipe(id);
    }

    @PostMapping("/hops")
    public RecipeDetailedDto addHopToRecipe(@Valid @RequestBody RecipeHopDto recipeHopDto) {
        return recipeFacadeAdapter.addHopToRecipe(recipeHopDto);
    }

    @GetMapping("/hops/{recipeId}")
    public Set <RecipeHopDto> getAllRecipeHops (@PathVariable UUID recipeId) {
        return recipeFacadeAdapter.getAllRecipeHopFromRecipe(recipeId);
    }

    @PostMapping("/malts")
    public RecipeDetailedDto addMaltToRecipe (@Valid @RequestBody RecipeMaltDto recipeMaltDto) {
        return recipeFacadeAdapter.addMaltToRecipe(recipeMaltDto);
    }

    @GetMapping("/malts/{recipeId}")
    public Set <RecipeMaltDto> getAllRecipeMalts (@PathVariable UUID recipeId) {
        return recipeFacadeAdapter.getAllRecipeMaltsFromRecipe(recipeId);
    }

    @PostMapping("/extra-ingredients")
    public RecipeDetailedDto addExtraIngredientToRecipe (@Valid @RequestBody RecipeExtraIngredientDto recipeExtraIngredientDto) {
        return recipeFacadeAdapter.addRecipeExtraIngredientToRecipe(recipeExtraIngredientDto);
    }

    @GetMapping("/extra-ingredients/{recipeId}")
    public Set<RecipeExtraIngredientDto> getAllRecipeExtraIngredients (@PathVariable UUID recipeId) {
        return recipeFacadeAdapter.getAllRecipeExtraIngredientsFromRecipe(recipeId);
    }

    @PostMapping("/yeast")
    public RecipeDetailedDto addYeastToRecipe (@Valid @RequestBody RecipeYeastDto recipeYeastDto) {
        return recipeFacadeAdapter.addYeastToRecipe(recipeYeastDto);
    }
}