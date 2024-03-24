package pl.vm.academy.brewingbuddy.core.business.recipe.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.Recipe;

import java.util.List;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
    List<Recipe> findAllByIsPublic (Boolean isPublic);

    boolean existsRecipeByRecipeName (String recipeName);
}