package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.extraIngredients;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("JUICE")
@Data
public class Juice extends ExtraIngredient {
    private String juiceFlavor;
}