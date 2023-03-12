package pl.vm.academy.brewingbuddy.core.business.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeCalculatedParameters;

import java.util.UUID;

public interface RecipeCalculatedParametersRepository extends JpaRepository<RecipeCalculatedParameters, UUID> {
    RecipeCalculatedParameters findByRecipe(Recipe recipe);
}
