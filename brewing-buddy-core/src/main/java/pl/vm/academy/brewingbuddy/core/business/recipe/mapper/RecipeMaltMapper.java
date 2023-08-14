package pl.vm.academy.brewingbuddy.core.business.recipe.mapper;

import lombok.NoArgsConstructor;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeMalt;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

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

        return recipeMalt;
    }

    public Set<RecipeMaltDto> mapRecipeMaltSetToDtoSet(Set<RecipeMalt> recipeMaltSet) {
        if (recipeMaltSet == null)
            return Collections.emptySet();
        else
            return recipeMaltSet.stream().map(this::mapRecipeMaltToDto).collect(Collectors.toSet());
    }
}
