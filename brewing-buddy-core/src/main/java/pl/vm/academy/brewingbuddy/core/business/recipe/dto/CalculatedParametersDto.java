package pl.vm.academy.brewingbuddy.core.business.recipe.dto;

import lombok.Builder;


import java.math.BigDecimal;


@Builder
public record CalculatedParametersDto(
        BigDecimal mashingFactorInLitersPerKg,
        BigDecimal waterRequiredForMashingInLiters,
        BigDecimal waterRequiredForSpargingInLiters,
        BigDecimal waterRequiredForWholeProcessInLiters,
        BigDecimal amountOfWaterBeforeBoilingInLiters,
        BigDecimal extractBeforeBoilingInPercentage,
        BigDecimal amountOfHotWort,
        BigDecimal calculatedIbu,
        BigDecimal calculatedColourEBC,
        BigDecimal calculatedExtractInPercentage,
        BigDecimal estimatedAmountOfAlcoholAfterFermentation
) {}