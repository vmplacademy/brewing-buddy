package pl.vm.academy.brewingbuddy.core.business.recipe.mapper;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeYeastDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeYeast;

public record RecipeYeastMapper() {
    public RecipeYeastDto mapRecipeYeastToDto (RecipeYeast recipeYeast) {

        if (recipeYeast == null) {
            return null;
        }

        return RecipeYeastDto.builder()
                .id(recipeYeast.getId())
                .recipeId(recipeYeast.getRecipe().getId())
                .yeastId(recipeYeast.getYeastId())
                .yeastQuantity(recipeYeast.getYeastQuantity())
                .build();
    }

    public RecipeYeast mapRecipeYeastDtoToEntity(RecipeYeastDto recipeYeastDto) {

        if (recipeYeastDto == null) {
            return null;
        }

        RecipeYeast recipeYeast = new RecipeYeast();

        if (recipeYeastDto.id() != null) {
            recipeYeast.setId(recipeYeastDto.id());
        }

        recipeYeast.setYeastId(recipeYeastDto.yeastId());
        recipeYeast.setYeastQuantity(recipeYeastDto.yeastQuantity());

        return recipeYeast;
    }
}