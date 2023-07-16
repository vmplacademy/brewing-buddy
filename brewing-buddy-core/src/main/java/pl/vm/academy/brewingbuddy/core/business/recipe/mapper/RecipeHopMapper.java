package pl.vm.academy.brewingbuddy.core.business.recipe.mapper;

import lombok.NoArgsConstructor;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeHop;

import java.util.List;

@NoArgsConstructor
public class RecipeHopMapper {

    public RecipeHopDto mapRecipeHopToDto(RecipeHop recipeHop) {

        if (recipeHop == null)
            return null;

        return RecipeHopDto.builder()
                .id(recipeHop.getId())
                .recipeId(recipeHop.getRecipe().getId())
                .hopId(recipeHop.getHopId())
                .hopAmountInGrams(recipeHop.getHopAmountInGrams())
                .boilingTimeInMinutes(recipeHop.getBoilingTimeInMinutes())
                .hopUtilizationInPercentage(recipeHop.getHopUtilizationInPercentage())
                .build();
    }

    public RecipeHop mapRecipeHopDtoToEntity (RecipeHopDto recipeHopDto) {

        if (recipeHopDto == null)
            return null;

        RecipeHop recipeHop = new RecipeHop();

        if (recipeHopDto.id() != null)
            recipeHop.setId(recipeHopDto.id());

        recipeHop.setHopId(recipeHopDto.hopId());
        recipeHop.setHopAmountInGrams(recipeHopDto.hopAmountInGrams());
        recipeHop.setHopUtilizationInPercentage(recipeHopDto.hopUtilizationInPercentage());
        recipeHop.setBoilingTimeInMinutes(recipeHopDto.boilingTimeInMinutes());

        return recipeHop;
    }

    public List<RecipeHopDto> mapRecipeHopListToDtoList (List<RecipeHop> recipeHopList) {
        return recipeHopList.stream().map(this::mapRecipeHopToDto).toList();
    }
}
