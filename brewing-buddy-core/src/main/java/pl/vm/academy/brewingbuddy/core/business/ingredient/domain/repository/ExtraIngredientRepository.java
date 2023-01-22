package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.ExtraIngredient;

public interface ExtraIngredientRepository extends JpaRepository<ExtraIngredient, Long> {
}