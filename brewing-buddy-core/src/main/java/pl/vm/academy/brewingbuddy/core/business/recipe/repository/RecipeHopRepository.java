package pl.vm.academy.brewingbuddy.core.business.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeHop;

import java.util.List;
import java.util.UUID;

public interface RecipeHopRepository extends JpaRepository<RecipeHop, UUID> {
    List<RecipeHop> findAllByRecipe(Recipe recipe);
}