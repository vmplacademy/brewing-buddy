package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.CalculatedParametersMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeHopMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeCalculatedParameter;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeCalculatedParametersRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeHopRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class RecipeServiceAdapter implements RecipeService {
    private static final String ERROR_MESSAGE_RECIPE_ID_NOT_FOUND = "entity with id: %s not found in database";

    private final RecipeRepository recipeRepository;
    private final RecipeCalculatedParametersRepository recipeCalculatedParametersRepository;
    private final RecipeHopRepository recipeHopRepository;
    private final RecipeMapper recipeMapper;
    private final CalculatedParametersMapper calculatedParametersMapper;
    private final RecipeHopMapper recipeHopMapper;

    @Override
    public RecipeDto createRecipe(RecipeDto recipeDto) {

        if (recipeRepository.existsRecipeByRecipeName(recipeDto.recipeName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Recipe with such name already exists!");
        }

        Recipe recipe = recipeMapper.mapRecipeDtoToEntity(recipeDto);
        RecipeCalculatedParameter calculatedParameters = new RecipeCalculatedParameter();

        calculatedParameters = recipeCalculatedParametersRepository.save(calculatedParameters);
        recipe.setRecipeCalculatedParameter(calculatedParameters);
        recipe = recipeRepository.save(recipe);
        calculatedParameters.setRecipe(recipe);

        return recipeMapper.mapRecipeToDto(recipe);
    }

    @Override
    public RecipeDto updateRecipe(RecipeDto recipeDto) {

        Optional<Recipe> recipeOp = recipeRepository.findById(recipeDto.id());

        if (recipeOp.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity with such id not found in database");

        RecipeCalculatedParameter recipeCalculatedParameter =
                recipeCalculatedParametersRepository.findByRecipe(recipeOp.get());

        Recipe recipe = recipeMapper.mapRecipeDtoToEntity(recipeDto);
        recipe.setRecipeCalculatedParameter(recipeCalculatedParameter);
        recipe = recipeRepository.save(recipe);

        return recipeMapper.mapRecipeToDto(recipe);
    }

    @Override
    public List<RecipeDto> getAllRecipes() {
        return recipeMapper.mapRecipeListToDtoList(recipeRepository.findAll());
    }

    @Override
    public List<RecipeDto> getAllPublicRecipes() {
        return recipeMapper.mapRecipeListToDtoList(recipeRepository.findAllByIsPublic(true));
    }

    @Override
    public RecipeDto getRecipeById(UUID recipeId) {
        Optional<Recipe> recipeOp = recipeRepository.findById(recipeId);

        return recipeOp.map(recipeMapper::mapRecipeToDto).orElseThrow(() ->
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