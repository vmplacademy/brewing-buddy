package pl.vm.academy.brewingbuddy.core;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.vm.academy.brewingbuddy.core.business.recipe.converter.RecipeConverter;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeCalculatedParameters;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeCalculatedParametersRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.RecipeService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @InjectMocks
    private RecipeService recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeConverter recipeConverter;

    @Mock
    private RecipeCalculatedParametersRepository recipeCalculatedParametersRepository;


    @Test
    public void should_save_one_recipe() {

        RecipeConverter converter = new RecipeConverter();

        //given
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setRecipeName("F*cking good IPA");
        Recipe recipe = new Recipe();
        recipe.setRecipeName("F*cking good IPA");
        Recipe recipeToSave = converter.recipeDtoToEntity(recipeDto);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipeToSave);
        when(recipeCalculatedParametersRepository.save(any(RecipeCalculatedParameters.class))).
                thenReturn(new RecipeCalculatedParameters());
        when(recipeConverter.recipeDtoToEntity(any(RecipeDto.class))).thenReturn(recipe);
        when(recipeConverter.recipeToDto(any(Recipe.class))).thenReturn(recipeDto);

        //when
        RecipeDto savedRecipe = recipeService.createRecipe(new RecipeDto());

        //then
        assertThat(savedRecipe).usingRecursiveAssertion().isEqualTo(recipeDto);
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }



    @Test
    public void should_find_and_return_all_recipes() {
        // given
        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();
        recipe1.setRecipeName("dupa");
        recipe2.setRecipeName("dupa2");
        List<Recipe> recipeList = new ArrayList<>();
        recipeList.add(recipe1);
        recipeList.add(recipe2);

        List<RecipeDto> recipeDtoList = new ArrayList<>();
        RecipeDto recipeDto1 = new RecipeDto();
        RecipeDto recipeDto2 = new RecipeDto();
        recipeDto1.setRecipeName("dupa");
        recipeDto2.setRecipeName("dupa2");
        recipeDtoList.add(recipeDto1);
        recipeDtoList.add(recipeDto2);

        when(recipeRepository.findAllByIsPublic(true)).thenReturn(recipeList);
        when(recipeConverter.recipeListToDtoList(recipeList)).thenReturn(recipeDtoList);
        // when
        List<RecipeDto> returnedRecipeDtoList = recipeService.getAllPublicRecipes();

        // then
        assertEquals(returnedRecipeDtoList.size(), 2);
        assertEquals(returnedRecipeDtoList.get(0).getRecipeName(), "dupa");

        //assertThat(recipeService.getAllPublicRecipes().size() == 2);
        //assertThat(recipeService.getAllPublicRecipes().get(0).getRecipeName().equals("dupa2"));
        //verify(recipeRepository, times(1)).findAllByIsPublic(true);
        //verifyNoMoreInteractions(recipeRepository);
    }
}
