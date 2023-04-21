package pl.vm.academy.brewingbuddy.core.business.recipe.converter;

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
public class RecipeConverter {
    RecipeRepository recipeRepository;
    CalculatedParametersConverter calculatedParametersConverter;

    @Autowired
    public RecipeConverter(RecipeRepository recipeRepository, CalculatedParametersConverter calculatedParametersConverter) {
        this.recipeRepository = recipeRepository;
        this.calculatedParametersConverter = calculatedParametersConverter;
    }

    public RecipeDto recipeToDto (Recipe recipe) {
        RecipeDto recipeDto = new RecipeDto();
//        recipeDto.setId(recipe.getId().toString());
        recipeDto.setIsPublic(recipe.getIsPublic());
        recipeDto.setRecipeName(recipe.getRecipeName());
        recipeDto.setBeerStyle(recipe.getBeerStyle());
        recipeDto.setExpectedAmountOfBeerInLiters(recipe.getExpectedAmountOfBeerInLiters());
        recipeDto.setBoilingProcessTime(recipe.getBoilingProcessTime());
        recipeDto.setWaterEvaporationInPercentagePerHour(recipe.getWaterEvaporationInPercentagePerHour());
        recipeDto.setBoilingProcessLossInPercentage(recipe.getBoilingProcessLossInPercentage());
        recipeDto.setFermentationProcessLossInPercentage(recipe.getFermentationProcessLossInPercentage());
        recipeDto.setCalculatedParametersDto(calculatedParametersConverter.parametersToDto(recipe.getRecipeCalculatedParameters()));
        return recipeDto;
    }

    public Recipe recipeDtoToEntity (RecipeDto recipeDto) {
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