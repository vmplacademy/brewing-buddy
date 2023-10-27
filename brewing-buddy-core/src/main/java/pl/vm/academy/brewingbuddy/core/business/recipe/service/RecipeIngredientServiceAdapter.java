package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import lombok.RequiredArgsConstructor;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeYeastDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeCommonMapper;
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

import java.util.UUID;

@RequiredArgsConstructor
public class RecipeIngredientServiceAdapter implements RecipeIngredientService {
    private final RecipeRepository recipeRepository;
    private final RecipeHopRepository recipeHopRepository;
    private final RecipeMaltRepository recipeMaltRepository;
    private final RecipeExtraIngredientRepository recipeExtraIngredientRepository;
    private final RecipeYeastRepository recipeYeastRepository;
    private final RecipeCommonMapper recipeCommonMapper;

    private static final String ERROR_MESSAGE_RECIPE_ID_NOT_FOUND = "entity with id: %s not found in database";

    @Override
    public RecipeDetailedDto addHopToRecipe(RecipeHopDto recipeHopDto) {

        Recipe recipe = findRecipeById(recipeHopDto.recipeId(), recipeRepository);

        RecipeHop recipeHop = recipeCommonMapper.recipeHopMapper().mapRecipeHopDtoToEntity(recipeHopDto);
        recipe.addRecipeHop(recipeHop);

        recipe = recipeRepository.save(recipe);

        return recipeCommonMapper.recipeMapper().mapRecipeToDetailedDto(recipe);
    }

    @Override
    public RecipeDetailedDto addMaltToRecipe(RecipeMaltDto recipeMaltDto) {

        Recipe recipe = findRecipeById(recipeMaltDto.recipeId(), recipeRepository);

        RecipeMalt recipeMalt = recipeCommonMapper.recipeMaltMapper().mapRecipeMaltDtoToEntity(recipeMaltDto);
        recipe.addRecipeMalt(recipeMalt);

        recipe = recipeRepository.save(recipe);

        return recipeCommonMapper.recipeMapper().mapRecipeToDetailedDto(recipe);
    }

    @Override
    public RecipeDetailedDto addExtraIngredientToRecipe(RecipeExtraIngredientDto recipeExtraIngredientDto) {

        Recipe recipe = findRecipeById(recipeExtraIngredientDto.recipeId(), recipeRepository);

        RecipeExtraIngredient recipeExtraIngredient = recipeCommonMapper.recipeExtraIngredientMapper()
                .mapRecipeExtraIngredientDtoToEntity(recipeExtraIngredientDto);
        recipe.addRecipeExtraIngredient(recipeExtraIngredient);

        recipe = recipeRepository.save(recipe);

        return recipeCommonMapper.recipeMapper().mapRecipeToDetailedDto(recipe);
    }

    @Override
    public RecipeDetailedDto addYeastToRecipe(RecipeYeastDto recipeYeastDto) {
        Recipe recipe = findRecipeById(recipeYeastDto.recipeId(), recipeRepository);

        RecipeYeast recipeYeast = recipeCommonMapper.recipeYeastMapper().mapRecipeYeastDtoToEntity(recipeYeastDto);
        recipe.setRecipeYeast(recipeYeast);

        recipe = recipeRepository.save(recipe);

        return recipeCommonMapper.recipeMapper().mapRecipeToDetailedDto(recipe);
    }

    static Recipe findRecipeById (UUID recipeId, RecipeRepository recipeRepository) {
        return recipeRepository.findById(recipeId).orElseThrow(() ->
                new IllegalStateException(String.format(ERROR_MESSAGE_RECIPE_ID_NOT_FOUND, recipeId)));
    }

}
