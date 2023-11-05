package pl.vm.academy.brewingbuddy.core.business.recipe.mapper;

public record RecipeCommonMapper(RecipeMapper recipeMapper, RecipeHopMapper recipeHopMapper,
                                 RecipeMaltMapper recipeMaltMapper,
                                 RecipeExtraIngredientMapper recipeExtraIngredientMapper,
                                 RecipeYeastMapper recipeYeastMapper,
                                 RecipeCalculatedParametersMapper recipeCalculatedParametersMapper) {
}
