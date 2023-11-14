package pl.vm.academy.brewingbuddy.core.business.ingredient.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import pl.vm.academy.brewingbuddy.core.business.ingredient.model.enums.ExtraIngredientType;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_extra_ingredient")
public class ExtraIngredient {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String name;
    @Enumerated(EnumType.STRING)
    private ExtraIngredientType extraIngredientType;
}