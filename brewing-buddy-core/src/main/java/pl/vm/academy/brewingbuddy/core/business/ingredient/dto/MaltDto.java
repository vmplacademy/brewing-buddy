package pl.vm.academy.brewingbuddy.core.business.ingredient.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record MaltDto(
        UUID id,
        String name,
        BigDecimal extractionRateInPercentage,
        BigDecimal meanColorInEbcScale
) {}
