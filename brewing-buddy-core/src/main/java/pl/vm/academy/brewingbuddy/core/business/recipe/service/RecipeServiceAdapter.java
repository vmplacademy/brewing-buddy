package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeSimpleDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeCalculatedParametersMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeCalculatedParameter;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeCalculatedParametersRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class RecipeServiceAdapter implements RecipeService {
    private static final String ERROR_MESSAGE_RECIPE_ID_NOT_FOUND = "entity with id: %s not found in database";

    private final RecipeRepository recipeRepository;
    private final RecipeCalculatedParametersRepository recipeCalculatedParametersRepository;
    private final RecipeMapper recipeMapper;
    private final RecipeCalculatedParametersMapper recipeCalculatedParametersMapper;

    @Override
    public RecipeDetailedDto createRecipe(RecipeSimpleDto recipeSimpleDto) {

        if (recipeRepository.existsRecipeByRecipeName(recipeSimpleDto.recipeName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Recipe with such name already exists!");
        }

        Recipe recipe = recipeMapper.mapRecipeSimpleDtoToEntity(recipeSimpleDto, null);
        RecipeCalculatedParameter recipeCalculatedParameter = new RecipeCalculatedParameter();

        recipe = recipeRepository.save(recipe);
        recipeCalculatedParameter.setRecipe(recipe);
        recipeCalculatedParameter = recipeCalculatedParametersRepository.save(recipeCalculatedParameter);
        recipe.setRecipeCalculatedParameter(recipeCalculatedParameter);

        return recipeMapper.mapRecipeToDetailedDto(recipe);
    }

    @Override
    public RecipeCalculatedParametersDto updateRecipe(RecipeSimpleDto recipeSimpleDto) {

        Optional<Recipe> recipeOp = recipeRepository.findById(recipeSimpleDto.id());

        if (recipeOp.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity with such id not found in database");

        Recipe recipe = recipeMapper.mapRecipeSimpleDtoToEntity(recipeSimpleDto, recipeOp.get());
        recipe = recipeRepository.save(recipe);

        return recipeCalculatedParametersMapper.mapParametersToDto(recipeCalculatedParametersRepository.findByRecipe(recipe));
    }

    @Override
    public List<RecipeDetailedDto> getAllRecipes() {
        return recipeMapper.mapRecipeListToDtoList(recipeRepository.findAll());
    }

    @Override
    public List<RecipeDetailedDto> getAllPublicRecipes() {
        return recipeMapper.mapRecipeListToDtoList(recipeRepository.findAllByIsPublic(true));
    }

    @Override
    public RecipeDetailedDto getRecipeById(UUID recipeId) {
        Optional<Recipe> recipeOp = recipeRepository.findById(recipeId);

        return recipeOp.map(recipeMapper::mapRecipeToDetailedDto).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(ERROR_MESSAGE_RECIPE_ID_NOT_FOUND, recipeId)));
    }

    @Override
    public void deleteRecipe(UUID id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(ERROR_MESSAGE_RECIPE_ID_NOT_FOUND, id)));

        recipeCalculatedParametersRepository.delete(recipeCalculatedParametersRepository.findByRecipe(recipe));
        recipeRepository.delete(recipe);
    }
    private Recipe findRecipe (UUID recipeId) {
        return recipeRepository.findById(recipeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(ERROR_MESSAGE_RECIPE_ID_NOT_FOUND, recipeId)));
    }
}