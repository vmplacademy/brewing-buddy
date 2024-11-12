package pl.vm.academy.brewingbuddy.core.business.ingredient.mapper;

import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.HopDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.Hop;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record HopMapper() {
    public HopDto mapHopToDto (Hop hop) {

        if (hop == null)
            return null;

        return HopDto.builder()
                .id(hop.getId())
                .name(hop.getName())
                .alfaAcidInPercentage(hop.getAlfaAcidInPercentage())
                .build();
    }

    public Hop mapHopDtoToEntity (HopDto hopDto) {

        if (hopDto == null)
            return null;

        Hop hop = new Hop();

        if (hopDto.id() != null)
            hop.setId(hopDto.id());

        hop.setName(hopDto.name());
        hop.setAlfaAcidInPercentage(hopDto.alfaAcidInPercentage());

        return hop;
    }

    public Set<HopDto> mapHopsToDtos (List<Hop> hops) {

        if (hops.isEmpty()) {
            return new HashSet<>();
        }

        return hops.stream().map(this::mapHopToDto).collect(Collectors.toSet());
    }
}