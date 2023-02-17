package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeHop;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeHopRepository;

public record RecipeHopService(
        RecipeHopRepository recipeHopRepository
) {

    void test() {
        RecipeHop recipeHop = recipeHopRepository.getReferenceById(1L);

        recipeHop.getRecipe().setRecipeName("dupa");
    }
}
