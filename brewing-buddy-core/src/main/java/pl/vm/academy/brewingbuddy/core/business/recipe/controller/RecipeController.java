package pl.vm.academy.brewingbuddy.core.business.recipe.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
import pl.vm.academy.brewingbuddy.core.business.recipe.service.RecipeFacade;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipes")
public class RecipeController {
   private final  RecipeFacade recipeFacade;

    @PostMapping
    public RecipeDto createRecipe(@Valid @RequestBody RecipeDto recipeDto) {
        return recipeFacade.createRecipe(recipeDto);
    }

    @PutMapping
    public RecipeDto updateRecipe(@Valid @RequestBody RecipeDto recipeDto) {
        return recipeFacade.updateRecipe(recipeDto);
    }

    @GetMapping("/all")
    public List<RecipeDto> getAllRecipes() {
        return recipeFacade.getAllRecipes();
    }

    @GetMapping
    public RecipeDto getRecipeById(@RequestBody RecipeDto recipeDto) {
        return recipeFacade.getRecipeById(recipeDto.id());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRecipe (@PathVariable UUID id) {
        recipeFacade.deleteRecipe(id);
    }
}