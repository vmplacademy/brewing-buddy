package pl.vm.academy.brewingbuddy.core.business.recipe.model;

import jakarta.persistence.Entity;
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

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_recipe_malt")
public class RecipeMalt {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @NotNull
    private UUID maltId;

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private BigDecimal maltAmountInKilos;
    private BigDecimal theoreticalExtractAmountInPercentage;
    private BigDecimal realExtractAmountInPercentage;
    private BigDecimal extractionRateInPercentage;


    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof RecipeMalt))
            return  false;

        return id != null && id.equals(((RecipeMalt) obj).getId());
    }
}