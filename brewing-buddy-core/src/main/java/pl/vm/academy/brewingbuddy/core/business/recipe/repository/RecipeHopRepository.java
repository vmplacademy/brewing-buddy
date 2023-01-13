package pl.vm.academy.brewingbuddy.core.business.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeHop;

public interface RecipeHopRepository extends JpaRepository<RecipeHop, Long> {
}
