package pl.vm.academy.brewingbuddy.core.business.recipe.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record RecipeMaltDto(
        UUID id,
        UUID recipeId,
        UUID maltId,
        BigDecimal maltAmountInKilos,
        BigDecimal theoreticalExtractAmountInPercentage,
        BigDecimal realExtractAmountInPercentage,
        BigDecimal extractionRateInPercentage
) {}
