package pl.vm.academy.brewingbuddy.core.business.recipe.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.RecipeFacade;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;

import java.util.List;

@RestController
@AllArgsConstructor
public class RecipeController {
    RecipeFacade recipeFacade;

    @PostMapping("/create-recipe")
    public RecipeDto createRecipe(@RequestBody RecipeDto recipeDto) {
        return recipeFacade.createRecipe(recipeDto);
    }

    @PutMapping("/update-recipe")
    public RecipeDto updateRecipe(@RequestBody RecipeDto recipeDto) {
        return recipeFacade.updateRecipe(recipeDto);
    }

    @GetMapping("recipes/all")
    public List<RecipeDto> getAllRecipes() {
        return recipeFacade.getAllRecipes();
    }

    @DeleteMapping("delete-recipe")
    public HttpStatus deleteRecipe (@RequestBody RecipeDto recipeDto) {
        return recipeFacade.deleteRecipe(recipeDto);
    }


}
