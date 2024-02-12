package pl.vm.academy.brewingbuddy.core.business.recipe.dto;

import lombok.Builder;

import lombok.With;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.enums.BeerStyle;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;

@Builder
@With
public record RecipeDetailedDto(
        UUID id,
        UUID userId,
        Set<RecipeMaltDto> recipeMalts,
        Set<RecipeHopDto> recipeHops,
        Set<RecipeExtraIngredientDto> recipeExtraIngredients,
        RecipeYeastDto recipeYeastDto,
        boolean isPublic,
        String recipeName,
        BeerStyle beerStyle,
        BigDecimal expectedAmountOfBeerInLiters,
        BigDecimal mashingPerformanceInPercentage,
        Duration boilingProcessTime,
        BigDecimal waterEvaporationInPercentagePerHour,
        BigDecimal boilingProcessLossInPercentage,
        BigDecimal fermentationProcessLossInPercentage,
        BigDecimal mashingFactorInLitersPerKg,
        RecipeCalculatedParametersDto recipeCalculatedParametersDto
) {}



