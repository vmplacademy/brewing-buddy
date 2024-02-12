package pl.vm.academy.brewingbuddy.core.business.recipe.mapper;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.RecipeExtraIngredient;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public record RecipeExtraIngredientMapper() {

    public RecipeExtraIngredientDto mapRecipeExtraIngredientToDto (RecipeExtraIngredient recipeExtraIngredient) {

        if (recipeExtraIngredient == null) {
            return null;
        }

        if (recipeExtraIngredient.getRecipe() == null) {
            return null;
        }

        return RecipeExtraIngredientDto
                .builder()
                .id(recipeExtraIngredient.getId())
                .extraIngredientId(recipeExtraIngredient.getExtraIngredientId())
                .recipeId(recipeExtraIngredient.getRecipe().getId())
                .measureUnit(recipeExtraIngredient.getMeasureUnit())
                .addingPhase(recipeExtraIngredient.getAddingPhase())
                .addingTime(recipeExtraIngredient.getAddingTime())
                .addingTimeUnit(recipeExtraIngredient.getAddingTimeUnit())
                .build();
    }

    public RecipeExtraIngredient mapRecipeExtraIngredientDtoToEntity (RecipeExtraIngredientDto recipeExtraIngredientDto) {
        if (recipeExtraIngredientDto == null) {
            return null;
        }

        RecipeExtraIngredient recipeExtraIngredient = new RecipeExtraIngredient();

        if (recipeExtraIngredientDto.id() != null) {
            recipeExtraIngredient.setId(recipeExtraIngredientDto.id());
        }

        recipeExtraIngredient.setExtraIngredientId(recipeExtraIngredientDto.extraIngredientId());
        recipeExtraIngredient.setAmount(recipeExtraIngredientDto.amount());
        recipeExtraIngredient.setMeasureUnit(recipeExtraIngredientDto.measureUnit());
        recipeExtraIngredient.setAddingPhase(recipeExtraIngredientDto.addingPhase());
        recipeExtraIngredient.setAddingTime(recipeExtraIngredientDto.addingTime());
        recipeExtraIngredient.setAddingTimeUnit(recipeExtraIngredientDto.addingTimeUnit());

        return recipeExtraIngredient;
    }

    public Set<RecipeExtraIngredientDto> mapRecipeExtraIngredientSetToDtoSet (Set<RecipeExtraIngredient> recipeExtraIngredientSet) {
        if (recipeExtraIngredientSet == null) {
            return Collections.emptySet();
        } else {
            return recipeExtraIngredientSet.stream()
                    .map(this::mapRecipeExtraIngredientToDto)
                    .collect(Collectors.toSet());
        }
    }
}
