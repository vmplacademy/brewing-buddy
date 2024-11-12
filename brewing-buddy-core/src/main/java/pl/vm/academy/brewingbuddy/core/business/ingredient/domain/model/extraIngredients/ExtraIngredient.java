package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.extraIngredients;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.Ingredient;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.enums.ExtraIngredientType;


@EqualsAndHashCode(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "extraIngredientType", discriminatorType = DiscriminatorType.STRING)
@Data
@Table(name = "t_extra_ingredient")
public abstract class ExtraIngredient extends Ingredient {

    @Enumerated(EnumType.STRING)
    @Column(name = "extraIngredientType", insertable = false, updatable = false)
    private ExtraIngredientType extraIngredientType;
}