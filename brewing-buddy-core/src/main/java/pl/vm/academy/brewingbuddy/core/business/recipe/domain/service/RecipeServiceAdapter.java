package pl.vm.academy.brewingbuddy.core.business.recipe.domain.service;

import lombok.RequiredArgsConstructor;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeBasicDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeSimpleDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeCalculatedParametersMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.RecipeCalculatedParameter;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.repository.RecipeCalculatedParametersRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.repository.RecipeRepository;

import java.util.List;
import java.util.UUID;
import pl.vm.academy.brewingbuddy.core.errorhandling.RecipeAlreadyExistsException;

@RequiredArgsConstructor
public class RecipeServiceAdapter implements RecipeService {
    private static final String ERROR_MESSAGE_RECIPE_ID_NOT_FOUND = "entity with id: %s not found in database";

    private final RecipeRepository recipeRepository;
    private final RecipeCalculatedParametersRepository recipeCalculatedParametersRepository;
    private final RecipeMapper recipeMapper;
    private final RecipeCalculatedParametersMapper recipeCalculatedParametersMapper;

    @Override
    public RecipeDetailedDto createRecipe(RecipeSimpleDto recipeSimpleDto) {

        if (recipeSimpleDto == null) {
            throw new IllegalArgumentException("RecipeSimpleDto cannot be null");
        }

        if (recipeRepository.existsRecipeByRecipeName(recipeSimpleDto.recipeName())) {
            throw new RecipeAlreadyExistsException("Recipe with such name already exists!");
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

        Recipe recipe = findRecipeById(recipeSimpleDto.id(), recipeRepository);
        recipe = recipeRepository.save(recipe);

        return recipeCalculatedParametersMapper.mapParametersToDto(recipeCalculatedParametersRepository.findByRecipe(recipe));
    }

    @Override
    public List<RecipeBasicDto> getAllRecipes() {
        return recipeMapper.mapRecipeListToBasicDtoList(recipeRepository.findAll());
    }

    @Override
    public List<RecipeBasicDto> getAllPublicRecipes() {
        return recipeMapper.mapRecipeListToBasicDtoList(recipeRepository.findAllByIsPublic(true));
    }

    @Override
    public RecipeDetailedDto getRecipeById(UUID recipeId) {
        return recipeMapper.mapRecipeToDetailedDto(findRecipeById(recipeId, recipeRepository));
    }

    @Override
    public void deleteRecipe(UUID id) {
        Recipe recipe = findRecipeById(id, recipeRepository);
        recipeCalculatedParametersRepository.delete(recipeCalculatedParametersRepository.findByRecipe(recipe));
        recipeRepository.delete(recipe);
    }

    static Recipe findRecipeById (UUID recipeId, RecipeRepository recipeRepository) {
        return recipeRepository.findById(recipeId).orElseThrow(() ->
                new IllegalStateException(String.format(ERROR_MESSAGE_RECIPE_ID_NOT_FOUND, recipeId)));
    }
}