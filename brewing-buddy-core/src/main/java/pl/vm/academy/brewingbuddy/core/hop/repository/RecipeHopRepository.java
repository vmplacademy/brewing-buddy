package pl.vm.academy.brewingbuddy.core.hop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.hop.model.entity.RecipeHop;

public interface RecipeHopRepository extends JpaRepository<RecipeHop, Long> {
}
