package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "t_malt")
public class Malt extends Ingredient {

    private BigDecimal extractionRateInPercentage;
    private BigDecimal meanColorInEbcScale;
}