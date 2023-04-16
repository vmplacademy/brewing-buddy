package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import pl.vm.academy.brewingbuddy.core.business.recipe.converter.CalculatedParametersConverter;
import pl.vm.academy.brewingbuddy.core.business.recipe.converter.RecipeConverter;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.CalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeCalculatedParameters;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeCalculatedParametersRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
public class RecipeService {
    RecipeRepository recipeRepository;
    RecipeCalculatedParametersRepository recipeCalculatedParametersRepository;
    RecipeConverter recipeConverter;
    CalculatedParametersConverter calculatedParametersConverter;

    @Autowired
    RecipeService(RecipeRepository recipeRepository, RecipeCalculatedParametersRepository recipeCalculatedParametersRepository,
                  RecipeConverter recipeConverter, CalculatedParametersConverter calculatedParametersConverter) {
        this.recipeRepository = recipeRepository;
        this.recipeCalculatedParametersRepository = recipeCalculatedParametersRepository;
        this.recipeConverter = recipeConverter;
        this.calculatedParametersConverter = calculatedParametersConverter;

    }

    RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public RecipeDto createRecipe (RecipeDto recipeDto) {

        Optional recipeOp = recipeRepository.findRecipeByRecipeName(recipeDto.getRecipeName());
        if (recipeOp.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Recipe with such name already exists!");
        }

        Recipe recipe = recipeConverter.recipeDtoToEntity(recipeDto);
        RecipeCalculatedParameters calculatedParameters = new RecipeCalculatedParameters();

        calculatedParameters = recipeCalculatedParametersRepository.save(calculatedParameters);
        recipe.setRecipeCalculatedParameters(calculatedParameters);
        recipe = recipeRepository.save(recipe);
        calculatedParameters.setRecipe(recipe);
        recipeCalculatedParametersRepository.save(calculatedParameters);

        return recipeConverter.recipeToDto(recipe);
    }

    RecipeDto updateRecipe (RecipeDto recipeDto) {

        Optional recipeOp = recipeRepository.findById(UUID.fromString(recipeDto.getId()));

        if (recipeOp.isPresent()) {
            RecipeCalculatedParameters recipeCalculatedParameters =
                    recipeCalculatedParametersRepository.findByRecipe((Recipe) recipeOp.get());

            Recipe recipe = recipeConverter.recipeDtoToEntity(recipeDto);
            recipe.setRecipeCalculatedParameters(recipeCalculatedParameters);
            recipe = recipeRepository.save(recipe);
            return recipeConverter.recipeToDto(recipe);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity with such id not found in database");
        }
    }

    List<RecipeDto> getAllRecipes () {
        return recipeConverter.recipeListToDtoList(recipeRepository.findAll());
    }

    public List<RecipeDto> getAllPublicRecipes() {
        return recipeConverter.recipeListToDtoList(recipeRepository.findAllByIsPublic(true));
    }

    public RecipeDto getRecipeById(RecipeDto recipeDto) {
        Optional recipeOp = recipeRepository.findById(UUID.fromString(recipeDto.getId()));
        if (recipeOp.isPresent()) {
            return recipeConverter.recipeToDto((Recipe)recipeOp.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity with such id not found in database");
        }
    }

    HttpStatus deleteRecipe (RecipeDto recipeDto) {
        Optional recipeOp = recipeRepository.findById(UUID.fromString(recipeDto.getId()));

        if (recipeOp.isPresent()) {
            Recipe recipe = (Recipe)recipeOp.get();
            RecipeCalculatedParameters parameters = recipeCalculatedParametersRepository.findByRecipe(recipe);

            parameters.setRecipe(null);
            recipe.setRecipeCalculatedParameters(null);

            recipe = recipeRepository.save(recipe);
            parameters = recipeCalculatedParametersRepository.save(parameters);

            recipeRepository.delete(recipe);
            recipeCalculatedParametersRepository.delete(parameters);

            return HttpStatus.NO_CONTENT;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity with such id not found in database");
        }
    }
}