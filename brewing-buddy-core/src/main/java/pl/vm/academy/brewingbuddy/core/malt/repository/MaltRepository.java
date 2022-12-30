package pl.vm.academy.brewingbuddy.core.malt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.malt.model.entity.Malt;

public interface MaltRepository extends JpaRepository<Malt, Long> {
}
