package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.CalculatedParametersMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeCalculatedParameters;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeCalculatedParametersRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RecipeServiceAdapter implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCalculatedParametersRepository recipeCalculatedParametersRepository;
    private final RecipeMapper recipeMapper;
    private final CalculatedParametersMapper calculatedParametersMapper;

    public RecipeServiceAdapter(RecipeRepository recipeRepository,
                                RecipeCalculatedParametersRepository recipeCalculatedParametersRepository,
                                RecipeMapper recipeMapper,
                                CalculatedParametersMapper calculatedParametersMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeCalculatedParametersRepository = recipeCalculatedParametersRepository;
        this.recipeMapper = recipeMapper;
        this.calculatedParametersMapper = calculatedParametersMapper;
    }

    @Override
    public RecipeDto createRecipe(RecipeDto recipeDto) {

        Optional<Recipe> recipeOp = recipeRepository.findRecipeByRecipeName(recipeDto.getRecipeName());
        if (recipeOp.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Recipe with such name already exists!");
        }

        Recipe recipe = recipeMapper.recipeDtoToEntity(recipeDto);
        RecipeCalculatedParameters calculatedParameters = new RecipeCalculatedParameters();

        calculatedParameters = recipeCalculatedParametersRepository.save(calculatedParameters);
        recipe.setRecipeCalculatedParameters(calculatedParameters);
        recipe = recipeRepository.save(recipe);
        calculatedParameters.setRecipe(recipe);
        recipeCalculatedParametersRepository.save(calculatedParameters);

        return recipeMapper.recipeToDto(recipe);
    }

    @Override
    public RecipeDto updateRecipe(RecipeDto recipeDto) {

        Optional recipeOp = recipeRepository.findById(UUID.fromString(recipeDto.getId()));

        if (recipeOp.isPresent()) {
            RecipeCalculatedParameters recipeCalculatedParameters =
                    recipeCalculatedParametersRepository.findByRecipe((Recipe) recipeOp.get());

            Recipe recipe = recipeMapper.recipeDtoToEntity(recipeDto);
            recipe.setRecipeCalculatedParameters(recipeCalculatedParameters);
            recipe = recipeRepository.save(recipe);
            return recipeMapper.recipeToDto(recipe);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity with such id not found in database");
        }
    }

    @Override
    public List<RecipeDto> getAllRecipes() {
        return recipeMapper.recipeListToDtoList(recipeRepository.findAll());
    }

    @Override
    public List<RecipeDto> getAllPublicRecipes() {
        return recipeMapper.recipeListToDtoList(recipeRepository.findAllByIsPublic(true));
    }

    @Override
    public RecipeDto getRecipeById(UUID recipeId) {
        Optional<Recipe> recipeOp = recipeRepository.findById(recipeId);
        if (recipeOp.isPresent()) {
            return recipeMapper.recipeToDto((Recipe) recipeOp.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity with such id not found in database");
        }
    }

    @Override
    public HttpStatus deleteRecipe(RecipeDto recipeDto) {
        Optional recipeOp = recipeRepository.findById(UUID.fromString(recipeDto.getId()));

        if (recipeOp.isPresent()) {
            Recipe recipe = (Recipe) recipeOp.get();
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