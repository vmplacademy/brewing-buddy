package pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.ingredient;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.extraIngredients.ExtraIngredient;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.ingredient.ExtraIngredientDto;

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

    if (extraIngredient.getId() != null)
      extraIngredient.setId(extraIngredientDto.id());

    extraIngredient.setName(extraIngredient.getName());
    extraIngredient.setExtraIngredientType(extraIngredientDto.extraIngredientType());

    return extraIngredient;
  }

  public Set<ExtraIngredientDto> mapExtraIngredientsToDto(List<ExtraIngredient> extraIngredients) {
    return extraIngredients.stream().map(this::mapExtraIngredientToDto).collect(Collectors.toSet());
  }

}
