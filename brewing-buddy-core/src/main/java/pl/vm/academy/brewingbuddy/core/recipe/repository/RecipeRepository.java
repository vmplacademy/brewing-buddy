package pl.vm.academy.brewingbuddy.core.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vm.academy.brewingbuddy.core.recipe.model.entity.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
