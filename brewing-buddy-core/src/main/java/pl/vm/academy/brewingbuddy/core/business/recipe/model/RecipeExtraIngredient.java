package pl.vm.academy.brewingbuddy.core.business.recipe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.enums.AddingPhase;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.enums.MeasureUnit;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_recipe_extra_ingredient")
public class RecipeExtraIngredient {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @NotNull
    private UUID extraIngredientId;

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private BigDecimal amount;
    private MeasureUnit measureUnit;
    @Enumerated(EnumType.STRING)
    private AddingPhase addingPhase;
    private Duration addingTime;
    @Enumerated(EnumType.STRING)
    private TimeUnit addingTimeUnit;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof RecipeExtraIngredient))
            return  false;

        return id != null && id.equals(((RecipeExtraIngredient) obj).getId());
    }
}