package pl.vm.academy.brewingbuddy.core.business.recipe.dto;

import lombok.Builder;
import lombok.With;


import java.math.BigDecimal;


@Builder
@With
public record RecipeCalculatedParametersDto(
        BigDecimal waterRequiredForMashingInLiters,
        BigDecimal waterRequiredForSpargingInLiters,
        BigDecimal waterRequiredForWholeProcessInLiters,
        BigDecimal amountOfWaterBeforeBoilingInLiters,
        BigDecimal extractBeforeBoilingInPercentage,
        BigDecimal amountOfHotWort,
        BigDecimal calculatedIbu,
        BigDecimal calculatedColourEBC,
        BigDecimal calculatedExtractInPercentage,
        BigDecimal estimatedAmountOfAlcoholAfterFermentation,
        BigDecimal overallAmountOfMaltInKg,

        // additional parameters for calc
        BigDecimal theoreticalExtractInGrams,
        BigDecimal realExtractInGrams,
        BigDecimal wortWeightInGrams
) {}