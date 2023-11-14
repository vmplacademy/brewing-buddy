package pl.vm.academy.brewingbuddy.core.business.ingredient.dto;

import lombok.Builder;
import pl.vm.academy.brewingbuddy.core.business.ingredient.model.enums.YeastType;

import java.util.UUID;

@Builder
public record YeastDto(
        UUID id,
        String name,
        String description,
        YeastType yeastType
) {}
