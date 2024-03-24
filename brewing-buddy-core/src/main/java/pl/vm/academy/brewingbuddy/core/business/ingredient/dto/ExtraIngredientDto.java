package pl.vm.academy.brewingbuddy.core.business.ingredient.dto;

import lombok.Builder;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.enums.ExtraIngredientType;

import java.util.UUID;

@Builder
public record ExtraIngredientDto(
        UUID id,
        String name,
        ExtraIngredientType extraIngredientType
) {}
