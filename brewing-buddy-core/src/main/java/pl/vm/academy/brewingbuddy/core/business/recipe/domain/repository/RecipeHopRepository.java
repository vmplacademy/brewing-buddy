package pl.vm.academy.brewingbuddy.core.business.recipe.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.RecipeHop;


import java.util.UUID;

public interface RecipeHopRepository extends JpaRepository<RecipeHop, UUID> {
}