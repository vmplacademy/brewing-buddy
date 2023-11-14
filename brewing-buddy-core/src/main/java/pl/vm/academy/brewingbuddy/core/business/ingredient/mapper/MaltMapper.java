package pl.vm.academy.brewingbuddy.core.business.ingredient.mapper;

import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.MaltDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.model.Malt;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record MaltMapper() {
    public MaltDto mapMaltToDto (Malt malt) {

        if (malt == null)
            return null;

        return MaltDto.builder()
                .id(malt.getId())
                .name(malt.getName())
                .extractionRateInPercentage(malt.getExtractionRateInPercentage())
                .meanColorInEbcScale(malt.getMeanColorInEbcScale())
                .build();
    }

    public Malt mapMaltDtoToEntity (MaltDto maltDto) {

        if (maltDto == null)
            return null;

        Malt malt = new Malt();

        if (maltDto.id() != null)
            malt.setId(maltDto.id());

        malt.setName(maltDto.name());
        malt.setExtractionRateInPercentage(maltDto.extractionRateInPercentage());
        malt.setMeanColorInEbcScale(maltDto.meanColorInEbcScale());

        return malt;
    }

    public Set<MaltDto> mapMaltsToDtos(List<Malt> malts) {
        if (malts.isEmpty()) {
            return new HashSet<>();
        }

        return malts.stream().map(this::mapMaltToDto).collect(Collectors.toSet());
    }
}