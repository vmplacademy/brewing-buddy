package pl.vm.academy.brewingbuddy.core.business.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeYeast;

import java.util.UUID;

public interface RecipeYeastRepository extends JpaRepository <RecipeYeast, UUID> {
}
