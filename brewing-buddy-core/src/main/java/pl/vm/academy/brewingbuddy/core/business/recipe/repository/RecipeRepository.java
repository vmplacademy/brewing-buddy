package pl.vm.academy.brewingbuddy.core.business.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
    List<Recipe> findAllByUserId (UUID userId);
    List<Recipe> findAllByIsPublic (Boolean isPublic);

    Optional<Recipe> findByRecipeName(String recipeName);

    boolean existsRecipeByRecipeName (String recipeName);

}