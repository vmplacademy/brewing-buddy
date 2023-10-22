package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.mapper;


import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.dto.HopDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.Hop;

public class HopMapper {
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
}