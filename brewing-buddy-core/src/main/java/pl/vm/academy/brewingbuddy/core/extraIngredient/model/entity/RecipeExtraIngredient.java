package pl.vm.academy.brewingbuddy.core.extraIngredient.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import pl.vm.academy.brewingbuddy.core.recipe.model.entity.Recipe;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "recipe_extra_ingredient")
public class RecipeExtraIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;
    private String addingPhase;
    private BigDecimal addingTime;
    private String addingTimeUnit;

    @ManyToOne
    @JoinColumn(name = "RECIPE_ID")
    private Recipe recipe;
}
