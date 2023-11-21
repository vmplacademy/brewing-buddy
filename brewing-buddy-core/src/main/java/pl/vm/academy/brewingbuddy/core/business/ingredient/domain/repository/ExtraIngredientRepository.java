package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.ExtraIngredient;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.Malt;

import java.util.Optional;
import java.util.UUID;

public interface ExtraIngredientRepository extends JpaRepository<ExtraIngredient, UUID> {

    Optional<ExtraIngredient> findById(UUID id);
}