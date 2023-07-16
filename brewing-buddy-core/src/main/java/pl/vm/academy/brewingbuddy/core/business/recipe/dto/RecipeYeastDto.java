package pl.vm.academy.brewingbuddy.core.business.recipe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.enums.UnitMeasure;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record RecipeYeastDto(
        UUID id,
        @NotNull
        @NotBlank
        UUID recipeId,
        @NotNull
        @NotBlank
        UUID yeastId,
        BigDecimal yeastQuantity
) {}
