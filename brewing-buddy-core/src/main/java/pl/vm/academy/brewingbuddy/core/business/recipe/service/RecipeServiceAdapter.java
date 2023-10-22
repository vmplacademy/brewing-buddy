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
            throw new IllegalStateException("Recipe with such name already exists!");
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

        Recipe recipe = RecipeIngredientServiceAdapter.findRecipeById(recipeSimpleDto.id(), recipeRepository);
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
        return recipeMapper.mapRecipeToDetailedDto(RecipeIngredientServiceAdapter.findRecipeById(recipeId, recipeRepository));
    }

    @Override
    public void deleteRecipe(UUID id) {
        Recipe recipe = RecipeIngredientServiceAdapter.findRecipeById(id, recipeRepository);
        recipeCalculatedParametersRepository.delete(recipeCalculatedParametersRepository.findByRecipe(recipe));
        recipeRepository.delete(recipe);
    }
}