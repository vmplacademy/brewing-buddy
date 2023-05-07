package pl.vm.academy.brewingbuddy.core.business.recipe.mapper;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;

import java.util.List;
import java.util.UUID;

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

    public RecipeDto recipeToDto (Recipe recipe) {

        if (recipe == null)
            return null;

        RecipeDto recipeDto = RecipeDto.builder()
                .isPublic(recipe.getIsPublic())
                .recipeName(recipe.getRecipeName())
                .beerStyle(recipe.getBeerStyle())
                .expectedAmountOfBeerInLiters(recipe.getExpectedAmountOfBeerInLiters())
                .boilingProcessTime(recipe.getBoilingProcessTime())
                .waterEvaporationInPercentagePerHour(recipe.getWaterEvaporationInPercentagePerHour())
                .boilingProcessLossInPercentage(recipe.getBoilingProcessLossInPercentage())
                .fermentationProcessLossInPercentage(recipe.getFermentationProcessLossInPercentage())
                .calculatedParametersDto(calculatedParametersMapper.parametersToDto(recipe.getRecipeCalculatedParameters()))
                .build();

        return recipeDto;
    }

    public Recipe recipeDtoToEntity (RecipeDto recipeDto) {

        if (recipeDto == null)
            return null;

        Recipe recipe  = new Recipe();

        if (recipeDto.getId() != null)
            recipe.setId(UUID.fromString(recipeDto.getId()));

        recipe.setIsPublic(recipeDto.getIsPublic());
        recipe.setRecipeName(recipeDto.getRecipeName());
        recipe.setBeerStyle(recipeDto.getBeerStyle());
        recipe.setExpectedAmountOfBeerInLiters(recipeDto.getExpectedAmountOfBeerInLiters());
        recipe.setBoilingProcessTime(recipeDto.getBoilingProcessTime());
        recipe.setWaterEvaporationInPercentagePerHour(recipeDto.getWaterEvaporationInPercentagePerHour());
        recipe.setBoilingProcessLossInPercentage(recipeDto.getBoilingProcessLossInPercentage());
        recipe.setFermentationProcessLossInPercentage(recipeDto.getFermentationProcessLossInPercentage());
        return recipe;
    }

    public List<RecipeDto> recipeListToDtoList (List<Recipe> recipeList) {
        return recipeList.stream().map(recipe -> recipeToDto(recipe)).toList();
    }
}