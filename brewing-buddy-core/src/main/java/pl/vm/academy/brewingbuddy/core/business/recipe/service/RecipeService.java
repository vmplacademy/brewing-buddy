package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import pl.vm.academy.brewingbuddy.core.business.recipe.converter.RecipeConverter;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;

import java.util.UUID;

@NoArgsConstructor
class RecipeService {
    RecipeRepository recipeRepository;
    RecipeConverter recipeConverter;
    @Autowired
    RecipeService(RecipeRepository recipeRepository, RecipeConverter recipeConverter) {
        this.recipeRepository = recipeRepository;
        this.recipeConverter = recipeConverter;
    }

    RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    RecipeDto createRecipe (RecipeDto recipeDto) {
        Recipe recipe = new Recipe();
        recipe.setRecipeName(recipeDto.getRecipeName());
        recipe.setBeerStyle(recipeDto.getBeerStyle());
        recipe.setBoilingProcessLossInPercentage(recipeDto.getBoilingProcessLossInPercentage());
        recipe.setBoilingProcessTime(recipeDto.getBoilingProcessTime());
        recipe.setExpectedAmountOfBeerInLiters(recipeDto.getExpectedAmountOfBeerInLiters());
        recipe.setWaterEvaporationInPercentagePerHour(recipeDto.getWaterEvaporationInPercentagePerHour());
        recipe.setFermentationProcessLossInPercentage(recipeDto.getFermentationProcessLossInPercentage());

       return recipeConverter.recipeToDto(recipeRepository.save(recipe));
    }

    RecipeDto updateRecipe (RecipeDto recipeDto) {

        if (recipeRepository.existsById(UUID.fromString(recipeDto.getId()))) {
            Recipe recipe = recipeRepository.save(recipeConverter.recipeDtoToEntity(recipeDto));
            return recipeConverter.recipeToDto(recipe);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity with such id not found in database");
        }
    }


}