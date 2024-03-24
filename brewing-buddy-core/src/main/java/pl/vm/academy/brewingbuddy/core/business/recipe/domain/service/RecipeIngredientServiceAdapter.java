package pl.vm.academy.brewingbuddy.core.business.recipe.domain.service;

import lombok.RequiredArgsConstructor;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeYeastDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeCommonMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.RecipeExtraIngredient;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.RecipeHop;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.RecipeMalt;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.RecipeYeast;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.repository.RecipeRepository;

@RequiredArgsConstructor
public class RecipeIngredientServiceAdapter implements RecipeIngredientService {
    private final RecipeRepository recipeRepository;
    private final RecipeCommonMapper recipeCommonMapper;

    @Override
    public RecipeDetailedDto addHopToRecipe(RecipeHopDto recipeHopDto) {

        Recipe recipe = RecipeServiceAdapter.findRecipeById(recipeHopDto.recipeId(), recipeRepository);

        RecipeHop recipeHop = recipeCommonMapper.recipeHopMapper().mapRecipeHopDtoToEntity(recipeHopDto);
        recipe.addRecipeHop(recipeHop);

        recipe = recipeRepository.save(recipe);

        return recipeCommonMapper.recipeMapper().mapRecipeToDetailedDto(recipe);
    }

    @Override
    public RecipeDetailedDto addMaltToRecipe(RecipeMaltDto recipeMaltDto) {

        Recipe recipe = RecipeServiceAdapter.findRecipeById(recipeMaltDto.recipeId(), recipeRepository);

        RecipeMalt recipeMalt = recipeCommonMapper.recipeMaltMapper().mapRecipeMaltDtoToEntity(recipeMaltDto);
        recipe.addRecipeMalt(recipeMalt);

        recipe = recipeRepository.save(recipe);

        return recipeCommonMapper.recipeMapper().mapRecipeToDetailedDto(recipe);
    }

    @Override
    public RecipeDetailedDto addExtraIngredientToRecipe(RecipeExtraIngredientDto recipeExtraIngredientDto) {

        Recipe recipe = RecipeServiceAdapter.findRecipeById(recipeExtraIngredientDto.recipeId(), recipeRepository);

        RecipeExtraIngredient recipeExtraIngredient = recipeCommonMapper.recipeExtraIngredientMapper()
                .mapRecipeExtraIngredientDtoToEntity(recipeExtraIngredientDto);
        recipe.addRecipeExtraIngredient(recipeExtraIngredient);

        recipe = recipeRepository.save(recipe);

        return recipeCommonMapper.recipeMapper().mapRecipeToDetailedDto(recipe);
    }

    @Override
    public RecipeDetailedDto addYeastToRecipe(RecipeYeastDto recipeYeastDto) {
        Recipe recipe = RecipeServiceAdapter.findRecipeById(recipeYeastDto.recipeId(), recipeRepository);

        RecipeYeast recipeYeast = recipeCommonMapper.recipeYeastMapper().mapRecipeYeastDtoToEntity(recipeYeastDto);
        recipe.setRecipeYeast(recipeYeast);

        recipe = recipeRepository.save(recipe);

        return recipeCommonMapper.recipeMapper().mapRecipeToDetailedDto(recipe);
    }
}
