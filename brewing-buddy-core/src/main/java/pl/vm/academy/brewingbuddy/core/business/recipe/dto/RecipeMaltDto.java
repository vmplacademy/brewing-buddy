package pl.vm.academy.brewingbuddy.core.business.recipe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record RecipeMaltDto(
        UUID id,
        @NotNull
        @NotBlank
        UUID recipeId,
        @NotNull
        @NotBlank
        UUID maltId,
        BigDecimal maltAmountInKilos
) {}
