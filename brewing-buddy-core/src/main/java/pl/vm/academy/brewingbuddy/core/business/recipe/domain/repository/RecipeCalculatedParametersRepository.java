package pl.vm.academy.brewingbuddy.core.business.recipe.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.RecipeCalculatedParameter;

import java.util.UUID;

public interface RecipeCalculatedParametersRepository extends JpaRepository<RecipeCalculatedParameter, UUID> {
    RecipeCalculatedParameter findByRecipe(Recipe recipe);
}
