package pl.vm.academy.brewingbuddy.core.business.recipe.dto;

import lombok.Builder;
import org.springframework.boot.convert.DurationUnit;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Builder
public record RecipeHopDto(
        UUID id,
        UUID recipeId,
        UUID hopId,
        BigDecimal hopAmountInGrams,
        @DurationUnit(ChronoUnit.MINUTES)
        Duration boilingTimeInMinutes,
        BigDecimal hopUtilizationInPercentage
) {}