package pl.vm.academy.brewingbuddy.core.business.recipe.mapper;

import lombok.NoArgsConstructor;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeMalt;

import java.util.List;

@NoArgsConstructor
public class RecipeMaltMapper {
    public RecipeMaltDto mapRecipeMaltToDto(RecipeMalt recipeMalt) {

        if (recipeMalt == null)
            return null;

        return RecipeMaltDto.builder()
                .id(recipeMalt.getId())
                .recipeId(recipeMalt.getRecipe().getId())
                .maltId(recipeMalt.getMaltId())
                .maltAmountInKilos(recipeMalt.getMaltAmountInKilos())
                .theoreticalExtractAmountInPercentage(recipeMalt.getTheoreticalExtractAmountInPercentage())
                .realExtractAmountInPercentage(recipeMalt.getRealExtractAmountInPercentage())
                .extractionRateInPercentage(recipeMalt.getExtractionRateInPercentage())
                .build();
    }

    public RecipeMalt mapRecipeMaltDtoToEntity (RecipeMaltDto recipeMaltDto) {

        if (recipeMaltDto == null)
            return null;

        RecipeMalt recipeMalt = new RecipeMalt();

        if (recipeMaltDto.id() != null)
            recipeMalt.setId(recipeMaltDto.id());

        recipeMalt.setMaltId(recipeMaltDto.maltId());
        recipeMalt.setMaltAmountInKilos(recipeMaltDto.maltAmountInKilos());
        recipeMalt.setTheoreticalExtractAmountInPercentage(recipeMaltDto.theoreticalExtractAmountInPercentage());
        recipeMalt.setRealExtractAmountInPercentage(recipeMaltDto.realExtractAmountInPercentage());
        recipeMalt.setExtractionRateInPercentage(recipeMaltDto.extractionRateInPercentage());

        return recipeMalt;
    }

    public List<RecipeMaltDto> mapRecipeMaltListToDtoList (List<RecipeMalt> recipeMaltList) {
        return recipeMaltList.stream().map(this::mapRecipeMaltToDto).toList();
    }
}
