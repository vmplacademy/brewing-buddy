package pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.ingredient;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.extraIngredients.Juice;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.ingredient.JuiceDto;

public record JuiceMapper() {
  public JuiceDto mapJuiceToDto (Juice juice) {

    if (juice == null)
      return null;

    return JuiceDto.builder()
        .id(juice.getId())
        .name(juice.getName())
        .extraIngredientType(juice.getExtraIngredientType())
        .juiceFlavor(juice.getJuiceFlavor())
        .build();
  }

  public Juice mapJuiceDtoToEntity (JuiceDto juiceDto) {

    if (juiceDto == null)
      return null;

    Juice juice = new Juice();

    if (juice.getId() != null)
      juice.setId(juiceDto.id());

    juice.setName(juice.getName());
    juice.setExtraIngredientType(juiceDto.extraIngredientType());
    juice.setJuiceFlavor(juiceDto.juiceFlavor());

    return juice;
  }

  public Set<JuiceDto> mapJuicesToDto(List<Juice> juices) {
    return juices.stream().map(this::mapJuiceToDto).collect(Collectors.toSet());
  }
}