package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.Hop;

public interface HopRepository extends JpaRepository<Hop, Long> {
}
