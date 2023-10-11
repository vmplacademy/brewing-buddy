package pl.vm.academy.brewingbuddy.core.business.recipe.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import pl.vm.academy.brewingbuddy.core.business.recipe.model.enums.BeerStyle;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Builder
public record RecipeDetailedDto(
        UUID id,
        UUID userId,
        Set<RecipeMaltDto> recipeMaltDtoSet,
        Set<RecipeHopDto> recipeHopDtoSet,
        Set<RecipeExtraIngredientDto> recipeExtraIngredientDtoSet,
        RecipeYeastDto recipeYeastDto,
        boolean isPublic,
        @NotNull
        @NotBlank
        String recipeName,
        BeerStyle beerStyle,
        @DecimalMin("1")
        @DecimalMax("1000")
        BigDecimal expectedAmountOfBeerInLiters,
        @DecimalMin("10")
        @DecimalMax("100")
        BigDecimal boilingProcessTime,
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



