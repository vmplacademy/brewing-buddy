package pl.vm.academy.brewingbuddy.core.extraIngredient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.extraIngredient.model.entity.ExtraIngredient;

public interface ExtraIngredientRepository extends JpaRepository<ExtraIngredient, Long> {
}
