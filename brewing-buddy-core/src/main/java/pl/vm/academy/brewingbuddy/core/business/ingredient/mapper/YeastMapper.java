package pl.vm.academy.brewingbuddy.core.business.ingredient.mapper;

import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.YeastDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.Yeast;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record YeastMapper() {
    public YeastDto mapYeastToDto (Yeast yeast) {

        if (yeast == null)
            return null;

        return YeastDto.builder()
                .id(yeast.getId())
                .name(yeast.getName())
                .yeastType(yeast.getYeastType())
                .description(yeast.getDescription())
                .build();
    }

    public Yeast mapYeastDtoToEntity (YeastDto yeastDto) {

        if (yeastDto == null)
            return null;

        Yeast yeast = new Yeast();

        if (yeastDto.id() != null)
            yeast.setId(yeastDto.id());

        yeast.setName(yeastDto.name());
        yeast.setYeastType(yeastDto.yeastType());
        yeast.setDescription(yeastDto.description());

        return yeast;
    }

    public Set<YeastDto> mapYeastsToDtos (List<Yeast> yeasts) {

        if (yeasts.isEmpty()) {
            return new HashSet<>();
        }

        return yeasts.stream().map(this::mapYeastToDto).collect(Collectors.toSet());
    }
}