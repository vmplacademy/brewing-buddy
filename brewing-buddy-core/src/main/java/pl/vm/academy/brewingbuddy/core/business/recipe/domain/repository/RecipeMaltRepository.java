package pl.vm.academy.brewingbuddy.core.business.recipe.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.RecipeMalt;

import java.util.UUID;

public interface RecipeMaltRepository extends JpaRepository<RecipeMalt, UUID> {
}