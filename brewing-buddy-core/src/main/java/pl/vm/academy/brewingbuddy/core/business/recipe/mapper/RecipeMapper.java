package pl.vm.academy.brewingbuddy.core.business.recipe.mapper;

import lombok.AllArgsConstructor;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeSimpleDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;

import java.util.List;

@AllArgsConstructor
public class RecipeMapper {
    private final RecipeCalculatedParametersMapper recipeCalculatedParametersMapper;
    private final RecipeHopMapper recipeHopMapper;
    private final RecipeMaltMapper recipeMaltMapper;
    private final RecipeExtraIngredientMapper recipeExtraIngredientMapper;
    private final RecipeYeastMapper recipeYeastMapper;

    public RecipeDetailedDto mapRecipeToDetailedDto(Recipe recipe) {

        if (recipe == null)
            return null;

        return RecipeDetailedDto.builder()
                .id(recipe.getId())
                .userId(recipe.getUserId())
                .recipeHopDtoSet(recipeHopMapper.mapRecipeHopSetToDtoSet(recipe.getRecipeHops()))
                .recipeMaltDtoSet(recipeMaltMapper.mapRecipeMaltSetToDtoSet(recipe.getRecipeMalts()))
                .recipeExtraIngredientDtoSet(recipeExtraIngredientMapper.mapRecipeExtraIngredientSetToDtoSet(recipe.getRecipeExtraIngredients()))
                .recipeYeastDto(recipeYeastMapper.mapRecipeYeastToDto(recipe.getRecipeYeast()))
                .isPublic(recipe.isPublic())
                .recipeName(recipe.getRecipeName())
                .beerStyle(recipe.getBeerStyle())
                .expectedAmountOfBeerInLiters(recipe.getExpectedAmountOfBeerInLiters())
                .boilingProcessTime(recipe.getBoilingProcessTime())
                .waterEvaporationInPercentagePerHour(recipe.getWaterEvaporationInPercentagePerHour())
                .boilingProcessLossInPercentage(recipe.getBoilingProcessLossInPercentage())
                .fermentationProcessLossInPercentage(recipe.getFermentationProcessLossInPercentage())
                .recipeCalculatedParametersDto(recipeCalculatedParametersMapper.mapParametersToDto(recipe.getRecipeCalculatedParameter()))
                .build();
    }

    public Recipe mapRecipeSimpleDtoToEntity(RecipeSimpleDto recipeSimpleDto, Recipe recipe) {

        if (recipeSimpleDto == null)
            return null;

        if (recipe == null)
            recipe = new Recipe();

        recipe.setPublic(recipeSimpleDto.isPublic());
        recipe.setRecipeName(recipeSimpleDto.recipeName());
        recipe.setBeerStyle(recipeSimpleDto.beerStyle());
        recipe.setExpectedAmountOfBeerInLiters(recipeSimpleDto.expectedAmountOfBeerInLiters());
        recipe.setBoilingProcessTime(recipeSimpleDto.boilingProcessTime());
        recipe.setWaterEvaporationInPercentagePerHour(recipeSimpleDto.waterEvaporationInPercentagePerHour());
        recipe.setBoilingProcessLossInPercentage(recipeSimpleDto.boilingProcessLossInPercentage());
        recipe.setFermentationProcessLossInPercentage(recipeSimpleDto.fermentationProcessLossInPercentage());
        return recipe;
    }

    public List<RecipeDetailedDto> mapRecipeListToDtoList(List<Recipe> recipeList) {

        if (recipeList.isEmpty())
            return null;

        return recipeList.stream().map(this::mapRecipeToDetailedDto).toList();
    }
}