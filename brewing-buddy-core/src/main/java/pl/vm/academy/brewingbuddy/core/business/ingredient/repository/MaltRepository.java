package pl.vm.academy.brewingbuddy.core.business.ingredient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.business.ingredient.model.Malt;

import java.util.Optional;
import java.util.UUID;

public interface MaltRepository extends JpaRepository<Malt, UUID> {
    Optional<Malt> findById(UUID id);
}