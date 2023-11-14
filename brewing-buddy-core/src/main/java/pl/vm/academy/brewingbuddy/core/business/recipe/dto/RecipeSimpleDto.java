package pl.vm.academy.brewingbuddy.core.business.recipe.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.time.DurationMax;
import org.hibernate.validator.constraints.time.DurationMin;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.enums.BeerStyle;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

@Builder
public record RecipeSimpleDto(
        UUID id,
        boolean isPublic,
        @NotNull
        @NotBlank
        String recipeName,
        BeerStyle beerStyle,
        @DecimalMin("1")
        @DecimalMax("1000")
        BigDecimal expectedAmountOfBeerInLiters,

        @DecimalMin("1")
        @DecimalMax("99")
        BigDecimal mashingPerformanceInPercentage,

        @DurationMin(minutes = 1)
        @DurationMax(minutes = 1000)
        Duration boilingProcessTime,
        @DecimalMin("1")
        @DecimalMax("99")
        BigDecimal waterEvaporationInPercentagePerHour,
        @DecimalMin("1")
        @DecimalMax("99")
        BigDecimal boilingProcessLossInPercentage,
        @DecimalMin("1")
        @DecimalMax("99")
        BigDecimal fermentationProcessLossInPercentage,
        BigDecimal mashingFactorInLitersPerKg,
        RecipeCalculatedParametersDto recipeCalculatedParametersDto
) {}
