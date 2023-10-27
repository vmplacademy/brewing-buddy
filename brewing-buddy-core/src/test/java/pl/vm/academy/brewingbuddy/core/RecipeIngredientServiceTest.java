package pl.vm.academy.brewingbuddy.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeYeastDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeCalculatedParametersMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeCommonMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeExtraIngredientMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeHopMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMaltMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeYeastMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeCalculatedParameter;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeExtraIngredientRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeHopRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeMaltRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeYeastRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.RecipeIngredientService;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.RecipeIngredientServiceAdapter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RecipeIngredientServiceTest {

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private RecipeHopRepository recipeHopRepository;
    @Mock
    private RecipeMaltRepository recipeMaltRepository;
    @Mock
    private RecipeExtraIngredientRepository recipeExtraIngredientRepository;
    @Mock
    private RecipeYeastRepository recipeYeastRepository;

    private RecipeHopMapper recipeHopMapper = new RecipeHopMapper();
    private RecipeMaltMapper recipeMaltMapper = new RecipeMaltMapper();
    private RecipeExtraIngredientMapper recipeExtraIngredientMapper = new RecipeExtraIngredientMapper();
    private RecipeYeastMapper recipeYeastMapper = new RecipeYeastMapper();
    private RecipeCalculatedParametersMapper recipeCalculatedParametersMapper = new RecipeCalculatedParametersMapper();
    private RecipeMapper recipeMapper = new RecipeMapper(
            recipeCalculatedParametersMapper,
            recipeHopMapper,
            recipeMaltMapper,
            recipeExtraIngredientMapper,
            recipeYeastMapper);

    private RecipeCommonMapper recipeCommonMapper = new RecipeCommonMapper(recipeMapper, recipeHopMapper,
            recipeMaltMapper, recipeExtraIngredientMapper, recipeYeastMapper, recipeCalculatedParametersMapper);

    private RecipeIngredientService recipeIngredientService;

    @BeforeEach
    void init() {
        recipeIngredientService = new RecipeIngredientServiceAdapter(
                recipeRepository,
                recipeHopRepository,
                recipeMaltRepository,
                recipeExtraIngredientRepository,
                recipeYeastRepository,
                recipeCommonMapper);
    }

    @Nested
    @DisplayName("Test of RecipeMalt operations")
    class RecipeMaltTest {
        @Test
        void should_add_recipeMalt_to_recipe () {
            // given
            Recipe recipeFoundInDb = new Recipe();
            recipeFoundInDb.setId(UUID.fromString("b1c06205-7ea9-456c-82ac-e9cca2881dac"));
            recipeFoundInDb.setRecipeMalts(new HashSet<>());

            RecipeMaltDto recipeMaltDto = RecipeMaltDto
                    .builder()
                    .recipeId(UUID.fromString("b1c06205-7ea9-456c-82ac-e9cca2881dac"))
                    .maltId(UUID.fromString("d4f336b0-f4a7-41f1-8b08-14ed81c4eff8"))
                    .maltAmountInKilos(BigDecimal.valueOf(5))
                    .build();

            Recipe recipeFoundInDbWithAddedMalt = new Recipe();
            recipeFoundInDbWithAddedMalt.setId(UUID.fromString("b1c06205-7ea9-456c-82ac-e9cca2881dac"));
            recipeFoundInDbWithAddedMalt.setRecipeMalts(new HashSet<>());
            recipeFoundInDbWithAddedMalt.setRecipeCalculatedParameter(new RecipeCalculatedParameter());
            recipeFoundInDbWithAddedMalt.addRecipeMalt(recipeMaltMapper.mapRecipeMaltDtoToEntity(recipeMaltDto));

            when(recipeRepository.findById(any(UUID.class))).thenReturn(Optional.of(recipeFoundInDb));
            when(recipeRepository.save(any(Recipe.class))).thenReturn(recipeFoundInDbWithAddedMalt);

            // when
            RecipeDetailedDto recipeDetailedDto = recipeIngredientService.addMaltToRecipe(recipeMaltDto);

            // then
            assertThat(recipeDetailedDto.recipeMalts().size()).isEqualTo(1);
            assertThat(recipeDetailedDto.recipeMalts()).contains(recipeMaltDto);
            verify(recipeRepository, times(1)).save(any(Recipe.class));
        }

        @Test
        void should_throw_exception_when_recipe_not_found () {
            // given
            RecipeMaltDto recipeMaltDto = RecipeMaltDto
                    .builder()
                    .recipeId(UUID.fromString("b1c06205-7ea9-456c-82ac-e9cca2881dac"))
                    .maltId(UUID.fromString("d4f336b0-f4a7-41f1-8b08-14ed81c4eff8"))
                    .maltAmountInKilos(BigDecimal.valueOf(5))
                    .build();

            when(recipeRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

            // when
            Exception exception = assertThrows(ResponseStatusException.class, () ->
                    recipeIngredientService.addMaltToRecipe(recipeMaltDto));

            String expectedMessage = String.format("entity with id: %s not found in database", recipeMaltDto.recipeId().toString());
            String actualMessage = exception.getMessage();

            // then
            assertTrue(actualMessage.contains(expectedMessage));
        }
    }

    @Nested
    @DisplayName("Test of RecipeHop operations")
    class RecipeHopTest {
        @Test
        void should_add_recipeHop_to_recipe () {
            // given
            Recipe recipeFoundInDb = new Recipe();
            recipeFoundInDb.setId(UUID.fromString("b1c06205-7ea9-456c-82ac-e9cca2881dac"));
            recipeFoundInDb.setRecipeHops(new HashSet<>());

            RecipeHopDto recipeHopDto = RecipeHopDto.builder()
                    .recipeId(UUID.fromString("b1c06205-7ea9-456c-82ac-e9cca2881dac"))
                    .hopId(UUID.fromString("d4f336b0-f4a7-41f1-8b08-14ed81c4eff8"))
                    .hopAmountInGrams(BigDecimal.valueOf(50))
                    .build();

            Recipe recipeFoundInDbWithAddedHop = new Recipe();
            recipeFoundInDbWithAddedHop.setId(UUID.fromString("b1c06205-7ea9-456c-82ac-e9cca2881dac"));
            recipeFoundInDbWithAddedHop.setRecipeHops(new HashSet<>());
            recipeFoundInDbWithAddedHop.setRecipeCalculatedParameter(new RecipeCalculatedParameter());
            recipeFoundInDbWithAddedHop.addRecipeHop(recipeHopMapper.mapRecipeHopDtoToEntity(recipeHopDto));

            when(recipeRepository.findById(any(UUID.class))).thenReturn(Optional.of(recipeFoundInDb));
            when(recipeRepository.save(any(Recipe.class))).thenReturn(recipeFoundInDbWithAddedHop);

            // when
            RecipeDetailedDto recipeDetailedDto = recipeIngredientService.addHopToRecipe(recipeHopDto);

            // then
            assertThat(recipeDetailedDto.recipeHops().size()).isEqualTo(1);
            assertThat(recipeDetailedDto.recipeHops()).contains(recipeHopDto);
            verify(recipeRepository, times(1)).save(any(Recipe.class));
        }

        @Test
        void should_throw_exception_when_recipe_not_found () {
            // given
            RecipeHopDto recipeHopDto = RecipeHopDto.builder()
                    .recipeId(UUID.fromString("b1c06205-7ea9-456c-82ac-e9cca2881dac"))
                    .hopId(UUID.fromString("d4f336b0-f4a7-41f1-8b08-14ed81c4eff8"))
                    .hopAmountInGrams(BigDecimal.valueOf(50))
                    .build();

            when(recipeRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

            // when
            Exception exception = assertThrows(ResponseStatusException.class, () ->
                    recipeIngredientService.addHopToRecipe(recipeHopDto));

            String expectedMessage = String.format("entity with id: %s not found in database", recipeHopDto.recipeId().toString());
            String actualMessage = exception.getMessage();

            // then
            assertTrue(actualMessage.contains(expectedMessage));
        }
    }

    @Nested
    @DisplayName("Test of RecipeExtraIngredient operations")
    class RecipeExtraIngredientTest {
        @Test
        void should_add_recipeExtraIngredient_to_recipe () {
            // given
            Recipe recipeFoundInDb = new Recipe();
            recipeFoundInDb.setId(UUID.fromString("b1c06205-7ea9-456c-82ac-e9cca2881dac"));
            recipeFoundInDb.setRecipeExtraIngredients(new HashSet<>());

            RecipeExtraIngredientDto recipeExtraIngredientDto = RecipeExtraIngredientDto.builder()
                    .recipeId(UUID.fromString("b1c06205-7ea9-456c-82ac-e9cca2881dac"))
                    .extraIngredientId(UUID.fromString("d4f336b0-f4a7-41f1-8b08-14ed81c4eff8"))
                    .build();

            Recipe recipeFoundInDbWithAddedExtraIngredient = new Recipe();
            recipeFoundInDbWithAddedExtraIngredient.setId(UUID.fromString("b1c06205-7ea9-456c-82ac-e9cca2881dac"));
            recipeFoundInDbWithAddedExtraIngredient.setRecipeExtraIngredients(new HashSet<>());
            recipeFoundInDbWithAddedExtraIngredient.setRecipeCalculatedParameter(new RecipeCalculatedParameter());
            recipeFoundInDbWithAddedExtraIngredient.addRecipeExtraIngredient(
                    recipeExtraIngredientMapper.mapRecipeExtraIngredientDtoToEntity(recipeExtraIngredientDto));

            when(recipeRepository.findById(any(UUID.class))).thenReturn(Optional.of(recipeFoundInDb));
            when(recipeRepository.save(any(Recipe.class))).thenReturn(recipeFoundInDbWithAddedExtraIngredient);

            // when
            RecipeDetailedDto recipeDetailedDto = recipeIngredientService.addExtraIngredientToRecipe(recipeExtraIngredientDto);

            // then
            assertThat(recipeDetailedDto.recipeExtraIngredients().size()).isEqualTo(1);
            assertThat(recipeDetailedDto.recipeExtraIngredients()).contains(recipeExtraIngredientDto);
            verify(recipeRepository, times(1)).save(any(Recipe.class));
        }

        @Test
        void should_throw_exception_when_recipe_not_found () {
            // given
            RecipeExtraIngredientDto recipeExtraIngredientDto = RecipeExtraIngredientDto.builder()
                    .recipeId(UUID.fromString("b1c06205-7ea9-456c-82ac-e9cca2881dac"))
                    .extraIngredientId(UUID.fromString("d4f336b0-f4a7-41f1-8b08-14ed81c4eff8"))
                    .amount(BigDecimal.valueOf(50))
                    .build();

            when(recipeRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

            // when
            Exception exception = assertThrows(ResponseStatusException.class, () ->
                    recipeIngredientService.addExtraIngredientToRecipe(recipeExtraIngredientDto));

            String expectedMessage = String.format("entity with id: %s not found in database", recipeExtraIngredientDto.recipeId().toString());
            String actualMessage = exception.getMessage();

            // then
            assertTrue(actualMessage.contains(expectedMessage));
        }
    }

    @Nested
    @DisplayName("Test of RecipeYeast operations")
    class RecipeYeastTest {
        @Test
        void should_add_recipeExtraIngredient_to_recipe () {
            // given
            Recipe recipeFoundInDb = new Recipe();
            recipeFoundInDb.setId(UUID.fromString("b1c06205-7ea9-456c-82ac-e9cca2881dac"));
            recipeFoundInDb.setRecipeExtraIngredients(new HashSet<>());

            RecipeYeastDto recipeYeastDto = RecipeYeastDto.builder()
                    .recipeId(UUID.fromString("b1c06205-7ea9-456c-82ac-e9cca2881dac"))
                    .yeastId(UUID.fromString("d4f336b0-f4a7-41f1-8b08-14ed81c4eff8"))
                    .yeastQuantity(BigDecimal.valueOf(50))
                    .build();

            Recipe recipeFoundInDbWithAddedYeast = new Recipe();
            recipeFoundInDbWithAddedYeast.setId(UUID.fromString("b1c06205-7ea9-456c-82ac-e9cca2881dac"));
            recipeFoundInDbWithAddedYeast.setRecipeCalculatedParameter(new RecipeCalculatedParameter());
            recipeFoundInDbWithAddedYeast.setRecipeYeast(recipeYeastMapper.mapRecipeYeastDtoToEntity(recipeYeastDto));

            when(recipeRepository.findById(any(UUID.class))).thenReturn(Optional.of(recipeFoundInDb));
            when(recipeRepository.save(any(Recipe.class))).thenReturn(recipeFoundInDbWithAddedYeast);

            // when
            RecipeDetailedDto recipeDetailedDto = recipeIngredientService.addYeastToRecipe(recipeYeastDto);

            // then
            assertThat(recipeDetailedDto.recipeYeastDto()).isEqualTo(recipeYeastDto);
            verify(recipeRepository, times(1)).save(any(Recipe.class));
        }

        @Test
        void should_throw_exception_when_recipe_not_found () {
            // given
            RecipeYeastDto recipeYeastDto = RecipeYeastDto.builder()
                    .recipeId(UUID.fromString("b1c06205-7ea9-456c-82ac-e9cca2881dac"))
                    .yeastId(UUID.fromString("d4f336b0-f4a7-41f1-8b08-14ed81c4eff8"))
                    .yeastQuantity(BigDecimal.valueOf(50))
                    .build();

            when(recipeRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

            // when
            Exception exception = assertThrows(ResponseStatusException.class, () ->
                    recipeIngredientService.addYeastToRecipe(recipeYeastDto));

            String expectedMessage = String.format("entity with id: %s not found in database", recipeYeastDto.recipeId().toString());
            String actualMessage = exception.getMessage();

            // then
            assertTrue(actualMessage.contains(expectedMessage));
        }
    }

}
