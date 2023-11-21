package pl.vm.academy.brewingbuddy.core.business.recipe.dto;

import lombok.Builder;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.enums.BeerStyle;

import java.util.UUID;

@Builder
public record RecipeBasicDto(
        UUID id,
        UUID userId,
        String recipeName,
        BeerStyle beerStyle,
        boolean isPublic
) {}