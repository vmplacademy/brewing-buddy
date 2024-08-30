package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.enums.YeastType;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "t_yeast")
public class Yeast extends Ingredient {
    private String description;
    @Enumerated
    private YeastType yeastType;
}
