package pl.vm.academy.brewingbuddy.core.business.recipe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.enums.AddingPhase;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.enums.MeasureUnit;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Builder
public record RecipeExtraIngredientDto(
        UUID id,
        @NotNull
        @NotBlank
        UUID recipeId,
        @NotNull
        @NotBlank
        UUID extraIngredientId,
        BigDecimal amount,
        MeasureUnit measureUnit,
        AddingPhase addingPhase,
        Duration addingTime,
        TimeUnit addingTimeUnit
) {}
