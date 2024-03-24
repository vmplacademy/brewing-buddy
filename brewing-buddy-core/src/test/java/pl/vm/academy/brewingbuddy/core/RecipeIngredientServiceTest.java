package pl.vm.academy.brewingbuddy.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.RecipeCalculatedParameter;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.repository.RecipeRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.RecipeIngredientService;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.RecipeIngredientServiceAdapter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RecipeIngredientServiceTest {
    @Mock
    private RecipeRepository recipeRepository;
    private final RecipeHopMapper recipeHopMapper = new RecipeHopMapper();
    private final RecipeMaltMapper recipeMaltMapper = new RecipeMaltMapper();
    private final RecipeExtraIngredientMapper recipeExtraIngredientMapper = new RecipeExtraIngredientMapper();
    private final RecipeYeastMapper recipeYeastMapper = new RecipeYeastMapper();
    private final RecipeCalculatedParametersMapper recipeCalculatedParametersMapper = new RecipeCalculatedParametersMapper();
    private final RecipeMapper recipeMapper = new RecipeMapper(
            recipeCalculatedParametersMapper,
            recipeHopMapper,
            recipeMaltMapper,
            recipeExtraIngredientMapper,
            recipeYeastMapper);

    private final RecipeCommonMapper recipeCommonMapper = new RecipeCommonMapper(
            recipeMapper,
            recipeHopMapper,
            recipeMaltMapper,
            recipeExtraIngredientMapper,
            recipeYeastMapper,
            recipeCalculatedParametersMapper);

    private RecipeIngredientService recipeIngredientService;

    @BeforeEach
    void init() {
        recipeIngredientService = new RecipeIngredientServiceAdapter(
                recipeRepository,
                recipeCommonMapper
        );
    }

    @Nested
    @DisplayName("Test of RecipeMalt operations")
    class RecipeMaltTest {
        private final String recipeId = "b1c06205-7ea9-456c-82ac-e9cca2881dac";
        private final String maltId = "d4f336b0-f4a7-41f1-8b08-14ed81c4eff8";
        @Test
        void should_add_recipeMalt_to_recipe () {
            // given
            Recipe recipeFoundInDb = createRecipe(recipeId);
            RecipeMaltDto recipeMaltDto = createRecipeMaltDto(recipeId, maltId);

            Recipe recipeFoundInDbWithAddedMalt = createRecipe("b1c06205-7ea9-456c-82ac-e9cca2881dac");
            recipeFoundInDbWithAddedMalt.addRecipeMalt(recipeMaltMapper.mapRecipeMaltDtoToEntity(recipeMaltDto));

            when(recipeRepository.findById(any(UUID.class))).thenReturn(Optional.of(recipeFoundInDb));
            when(recipeRepository.save(any(Recipe.class))).thenReturn(recipeFoundInDbWithAddedMalt);

            // when
            RecipeDetailedDto recipeDetailedDto = recipeIngredientService.addMaltToRecipe(recipeMaltDto);

            // then
            assertThat(recipeDetailedDto.recipeMalts()).hasSize(1);
            assertThat(recipeDetailedDto.recipeMalts()).contains(recipeMaltDto);
            verify(recipeRepository, times(1)).save(any(Recipe.class));
        }

        @Test
        void should_throw_exception_when_recipe_not_found () {
            // given
            RecipeMaltDto recipeMaltDto = createRecipeMaltDto(recipeId, maltId);

            when(recipeRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

            // when
            Throwable thrown = catchThrowable(() -> {
                    recipeIngredientService.addMaltToRecipe(recipeMaltDto);
            });

            // then
            assertThat(thrown).
                    isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining(String.format("entity with id: %s not found in database", recipeMaltDto.recipeId().toString()));
        }
    }

    @Nested
    @DisplayName("Test of RecipeHop operations")
    class RecipeHopTest {
        private final String recipeId = "b1c06205-7ea9-456c-82ac-e9cca2881dac";
        private final String hopId = "d4f336b0-f4a7-41f1-8b08-14ed81c4eff8";
        @Test
        void should_add_recipeHop_to_recipe () {
            // given
            Recipe recipeFoundInDb = createRecipe(recipeId);

            RecipeHopDto recipeHopDto = createRecipeHopDto(recipeId, hopId);

            Recipe recipeFoundInDbWithAddedHop = createRecipe(recipeId);
            recipeFoundInDbWithAddedHop.addRecipeHop(recipeHopMapper.mapRecipeHopDtoToEntity(recipeHopDto));

            when(recipeRepository.findById(any(UUID.class))).thenReturn(Optional.of(recipeFoundInDb));
            when(recipeRepository.save(any(Recipe.class))).thenReturn(recipeFoundInDbWithAddedHop);

            // when
            RecipeDetailedDto recipeDetailedDto = recipeIngredientService.addHopToRecipe(recipeHopDto);

            // then
            assertThat(recipeDetailedDto.recipeHops()).hasSize(1);
            assertThat(recipeDetailedDto.recipeHops()).contains(recipeHopDto);
            verify(recipeRepository, times(1)).save(any(Recipe.class));
        }

        @Test
        void should_throw_exception_when_recipe_not_found () {
            // given
            RecipeHopDto recipeHopDto = createRecipeHopDto(recipeId, hopId);

            when(recipeRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

            // when
            Throwable thrown = catchThrowable(() -> {
                recipeIngredientService.addHopToRecipe(recipeHopDto);
            });

            // then
            assertThat(thrown)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining(String.format("entity with id: %s not found in database", recipeHopDto.recipeId().toString()));
        }
    }

    @Nested
    @DisplayName("Test of RecipeExtraIngredient operations")
    class RecipeExtraIngredientTest {
        private final String recipeId = "b1c06205-7ea9-456c-82ac-e9cca2881dac";
        private final String extraIngredientId = "d4f336b0-f4a7-41f1-8b08-14ed81c4eff8";
        @Test
        void should_add_recipeExtraIngredient_to_recipe () {
            // given
            Recipe recipeFoundInDb = createRecipe(recipeId);

            RecipeExtraIngredientDto recipeExtraIngredientDto = createExtraIngredient(recipeId, extraIngredientId);

            Recipe recipeFoundInDbWithAddedExtraIngredient = createRecipe(recipeId);
            recipeFoundInDbWithAddedExtraIngredient.addRecipeExtraIngredient(
                    recipeExtraIngredientMapper.mapRecipeExtraIngredientDtoToEntity(recipeExtraIngredientDto));

            when(recipeRepository.findById(any(UUID.class))).thenReturn(Optional.of(recipeFoundInDb));
            when(recipeRepository.save(any(Recipe.class))).thenReturn(recipeFoundInDbWithAddedExtraIngredient);

            // when
            RecipeDetailedDto recipeDetailedDto = recipeIngredientService.addExtraIngredientToRecipe(recipeExtraIngredientDto);

            // then
            assertThat(recipeDetailedDto.recipeExtraIngredients()).hasSize(1);
            assertThat(recipeDetailedDto.recipeExtraIngredients()).contains(recipeExtraIngredientDto);
            verify(recipeRepository, times(1)).save(any(Recipe.class));
        }

        @Test
        void should_throw_exception_when_recipe_not_found () {
            // given
            RecipeExtraIngredientDto recipeExtraIngredientDto = createExtraIngredient(recipeId, extraIngredientId);

            when(recipeRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

            // when
            Throwable thrown = catchThrowable(() -> {
                recipeIngredientService.addExtraIngredientToRecipe(recipeExtraIngredientDto);
            });

            assertThat(thrown)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining(String.format("entity with id: %s not found in database", recipeExtraIngredientDto.recipeId().toString()));
        }
    }

    @Nested
    @DisplayName("Test of RecipeYeast operations")
    class RecipeYeastTest {
        private final String recipeId = "b1c06205-7ea9-456c-82ac-e9cca2881dac";
        private final String yeastId = "d4f336b0-f4a7-41f1-8b08-14ed81c4eff8";
        @Test
        void should_add_recipeYeast_to_recipe () {
            // given
            Recipe recipeFoundInDb = createRecipe(recipeId);

            RecipeYeastDto recipeYeastDto = createRecipeYeastDto(recipeId, yeastId);

            Recipe recipeFoundInDbWithAddedYeast = createRecipe(recipeId);
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
            Throwable thrown = catchThrowable(() -> {
                recipeIngredientService.addYeastToRecipe(recipeYeastDto);
            });

            // then
            assertThat(thrown)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining(String.format("entity with id: %s not found in database", recipeYeastDto.recipeId().toString()));
        }
    }

    private Recipe createRecipe (String uuid) {
        Recipe recipe = new Recipe();
        recipe.setId(UUID.fromString(uuid));
        recipe.setRecipeMalts(new HashSet<>());
        recipe.setRecipeHops(new HashSet<>());
        recipe.setRecipeExtraIngredients(new HashSet<>());
        recipe.setRecipeCalculatedParameter(new RecipeCalculatedParameter());
        return recipe;
    }

    private RecipeMaltDto createRecipeMaltDto (String recipeId, String maltId) {
        return RecipeMaltDto
                .builder()
                .recipeId(UUID.fromString(recipeId))
                .maltId(UUID.fromString(maltId))
                .maltAmountInKilos(BigDecimal.valueOf(5))
                .build();
    }

    private RecipeHopDto createRecipeHopDto(String recipeId, String hopId) {
        return RecipeHopDto.builder()
                .recipeId(UUID.fromString(recipeId))
                .hopId(UUID.fromString(hopId))
                .hopAmountInGrams(BigDecimal.valueOf(50))
                .build();
    }

    private RecipeExtraIngredientDto createExtraIngredient(String recipeId, String extraIngredientId) {
        return RecipeExtraIngredientDto.builder()
                .recipeId(UUID.fromString(recipeId))
                .extraIngredientId(UUID.fromString(extraIngredientId))
                .build();
    }

    private RecipeYeastDto createRecipeYeastDto(String recipeId, String yeastId) {
        return RecipeYeastDto.builder()
                .recipeId(UUID.fromString(recipeId))
                .yeastId(UUID.fromString(yeastId))
                .yeastQuantity(BigDecimal.valueOf(50))
                .build();
    }
}