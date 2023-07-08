package pl.vm.academy.brewingbuddy.core.business.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeHop;

import java.util.List;

public interface RecipeHopRepository extends JpaRepository<RecipeHop, Long> {
    List<RecipeHop> findAllByRecipe(Recipe recipe);
}