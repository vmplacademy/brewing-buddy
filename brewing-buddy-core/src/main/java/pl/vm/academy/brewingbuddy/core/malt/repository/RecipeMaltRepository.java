package pl.vm.academy.brewingbuddy.core.malt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.malt.model.entity.RecipeMalt;

public interface RecipeMaltRepository extends JpaRepository<RecipeMalt, Long> {
}
