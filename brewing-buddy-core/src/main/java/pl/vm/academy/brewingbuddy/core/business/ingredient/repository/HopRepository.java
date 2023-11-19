package pl.vm.academy.brewingbuddy.core.business.ingredient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.business.ingredient.model.Hop;

import java.util.Optional;
import java.util.UUID;

public interface HopRepository extends JpaRepository<Hop, UUID> {

    Optional<Hop> findById(UUID id);
}