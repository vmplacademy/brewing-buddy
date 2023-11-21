package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.mapper;

import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.dto.ExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.ExtraIngredient;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record ExtraIngredientMapper() {
    public ExtraIngredientDto mapExtraIngredientToDto (ExtraIngredient extraIngredient) {

        if (extraIngredient == null)
            return null;

        return ExtraIngredientDto.builder()
                .id(extraIngredient.getId())
                .name(extraIngredient.getName())
                .extraIngredientType(extraIngredient.getExtraIngredientType())
                .build();
    }

    public ExtraIngredient mapExtraIngredientDtoToEntity (ExtraIngredientDto extraIngredientDto) {

        if (extraIngredientDto == null)
            return null;

        ExtraIngredient extraIngredient = new ExtraIngredient();

        if (extraIngredientDto.id() != null)
            extraIngredient.setId(extraIngredientDto.id());

        extraIngredient.setName(extraIngredientDto.name());
        extraIngredient.setExtraIngredientType(extraIngredientDto.extraIngredientType());

        return extraIngredient;
    }

    public Set<ExtraIngredientDto> mapExtraIngredientsToDtos (List<ExtraIngredient> extraIngredients) {

        if (extraIngredients.isEmpty()) {
            return new HashSet<>();
        }

        return extraIngredients.stream().map(this::mapExtraIngredientToDto).collect(Collectors.toSet());
    }
}