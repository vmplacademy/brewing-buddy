package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeYeastDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeExtraIngredientMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeHopMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMaltMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeYeastMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeExtraIngredient;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeHop;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeMalt;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeYeast;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeExtraIngredientRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeHopRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeMaltRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeYeastRepository;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class RecipeIngredientServiceAdapter implements RecipeIngredientService {
    private final RecipeRepository recipeRepository;
    private final RecipeHopRepository recipeHopRepository;
    private final RecipeMaltRepository recipeMaltRepository;
    private final RecipeExtraIngredientRepository recipeExtraIngredientRepository;
    private final RecipeYeastRepository recipeYeastRepository;
    private final RecipeMapper recipeMapper;
    private final RecipeHopMapper recipeHopMapper;
    private final RecipeMaltMapper recipeMaltMapper;
    private final RecipeExtraIngredientMapper recipeExtraIngredientMapper;
    private final RecipeYeastMapper recipeYeastMapper;

    private static final String ERROR_MESSAGE_RECIPE_ID_NOT_FOUND = "entity with id: %s not found in database";

    @Override
    public RecipeDetailedDto addHopToRecipe(RecipeHopDto recipeHopDto) {

        Recipe recipe = findRecipeById(recipeHopDto.recipeId());

        RecipeHop recipeHop = recipeHopMapper.mapRecipeHopDtoToEntity(recipeHopDto);
        recipe.addRecipeHop(recipeHop);

        recipeRepository.save(recipe);

        return recipeMapper.mapRecipeToDetailedDto(recipe);
    }

    @Override
    public Set<RecipeHopDto> getAllRecipeHopFromRecipe(UUID recipeId) {

        Recipe recipe = findRecipeById(recipeId);

        return recipeHopMapper.mapRecipeHopSetToDtoSet(recipeHopRepository.findAllByRecipe(recipe));
    }

    @Override
    public RecipeDetailedDto addMaltToRecipe(RecipeMaltDto recipeMaltDto) {

        Recipe recipe = findRecipeById(recipeMaltDto.recipeId());

        RecipeMalt recipeMalt = recipeMaltMapper.mapRecipeMaltDtoToEntity(recipeMaltDto);
        recipe.addRecipeMalt(recipeMalt);

        recipeRepository.save(recipe);

        return recipeMapper.mapRecipeToDetailedDto(recipe);
    }

    @Override
    public Set<RecipeMaltDto> getAllRecipeMaltsFromRecipe(UUID recipeId) {

        Recipe recipe = findRecipeById(recipeId);

        return recipeMaltMapper.mapRecipeMaltSetToDtoSet(recipeMaltRepository.findAllByRecipe(recipe));
    }

    @Override
    public RecipeDetailedDto addExtraIngredientToRecipe(RecipeExtraIngredientDto recipeExtraIngredientDto) {

        Recipe recipe = findRecipeById(recipeExtraIngredientDto.recipeId());

        RecipeExtraIngredient recipeExtraIngredient = recipeExtraIngredientMapper.mapRecipeExtraIngredientDtoToEntity(recipeExtraIngredientDto);
        recipe.addRecipeExtraIngredient(recipeExtraIngredient);

        recipeRepository.save(recipe);

        return recipeMapper.mapRecipeToDetailedDto(recipe);
    }

    @Override
    public Set<RecipeExtraIngredientDto> getAllRecipeExtraIngredientsFromRecipe(UUID recipeId) {
        Recipe recipe = findRecipeById(recipeId);

        return recipeExtraIngredientMapper.mapRecipeExtraIngredientSetToDtoSet(recipeExtraIngredientRepository.findAllByRecipe(recipe));
    }

    @Override
    public RecipeDetailedDto addYeastToRecipe(RecipeYeastDto recipeYeastDto) {
        Recipe recipe = findRecipeById(recipeYeastDto.recipeId());

        RecipeYeast recipeYeast = recipeYeastMapper.mapRecipeYeastDtoToEntity(recipeYeastDto);
        recipe.setRecipeYeast(recipeYeast);

        recipeRepository.save(recipe);

        return recipeMapper.mapRecipeToDetailedDto(recipe);
    }

    private Recipe findRecipeById (UUID recipeId) {
        return recipeRepository.findById(recipeId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(ERROR_MESSAGE_RECIPE_ID_NOT_FOUND, recipeId)));
    }

}
