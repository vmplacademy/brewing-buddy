package pl.vm.academy.brewingbuddy.core;

import static org.assertj.core.api.Assertions.*;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.vm.academy.brewingbuddy.core.business.recipe.converter.RecipeConverter;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeCalculatedParameters;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeCalculatedParametersRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.RecipeService;

@RunWith(MockitoJUnitRunner.class)
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
        //verifyNoMoreInteractions(recipeRepository);
    }
}
