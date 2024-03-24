package pl.vm.academy.brewingbuddy.core.business.recipe.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.RecipeExtraIngredient;

import java.util.UUID;

public interface RecipeExtraIngredientRepository extends JpaRepository<RecipeExtraIngredient, UUID> {
}