package pl.vm.academy.brewingbuddy.core.business.ingredient.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record HopDto(
        UUID id,
        String name,
        BigDecimal alfaAcidInPercentage
) {}
