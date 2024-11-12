package pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.ingredient;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.extraIngredients.Lactose;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.ingredient.LactoseDto;

public record LactoseMapper() {
  public LactoseDto mapLactoseToDto (Lactose lactose) {

    if (lactose == null) {
      return null;
    }

    return LactoseDto.builder()
        .id(lactose.getId())
        .name(lactose.getName())
        .type(lactose.getExtraIngredientType())
        .brand(lactose.getLactoseBrand())
        .build();
  }

  public Lactose mapLactoseDtoToEntity (LactoseDto lactoseDto) {

    if (lactoseDto == null)
      return null;

    Lactose lactose = new Lactose();

    if (lactose.getId() != null)
      lactose.setId(lactoseDto.id());

    lactose.setName(lactoseDto.name());
    lactose.setExtraIngredientType(lactoseDto.type());
    lactose.setLactoseBrand(lactoseDto.brand());

    return lactose;
  }

  public Set<LactoseDto> mapJuicesToDto(List<Lactose> lactose) {
    return lactose.stream()
        .map(this::mapLactoseToDto)
        .collect(Collectors.toSet());
  }
}