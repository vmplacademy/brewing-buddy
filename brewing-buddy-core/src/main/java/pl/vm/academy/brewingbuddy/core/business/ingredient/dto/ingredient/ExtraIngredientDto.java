package pl.vm.academy.brewingbuddy.core.business.ingredient.dto.ingredient;

import java.util.UUID;
import lombok.Builder;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.enums.ExtraIngredientType;

@Builder
public record ExtraIngredientDto(
    UUID id,
    String name,
    ExtraIngredientType extraIngredientType
) {}