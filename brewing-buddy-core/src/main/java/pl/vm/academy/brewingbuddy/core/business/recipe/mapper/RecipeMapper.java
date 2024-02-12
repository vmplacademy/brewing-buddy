package pl.vm.academy.brewingbuddy.core.business.recipe.mapper;

import org.springframework.util.CollectionUtils;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeBasicDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeSimpleDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public record RecipeMapper (RecipeCalculatedParametersMapper recipeCalculatedParametersMapper,
                            RecipeHopMapper recipeHopMapper,
                            RecipeMaltMapper recipeMaltMapper,
                            RecipeExtraIngredientMapper recipeExtraIngredientMapper,
                            RecipeYeastMapper recipeYeastMapper) {

    public RecipeDetailedDto mapRecipeToDetailedDto(Recipe recipe) {

        if (recipe == null) {
            return null;
        }

        return RecipeDetailedDto.builder()
                .id(recipe.getId())
                .userId(recipe.getUserId())
                .recipeHops(recipeHopMapper.mapRecipeHopSetToDtoSet(recipe.getRecipeHops()))
                .recipeMalts(recipeMaltMapper.mapRecipeMaltSetToDtoSet(recipe.getRecipeMalts()))
                .recipeExtraIngredients(recipeExtraIngredientMapper.mapRecipeExtraIngredientSetToDtoSet(recipe.getRecipeExtraIngredients()))
                .recipeYeastDto(recipeYeastMapper.mapRecipeYeastToDto(recipe.getRecipeYeast()))
                .isPublic(recipe.isPublic())
                .recipeName(recipe.getRecipeName())
                .beerStyle(recipe.getBeerStyle())
                .expectedAmountOfBeerInLiters(recipe.getExpectedAmountOfBeerInLiters())
                .mashingPerformanceInPercentage(recipe.getMashingPerformanceInPercentage())
                .boilingProcessTime(recipe.getBoilingProcessTime())
                .waterEvaporationInPercentagePerHour(recipe.getWaterEvaporationInPercentagePerHour())
                .boilingProcessLossInPercentage(recipe.getBoilingProcessLossInPercentage())
                .fermentationProcessLossInPercentage(recipe.getFermentationProcessLossInPercentage())
                .mashingFactorInLitersPerKg(recipe.getMashingFactorInLitersPerKg())
                .recipeCalculatedParametersDto(recipeCalculatedParametersMapper.mapParametersToDto(recipe.getRecipeCalculatedParameter()))
                .build();
    }

    public Recipe mapRecipeSimpleDtoToEntity(RecipeSimpleDto recipeSimpleDto, Recipe recipe) {

        if (recipeSimpleDto == null) {
            return null;
        }

        if (recipe == null) {
            recipe = new Recipe();
        }

        recipe.setPublic(recipeSimpleDto.isPublic());
        recipe.setRecipeName(recipeSimpleDto.recipeName());
        recipe.setBeerStyle(recipeSimpleDto.beerStyle());
        recipe.setExpectedAmountOfBeerInLiters(recipeSimpleDto.expectedAmountOfBeerInLiters());
        recipe.setMashingPerformanceInPercentage(recipeSimpleDto.mashingPerformanceInPercentage());
        recipe.setBoilingProcessTime(recipeSimpleDto.boilingProcessTime());
        recipe.setWaterEvaporationInPercentagePerHour(recipeSimpleDto.waterEvaporationInPercentagePerHour());
        recipe.setBoilingProcessLossInPercentage(recipeSimpleDto.boilingProcessLossInPercentage());
        recipe.setFermentationProcessLossInPercentage(recipeSimpleDto.fermentationProcessLossInPercentage());
        recipe.setMashingFactorInLitersPerKg(recipeSimpleDto.mashingFactorInLitersPerKg());
        return recipe;
    }

    public RecipeBasicDto mapRecipeToBasicDto(Recipe recipe) {

        if (recipe == null) {
            return null;
        }

        return RecipeBasicDto.builder()
                .id(recipe.getId())
                .userId(recipe.getUserId())
                .recipeName(recipe.getRecipeName())
                .beerStyle(recipe.getBeerStyle())
                .isPublic(recipe.isPublic())
                .build();
    }

    public List<RecipeBasicDto> mapRecipeListToBasicDtoList(List<Recipe> recipeList) {

        if (CollectionUtils.isEmpty(recipeList)) {
            return new ArrayList<>();
        }

        return recipeList.stream()
                .map(this::mapRecipeToBasicDto)
                .toList();
    }
}