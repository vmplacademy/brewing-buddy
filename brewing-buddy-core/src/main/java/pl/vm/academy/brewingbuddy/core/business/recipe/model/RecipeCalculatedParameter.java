package pl.vm.academy.brewingbuddy.core.business.recipe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "t_recipe_calculated_parameter")
public class RecipeCalculatedParameter {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Recipe recipe;

    private BigDecimal waterRequiredForMashingInLiters;
    private BigDecimal waterRequiredForSpargingInLiters;
    private BigDecimal waterRequiredForWholeProcessInLiters;
    private BigDecimal amountOfWaterBeforeBoilingInLiters;
    private BigDecimal extractBeforeBoilingInPercentage;
    private BigDecimal amountOfHotWort;

    private BigDecimal calculatedIbu;
    private BigDecimal calculatedColourEBC;
    private BigDecimal calculatedExtractInPercentage;
    private BigDecimal estimatedAmountOfAlcoholAfterFermentation;

    private BigDecimal overallAmountOfMaltInKg;
}