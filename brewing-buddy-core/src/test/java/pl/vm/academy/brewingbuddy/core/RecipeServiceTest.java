package pl.vm.academy.brewingbuddy.core;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeBasicDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeSimpleDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeCalculatedParametersMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeExtraIngredientMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeHopMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMaltMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeYeastMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.RecipeCalculatedParameter;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.repository.RecipeCalculatedParametersRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.repository.RecipeHopRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.repository.RecipeRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.RecipeService;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.RecipeServiceAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import pl.vm.academy.brewingbuddy.core.errorhandling.RecipeAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private RecipeCalculatedParametersRepository recipeCalculatedParametersRepository;
    @Mock
    private RecipeHopRepository recipeHopRepository;
    @Mock
    private RecipeMapper recipeMapper;
    @Mock
    private RecipeHopMapper recipeHopMapper;
    @Mock
    private RecipeMaltMapper recipeMaltMapper;
    @Mock
    private RecipeExtraIngredientMapper recipeExtraIngredientMapper;
    @Mock
    private RecipeYeastMapper recipeYeastMapper;
    @Mock
    private RecipeCalculatedParametersMapper recipeCalculatedParametersMapper;
    private RecipeService recipeService;

    @BeforeEach
    void init() {
        recipeService = new RecipeServiceAdapter(
                recipeRepository,
                recipeCalculatedParametersRepository,
                recipeMapper,
                recipeCalculatedParametersMapper
        );
    }

    @Nested
    @DisplayName("Test for saving recipe")
    class SaveRecipeTest {

        @Test
        void should_save_one_recipe() {
            //given
            RecipeMapper converter = new RecipeMapper(recipeCalculatedParametersMapper, recipeHopMapper,
                    recipeMaltMapper, recipeExtraIngredientMapper, recipeYeastMapper);

            RecipeSimpleDto recipeSimpleDto = RecipeSimpleDto.builder().recipeName("good IPA").build();
            RecipeDetailedDto recipeDetailedDto = RecipeDetailedDto.builder().recipeName("good IPA").build();
            Recipe recipe = new Recipe();
            recipe.setRecipeName("good IPA");
            Recipe recipeToSave = converter.mapRecipeSimpleDtoToEntity(recipeSimpleDto, null);
            when(recipeRepository.save(any(Recipe.class))).thenReturn(recipeToSave);
            when(recipeCalculatedParametersRepository.save(any(RecipeCalculatedParameter.class))).
                    thenReturn(new RecipeCalculatedParameter());
            when(recipeMapper.mapRecipeSimpleDtoToEntity(any(RecipeSimpleDto.class), eq(null))).thenReturn(recipe);
            when(recipeMapper.mapRecipeToDetailedDto(any(Recipe.class))).thenReturn(recipeDetailedDto);

            //when
            RecipeDetailedDto savedRecipe = recipeService.createRecipe(RecipeSimpleDto.builder().build());

            //then
            assertThat(savedRecipe).usingRecursiveComparison().isEqualTo(recipeDetailedDto);
            verify(recipeRepository, times(1)).save(any(Recipe.class));
        }


        @Test
        void should_throw_exception_when_recipe_with_such_name_already_exists() {
            //given
            Recipe recipeInRepo = new Recipe();
            recipeInRepo.setRecipeName("recipe");

            RecipeSimpleDto recipeToSave = RecipeSimpleDto.builder().recipeName("recipe").build();

            when(recipeRepository.existsRecipeByRecipeName(any(String.class))).thenReturn(true);

            Exception exception = assertThrows(RecipeAlreadyExistsException.class, () ->
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
        void should_find_and_return_all_recipes() {
            // given
            Recipe recipe1 = new Recipe();
            Recipe recipe2 = new Recipe();
            recipe1.setRecipeName("recipe1");
            recipe2.setRecipeName("recipe2");
            List<Recipe> recipeList = new ArrayList<>();
            recipeList.add(recipe1);
            recipeList.add(recipe2);

            List<RecipeBasicDto> recipeBasicDtos = new ArrayList<>();
            RecipeBasicDto recipeBasicDto1 = RecipeBasicDto.builder().recipeName("recipe1").build();
            RecipeBasicDto recipeBasicDto2 = RecipeBasicDto.builder().recipeName("recipe2").build();
            recipeBasicDtos.add(recipeBasicDto1);
            recipeBasicDtos.add(recipeBasicDto2);

            when(recipeRepository.findAllByIsPublic(true)).thenReturn(recipeList);
            when(recipeMapper.mapRecipeListToBasicDtoList(recipeList)).thenReturn(recipeBasicDtos);
            // when
            List<RecipeBasicDto> returnedRecipeDetailedDtoList = recipeService.getAllPublicRecipes();

            // then
            assertEquals(2, returnedRecipeDetailedDtoList.size());
            assertEquals("recipe1", returnedRecipeDetailedDtoList.get(0).recipeName());

        }
    }

    @Nested
    @DisplayName("Test for saving recipe")
    class FindRecipeByIdTest {

        @Test
        void should_find_and_return_one_recipe_by_its_id() {
            //given
            UUID recipeId = UUID.fromString("ec60c78e-dc5e-11ed-afa1-0242ac120002");
            Recipe recipeInDB = createRecipe(recipeId);
            RecipeDetailedDto recipeDetailedDtoToReturn = createRecipeDto(recipeId);

            when(recipeRepository.findById(any(UUID.class))).thenReturn(Optional.of(recipeInDB));
            when(recipeMapper.mapRecipeToDetailedDto(recipeInDB)).thenReturn(recipeDetailedDtoToReturn);

            //when
            RecipeDetailedDto returnedDto = recipeService.getRecipeById(recipeId);

            //then
            assertThat(returnedDto)
                    .isNotNull()
                    .isEqualTo(recipeDetailedDtoToReturn)
                    .extracting(RecipeDetailedDto::id).isEqualTo(recipeId);
        }

        @Test
        void should_throw_exception_when_recipe_with_such_id_is_not_in_db() {
            //given
            UUID recipeId = UUID.fromString("ec60c78e-dc5e-11ed-afa1-0242ac120002");
            RecipeDetailedDto inputRecipeDetailedDto = createRecipeDto(recipeId);

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

    @Nested
    @DisplayName("Test for adding Hop to recipe")
    class AddHopToRecipe {
        @Test
        void should_throw_exception_when_there_is_no_recipe_with_such_id() {
            // given
            RecipeHopDto recipeHopDto = RecipeHopDto.builder().recipeId(UUID.fromString("6186aa41-7ec3-4f8b-a4f4-ab63e7ddc811")).build();
            when(recipeRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

            // when
            Exception exception = assertThrows(ResponseStatusException.class, () ->
                    recipeService.getRecipeById(recipeHopDto.recipeId()));

            String expectedMessage = String.format("entity with id: %s not found in database", recipeHopDto.recipeId().toString());
            String actualMessage = exception.getMessage();

            // then
            assertTrue(actualMessage.contains(expectedMessage));
        }
    }

    private RecipeDetailedDto createRecipeDto(UUID id) {
        return RecipeDetailedDto.builder().id(id).build();
    }

    private Recipe createRecipe(UUID id) {
        Recipe recipe = new Recipe();
        recipe.setId(id);

        return recipe;
    }
}
