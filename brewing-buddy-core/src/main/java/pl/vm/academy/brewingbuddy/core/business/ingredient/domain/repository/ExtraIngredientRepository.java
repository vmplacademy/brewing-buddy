package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.extraIngredients.ExtraIngredient;

import java.util.Optional;
import java.util.UUID;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.extraIngredients.Juice;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.extraIngredients.Lactose;

@Repository
public interface ExtraIngredientRepository extends JpaRepository<ExtraIngredient, UUID> {

    Optional<ExtraIngredient> findById(UUID id);

    @Query("select i from ExtraIngredient i WHERE TYPE(i) = Juice")
    List<Juice> finaAllJuices();

    @Query("select i from ExtraIngredient i WHERE TYPE(i) = Lactose")
    List<Lactose> finaAllLactose();
}