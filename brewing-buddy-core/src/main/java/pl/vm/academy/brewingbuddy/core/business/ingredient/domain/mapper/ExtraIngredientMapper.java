package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.mapper;

import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.dto.ExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.ExtraIngredient;

public class ExtraIngredientMapper {
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
}