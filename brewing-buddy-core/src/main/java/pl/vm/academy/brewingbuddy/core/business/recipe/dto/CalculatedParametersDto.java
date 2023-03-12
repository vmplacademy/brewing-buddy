package pl.vm.academy.brewingbuddy.core.business.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CalculatedParametersDto {
    private BigDecimal mashingFactorInLitersPerKg;
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
}
