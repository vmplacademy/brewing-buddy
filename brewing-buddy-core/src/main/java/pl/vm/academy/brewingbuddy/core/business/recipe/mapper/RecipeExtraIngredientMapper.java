package pl.vm.academy.brewingbuddy.core.business.recipe.mapper;

import lombok.NoArgsConstructor;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeExtraIngredient;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public class RecipeExtraIngredientMapper {

    public RecipeExtraIngredientDto mapRecipeExtraIngredientToDto (RecipeExtraIngredient recipeExtraIngredient) {

        if (recipeExtraIngredient == null)
            return null;

        return RecipeExtraIngredientDto
                .builder()
                .id(recipeExtraIngredient.getId())
                .extraIngredientId(recipeExtraIngredient.getExtraIngredientId())
                .recipeId(recipeExtraIngredient.getRecipe().getId())
                .unitMeasure(recipeExtraIngredient.getUnitMeasure())
                .addingPhase(recipeExtraIngredient.getAddingPhase())
                .addingTimeDurationFromStartOfSelectedPhase(recipeExtraIngredient.getAddingTimeDurationFromStartOfSelectedPhase())
                .addingTimeUnit(recipeExtraIngredient.getAddingTimeUnit())
                .build();
    }

    public RecipeExtraIngredient mapRecipeExtraIngredientDtoToEntity (RecipeExtraIngredientDto recipeExtraIngredientDto) {
        if (recipeExtraIngredientDto == null)
            return null;

        RecipeExtraIngredient recipeExtraIngredient = new RecipeExtraIngredient();

        if (recipeExtraIngredientDto.id() != null)
            recipeExtraIngredient.setId(recipeExtraIngredientDto.id());

        recipeExtraIngredient.setExtraIngredientId(recipeExtraIngredientDto.extraIngredientId());
        recipeExtraIngredient.setAmount(recipeExtraIngredientDto.amount());
        recipeExtraIngredient.setUnitMeasure(recipeExtraIngredientDto.unitMeasure());
        recipeExtraIngredient.setAddingPhase(recipeExtraIngredientDto.addingPhase());
        recipeExtraIngredient.setAddingTimeDurationFromStartOfSelectedPhase(recipeExtraIngredientDto.addingTimeDurationFromStartOfSelectedPhase());
        recipeExtraIngredient.setAddingTimeUnit(recipeExtraIngredientDto.addingTimeUnit());

        return recipeExtraIngredient;
    }

    public Set<RecipeExtraIngredientDto> mapRecipeExtraIngredientSetToDtoSet (Set<RecipeExtraIngredient> recipeExtraIngredientSet) {
        if (recipeExtraIngredientSet == null)
            return Collections.emptySet();
        else
            return recipeExtraIngredientSet.stream().map(this::mapRecipeExtraIngredientToDto).collect(Collectors.toSet());
    }
}
