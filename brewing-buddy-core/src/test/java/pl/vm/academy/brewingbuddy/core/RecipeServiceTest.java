package pl.vm.academy.brewingbuddy.core;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.CalculatedParametersMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeCalculatedParameter;
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
    private RecipeMapper recipeMapper;

    @Mock
    private CalculatedParametersMapper calculatedParametersMapper;

    private RecipeService recipeService;

    @BeforeEach
    void init() {
        recipeService = new RecipeServiceAdapter(
                recipeRepository,
                recipeCalculatedParametersRepository,
                recipeMapper,
                calculatedParametersMapper
        );
    }

    @Nested
    @DisplayName("Test for saving recipe")
    class SaveRecipeTest {

        @Test
        public void should_save_one_recipe() {
            //given
            RecipeMapper converter = new RecipeMapper();

            RecipeDto recipeDto = RecipeDto.builder().recipeName("good IPA").build();
            Recipe recipe = new Recipe();
            recipe.setRecipeName("good IPA");
            Recipe recipeToSave = converter.mapRecipeDtoToEntity(recipeDto);
            when(recipeRepository.save(any(Recipe.class))).thenReturn(recipeToSave);
            when(recipeCalculatedParametersRepository.save(any(RecipeCalculatedParameter.class))).
                    thenReturn(new RecipeCalculatedParameter());
            when(recipeMapper.mapRecipeDtoToEntity(any(RecipeDto.class))).thenReturn(recipe);
            when(recipeMapper.mapRecipeToDto(any(Recipe.class))).thenReturn(recipeDto);

            //when
            RecipeDto savedRecipe = recipeService.createRecipe(RecipeDto.builder().build());

            //then
            assertThat(savedRecipe).usingRecursiveComparison().isEqualTo(recipeDto);
            verify(recipeRepository, times(1)).save(any(Recipe.class));
        }


        @Test
        public void should_throw_exception_when_recipe_with_such_name_already_exists() {
            //given
            Recipe recipeInRepo = new Recipe();
            recipeInRepo.setRecipeName("recipe");

            RecipeDto recipeToSave = RecipeDto.builder().recipeName("recipe").build();

            when(recipeRepository.existsRecipeByRecipeName(any(String.class))).thenReturn(true);

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
            recipe1.setRecipeName("recipe1");
            recipe2.setRecipeName("recipe2");
            List<Recipe> recipeList = new ArrayList<>();
            recipeList.add(recipe1);
            recipeList.add(recipe2);

            List<RecipeDto> recipeDtoList = new ArrayList<>();
            RecipeDto recipeDto1 = RecipeDto.builder().recipeName("recipe1").build();
            RecipeDto recipeDto2 = RecipeDto.builder().recipeName("recipe2").build();
            recipeDtoList.add(recipeDto1);
            recipeDtoList.add(recipeDto2);

            when(recipeRepository.findAllByIsPublic(true)).thenReturn(recipeList);
            when(recipeMapper.mapRecipeListToDtoList(recipeList)).thenReturn(recipeDtoList);
            // when
            List<RecipeDto> returnedRecipeDtoList = recipeService.getAllPublicRecipes();

            // then
            assertEquals(returnedRecipeDtoList.size(), 2);
            assertEquals(returnedRecipeDtoList.get(0).recipeName(), "recipe1");

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
            when(recipeMapper.mapRecipeToDto(recipeInDB)).thenReturn(recipeDtoToReturn);

            //when
            RecipeDto returnedDto = recipeService.getRecipeById(recipeId);

            //then
            assertThat(returnedDto)
                    .isNotNull()
                    .isEqualTo(recipeDtoToReturn)
                    .extracting(RecipeDto::id).isEqualTo(recipeId);
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

            String expectedMessage = String.format("entity with id: %s not found in database", recipeId.toString());
            String actualMessage = exception.getMessage();

            //then
            assertTrue(actualMessage.contains(expectedMessage));
        }
    }

    private RecipeDto createRecipeDto(UUID id) {
        RecipeDto recipeDto = RecipeDto.builder().id(id).build();

        return recipeDto;
    }

    private Recipe createRecipe(UUID id) {
        Recipe recipe = new Recipe();
        recipe.setId(id);

        return recipe;
    }
}
