package pl.vm.academy.brewingbuddy.core.business.recipe.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeBasicDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeSimpleDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeYeastDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.RecipeFacade;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipes")
@CrossOrigin(origins = "http://localhost:4200",
    allowedHeaders = "*",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

public class RecipeController {
   private final RecipeFacade recipeFacade;

    @PostMapping
    public RecipeDetailedDto createRecipe(@Valid @RequestBody RecipeSimpleDto recipeSimpleDto) {
        return recipeFacade.createRecipe(recipeSimpleDto);
    }

    @PutMapping
    public RecipeCalculatedParametersDto updateRecipe(@Valid @RequestBody RecipeSimpleDto recipeSimpleDto) {
        return recipeFacade.updateRecipe(recipeSimpleDto);
    }

    @GetMapping
    public List<RecipeBasicDto> getAllRecipes() {
        return recipeFacade.getAllRecipes();
    }

    @GetMapping("/{id}")
    public RecipeDetailedDto getRecipeById(@PathVariable UUID id) {
        return recipeFacade.getRecipeById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe (@PathVariable UUID id) {
        recipeFacade.deleteRecipe(id);
    }

    @PostMapping("/hops")
    public RecipeDetailedDto addHopToRecipe(@Valid @RequestBody RecipeHopDto recipeHopDto) {
        return recipeFacade.addHopToRecipe(recipeHopDto);
    }

    @PostMapping("/malts")
    public RecipeDetailedDto addMaltToRecipe (@Valid @RequestBody RecipeMaltDto recipeMaltDto) {
        return recipeFacade.addMaltToRecipe(recipeMaltDto);
    }

    @PostMapping("/extra-ingredients")
    public RecipeDetailedDto addExtraIngredientToRecipe (@Valid @RequestBody RecipeExtraIngredientDto recipeExtraIngredientDto) {
        return recipeFacade.addRecipeExtraIngredientToRecipe(recipeExtraIngredientDto);
    }

    @PostMapping("/yeast")
    public RecipeDetailedDto addYeastToRecipe (@Valid @RequestBody RecipeYeastDto recipeYeastDto) {
        return recipeFacade.addYeastToRecipe(recipeYeastDto);
    }
}