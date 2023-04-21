package pl.vm.academy.brewingbuddy.core;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import pl.vm.academy.brewingbuddy.core.business.recipe.converter.CalculatedParametersConverter;
import pl.vm.academy.brewingbuddy.core.business.recipe.converter.RecipeConverter;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeCalculatedParameters;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeCalculatedParametersRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.RecipeService;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.RecipeServiceAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeCalculatedParametersRepository recipeCalculatedParametersRepository;

    @Mock
    private RecipeConverter recipeConverter;

    @Mock
    private CalculatedParametersConverter calculatedParametersConverter;

    private RecipeService recipeService;

    @BeforeEach
    void init() {
        recipeService = new RecipeServiceAdapter(
                recipeRepository,
                recipeCalculatedParametersRepository,
                recipeConverter,
                calculatedParametersConverter
        );
    }

    @Nested
    @DisplayName("Test for saving recipe")
    class SaveRecipeTest {

        @Test
        public void should_save_one_recipe() {
            //given
            RecipeConverter converter = new RecipeConverter();

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
        public void should_throw_exception_when_recipe_with_such_name_already_exists() {
            //given
            Recipe recipeInRepo = new Recipe();
            recipeInRepo.setRecipeName("dupa");

            RecipeDto recipeToSave = new RecipeDto();
            recipeToSave.setRecipeName("dupa");

            when(recipeRepository.findRecipeByRecipeName(any(String.class))).thenReturn(Optional.of(recipeInRepo));

            Exception exception = assertThrows(ResponseStatusException.class, () ->
                    recipeService.createRecipe(recipeToSave));

            String expectedMessage = "Recipe with such name already exists!";
            String actualMessage = exception.getMessage();
            //when

            //then
            assertTrue(actualMessage.contains(expectedMessage));
        }

    }

    @Nested
    @DisplayName("Test for finding all recipes")
    class FindAllRecipesTest {

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

        }
    }

    @Nested
    @DisplayName("Test for saving recipe")
    class FindRecipeByIdTest {

        @Test
        public void should_find_and_return_one_recipe_by_its_id() {
            //given
            UUID recipeId = UUID.fromString("ec60c78e-dc5e-11ed-afa1-0242ac120002");
            Recipe recipeInDB = createRecipe(recipeId);
            RecipeDto recipeDtoToReturn = createRecipeDto(recipeId);

            when(recipeRepository.findById(any(UUID.class))).thenReturn(Optional.of(recipeInDB));
            when(recipeConverter.recipeToDto(recipeInDB)).thenReturn(recipeDtoToReturn);

            //when
            RecipeDto returnedDto = recipeService.getRecipeById(recipeId);

            //then
            assertThat(returnedDto)
                    .isNotNull()
                    .isEqualTo(recipeDtoToReturn)
                    .extracting(RecipeDto::getId).isEqualTo(recipeId.toString());
        }

        @Test
        public void should_throw_exception_when_recipe_with_such_id_is_not_in_db() {
            //given
            UUID recipeId = UUID.fromString("ec60c78e-dc5e-11ed-afa1-0242ac120002");
            RecipeDto inputRecipeDto = createRecipeDto(recipeId);

            when(recipeRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

            //when
            Exception exception = assertThrows(ResponseStatusException.class, () ->
                    recipeService.getRecipeById(recipeId));

            String expectedMessage = "entity with such id not found in database";
            String actualMessage = exception.getMessage();

            //then
            assertTrue(actualMessage.contains(expectedMessage));
        }
    }

    private RecipeDto createRecipeDto(UUID id) {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(id.toString());

        return recipeDto;
    }

    private Recipe createRecipe(UUID id) {
        Recipe recipe = new Recipe();
        recipe.setId(id);

        return recipe;
    }
}
