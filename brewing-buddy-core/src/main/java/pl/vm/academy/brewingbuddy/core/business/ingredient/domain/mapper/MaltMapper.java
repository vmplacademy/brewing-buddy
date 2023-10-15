package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.mapper;

import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.dto.MaltDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.Malt;

public class MaltMapper {
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
}