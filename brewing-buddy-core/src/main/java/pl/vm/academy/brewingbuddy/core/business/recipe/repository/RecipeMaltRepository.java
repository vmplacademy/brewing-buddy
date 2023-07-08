package pl.vm.academy.brewingbuddy.core.business.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeHop;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeMalt;

import java.util.List;
import java.util.UUID;

public interface RecipeMaltRepository extends JpaRepository<RecipeMalt, UUID> {
    List<RecipeMalt> findAllByRecipe(Recipe recipe);


}