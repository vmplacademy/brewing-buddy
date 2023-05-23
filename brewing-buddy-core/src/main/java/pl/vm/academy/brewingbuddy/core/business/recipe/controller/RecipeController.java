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
}