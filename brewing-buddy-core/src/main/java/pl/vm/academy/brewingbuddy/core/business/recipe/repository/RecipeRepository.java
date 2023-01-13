package pl.vm.academy.brewingbuddy.core.business.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
