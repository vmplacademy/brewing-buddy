package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.dto;

import lombok.Builder;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.enums.YeastType;

import java.util.UUID;

@Builder
public record YeastDto(
        UUID id,
        String name,
        String description,
        YeastType yeastType
) {}
