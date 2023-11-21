package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.Hop;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.Malt;

import java.util.Optional;
import java.util.UUID;

public interface HopRepository extends JpaRepository<Hop, UUID> {

    Optional<Hop> findById(UUID id);
}