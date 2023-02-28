package pl.vm.academy.brewingbuddy.core.business.recipe.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;

import java.util.UUID;

@Component
public class RecipeConverter {
    RecipeRepository recipeRepository;

    @Autowired
    RecipeConverter(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public RecipeDto recipeToDto (Recipe recipe) {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(recipe.getId().toString());
        recipeDto.setRecipeName(recipe.getRecipeName());
        recipeDto.setBeerStyle(recipe.getBeerStyle());
        recipeDto.setExpectedAmountOfBeerInLiters(recipe.getExpectedAmountOfBeerInLiters());
        recipeDto.setBoilingProcessTime(recipe.getBoilingProcessTime());
        recipeDto.setWaterEvaporationInPercentagePerHour(recipe.getWaterEvaporationInPercentagePerHour());
        recipeDto.setBoilingProcessLossInPercentage(recipe.getBoilingProcessLossInPercentage());
        recipeDto.setFermentationProcessLossInPercentage(recipe.getFermentationProcessLossInPercentage());
        return recipeDto;
    }

    public Recipe recipeDtoToEntity (RecipeDto recipeDto) {
        Recipe recipe  = new Recipe();
        recipe.setId(UUID.fromString(recipeDto.getId()));
        recipe.setRecipeName(recipeDto.getRecipeName());
        recipe.setBeerStyle(recipeDto.getBeerStyle());
        recipe.setExpectedAmountOfBeerInLiters(recipeDto.getExpectedAmountOfBeerInLiters());
        recipe.setBoilingProcessTime(recipeDto.getBoilingProcessTime());
        recipe.setWaterEvaporationInPercentagePerHour(recipeDto.getWaterEvaporationInPercentagePerHour());
        recipe.setBoilingProcessLossInPercentage(recipeDto.getBoilingProcessLossInPercentage());
        recipe.setFermentationProcessLossInPercentage(recipeDto.getFermentationProcessLossInPercentage());
        return recipe;
    }
}