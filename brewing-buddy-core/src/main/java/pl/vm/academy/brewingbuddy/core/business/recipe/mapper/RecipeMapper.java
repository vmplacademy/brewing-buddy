package pl.vm.academy.brewingbuddy.core.business.recipe.mapper;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;

import java.util.List;

@Component
@NoArgsConstructor
public class RecipeMapper {
    RecipeRepository recipeRepository;
    CalculatedParametersMapper calculatedParametersMapper;

    @Autowired
    public RecipeMapper(RecipeRepository recipeRepository, CalculatedParametersMapper calculatedParametersMapper) {
        this.recipeRepository = recipeRepository;
        this.calculatedParametersMapper = calculatedParametersMapper;
    }

    public RecipeDto mapRecipeToDto(Recipe recipe) {

        if (recipe == null)
            return null;

        RecipeDto recipeDto = RecipeDto.builder()
                .id(recipe.getId())
                .isPublic(recipe.isPublic())
                .recipeName(recipe.getRecipeName())
                .beerStyle(recipe.getBeerStyle())
                .expectedAmountOfBeerInLiters(recipe.getExpectedAmountOfBeerInLiters())
                .boilingProcessTime(recipe.getBoilingProcessTime())
                .waterEvaporationInPercentagePerHour(recipe.getWaterEvaporationInPercentagePerHour())
                .boilingProcessLossInPercentage(recipe.getBoilingProcessLossInPercentage())
                .fermentationProcessLossInPercentage(recipe.getFermentationProcessLossInPercentage())
                .calculatedParametersDto(calculatedParametersMapper.mapParametersToDto(recipe.getRecipeCalculatedParameter()))
                .build();

        return recipeDto;
    }

    public Recipe mapRecipeDtoToEntity(RecipeDto recipeDto) {

        if (recipeDto == null)
            return null;

        Recipe recipe  = new Recipe();

        if (recipeDto.id() != null)
            recipe.setId(recipeDto.id());

        recipe.setPublic(recipeDto.isPublic());
        recipe.setRecipeName(recipeDto.recipeName());
        recipe.setBeerStyle(recipeDto.beerStyle());
        recipe.setExpectedAmountOfBeerInLiters(recipeDto.expectedAmountOfBeerInLiters());
        recipe.setBoilingProcessTime(recipeDto.boilingProcessTime());
        recipe.setWaterEvaporationInPercentagePerHour(recipeDto.waterEvaporationInPercentagePerHour());
        recipe.setBoilingProcessLossInPercentage(recipeDto.boilingProcessLossInPercentage());
        recipe.setFermentationProcessLossInPercentage(recipeDto.fermentationProcessLossInPercentage());
        return recipe;
    }

    public List<RecipeDto> mapRecipeListToDtoList(List<Recipe> recipeList) {
        return recipeList.stream().map(recipe -> mapRecipeToDto(recipe)).toList();
    }
}