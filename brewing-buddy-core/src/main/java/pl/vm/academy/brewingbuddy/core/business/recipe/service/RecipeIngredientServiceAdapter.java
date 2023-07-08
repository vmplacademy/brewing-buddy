package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeHopMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMaltMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeHop;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeMalt;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeHopRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeMaltRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class RecipeIngredientServiceAdapter implements RecipeIngredientService {
    private final RecipeRepository recipeRepository;
    private final RecipeHopRepository recipeHopRepository;
    private final RecipeMaltRepository recipeMaltRepository;
    private final RecipeMapper recipeMapper;
    private final RecipeHopMapper recipeHopMapper;
    private final RecipeMaltMapper recipeMaltMapper;

    private final static String ERROR_MESSAGE_RECIPE_ID_NOT_FOUND = "entity with id: %s not found in database";

    @Override
    public RecipeDto addHopToRecipe(RecipeHopDto recipeHopDto) {

        Recipe recipe = recipeRepository.findById(recipeHopDto.recipeId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(ERROR_MESSAGE_RECIPE_ID_NOT_FOUND, recipeHopDto.recipeId())));

        RecipeHop recipeHop = recipeHopMapper.mapRecipeHopDtoToEntity(recipeHopDto);
        recipeHop.setRecipe(recipe);
        recipeHop.setHopId(recipeHopDto.hopId());

        recipeHopRepository.save(recipeHop);

        return recipeMapper.mapRecipeToDto(recipe);
    }

    @Override
    public List<RecipeHopDto> getAllRecipeHopFromRecipe(UUID recipeId) {

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(ERROR_MESSAGE_RECIPE_ID_NOT_FOUND, recipeId)));

        return recipeHopMapper.mapRecipeHopListToDtoList(recipeHopRepository.findAllByRecipe(recipe));
    }

    @Override
    public RecipeDto addMaltToRecipe(RecipeMaltDto recipeMaltDto) {

        Recipe recipe = recipeRepository.findById(recipeMaltDto.recipeId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(ERROR_MESSAGE_RECIPE_ID_NOT_FOUND, recipeMaltDto.recipeId())));

        RecipeMalt recipeMalt = recipeMaltMapper.mapRecipeMaltDtoToEntity(recipeMaltDto);
        recipeMalt.setRecipe(recipe);
        recipeMalt.setMaltId(recipeMaltDto.maltId());

        recipeMaltRepository.save(recipeMalt);

        return recipeMapper.mapRecipeToDto(recipe);
    }

    @Override
    public List<RecipeMaltDto> getAllRecipeMaltsFromRecipe(UUID recipeId) {

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(ERROR_MESSAGE_RECIPE_ID_NOT_FOUND, recipeId)));

        return recipeMaltMapper.mapRecipeMaltListToDtoList(recipeMaltRepository.findAllByRecipe(recipe));
    }
}