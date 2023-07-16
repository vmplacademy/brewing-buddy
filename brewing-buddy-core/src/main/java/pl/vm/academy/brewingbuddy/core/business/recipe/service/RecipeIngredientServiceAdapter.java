package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeExtraIngredientMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeHopMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMaltMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeExtraIngredient;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeHop;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeMalt;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeExtraIngredientRepository;
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
    private final RecipeExtraIngredientRepository recipeExtraIngredientRepository;
    private final RecipeMapper recipeMapper;
    private final RecipeHopMapper recipeHopMapper;
    private final RecipeMaltMapper recipeMaltMapper;
    private final RecipeExtraIngredientMapper recipeExtraIngredientMapper;

    private static final String ERROR_MESSAGE_RECIPE_ID_NOT_FOUND = "entity with id: %s not found in database";

    @Override
    public RecipeDto addHopToRecipe(RecipeHopDto recipeHopDto) {

        Recipe recipe = findRecipeById(recipeHopDto.recipeId());

        RecipeHop recipeHop = recipeHopMapper.mapRecipeHopDtoToEntity(recipeHopDto);
        recipeHop.setRecipe(recipe);

        recipeHopRepository.save(recipeHop);

        return recipeMapper.mapRecipeToDto(recipe);
    }

    @Override
    public List<RecipeHopDto> getAllRecipeHopFromRecipe(UUID recipeId) {

        Recipe recipe = findRecipeById(recipeId);

        return recipeHopMapper.mapRecipeHopListToDtoList(recipeHopRepository.findAllByRecipe(recipe));
    }

    @Override
    public RecipeDto addMaltToRecipe(RecipeMaltDto recipeMaltDto) {

        Recipe recipe = findRecipeById(recipeMaltDto.recipeId());

        RecipeMalt recipeMalt = recipeMaltMapper.mapRecipeMaltDtoToEntity(recipeMaltDto);
        recipeMalt.setRecipe(recipe);

        recipeMaltRepository.save(recipeMalt);

        return recipeMapper.mapRecipeToDto(recipe);
    }

    @Override
    public List<RecipeMaltDto> getAllRecipeMaltsFromRecipe(UUID recipeId) {

        Recipe recipe = findRecipeById(recipeId);

        return recipeMaltMapper.mapRecipeMaltListToDtoList(recipeMaltRepository.findAllByRecipe(recipe));
    }

    @Override
    public RecipeDto addExtraIngredientToRecipe(RecipeExtraIngredientDto recipeExtraIngredientDto) {

        Recipe recipe = findRecipeById(recipeExtraIngredientDto.recipeId());

        RecipeExtraIngredient recipeExtraIngredient = recipeExtraIngredientMapper.mapRecipeExtraIngredientDtoToEntity(recipeExtraIngredientDto);
        recipeExtraIngredient.setRecipe(recipe);

        recipeExtraIngredientRepository.save(recipeExtraIngredient);

        return recipeMapper.mapRecipeToDto(recipe);
    }

    @Override
    public List<RecipeExtraIngredientDto> getAllRecipeExtraIngredientsFromRecipe(UUID recipeId) {
        Recipe recipe = findRecipeById(recipeId);

        return recipeExtraIngredientMapper.mapRecipeExtraIngredientListToDtoList(recipeExtraIngredientRepository.findAllByRecipe(recipe));
    }

    private Recipe findRecipeById (UUID recipeId) {
        return recipeRepository.findById(recipeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(ERROR_MESSAGE_RECIPE_ID_NOT_FOUND, recipeId)));
    }

}
