package pl.vm.academy.brewingbuddy.core.business.ingredient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.vm.academy.brewingbuddy.core.business.ingredient.model.Yeast;

import java.util.Optional;
import java.util.UUID;

public interface YeastRepository extends JpaRepository<Yeast, UUID> {

    Optional<Yeast> findById(UUID id);
}
