package pl.vm.academy.brewingbuddy.core;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.HopMapper;
import pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.IngredientCommonMapper;
import pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.MaltMapper;
import pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.YeastMapper;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.Hop;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.Malt;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.repository.ExtraIngredientRepository;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.repository.HopRepository;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.repository.MaltRepository;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.repository.YeastRepository;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.service.IngredientFacade;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.service.IngredientFacadeAdapter;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.service.IngredientServiceAdapter;
import pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.ingredient.ExtraIngredientMapper;
import pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.ingredient.JuiceMapper;
import pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.ingredient.LactoseMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeCalculatedParametersMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeExtraIngredientMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeHopMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMaltMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeYeastMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.RecipeCalculatedParameter;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.RecipeHop;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.RecipeMalt;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.repository.RecipeRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator.decorator.RecipeCalculatorDecoratorAdapter;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.utils.HopUtilisation;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeCalculatorDecoratorTest {
    private final String MALT_ID = "e3c8ef49-d78e-425e-b3b4-459c44db6456";
    private final String HOP_ID = "9b4cad8b-cee6-4fb1-a80d-52354c9efd4f";
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private MaltRepository maltRepository;
    @Mock
    private HopRepository hopRepository;
    @Mock
    private ExtraIngredientRepository extraIngredientRepository;
    @Mock
    private YeastRepository yeastRepository;

    private final HopUtilisation hopUtilisation = new HopUtilisation();
    private final RecipeMapper recipeMapper = new RecipeMapper(
            new RecipeCalculatedParametersMapper(),
            new RecipeHopMapper(),
            new RecipeMaltMapper(),
            new RecipeExtraIngredientMapper(),
            new RecipeYeastMapper()
    );

    private RecipeCalculatorDecoratorAdapter recipeParametersCalculator;

    @BeforeEach
    void init() {
        IngredientServiceAdapter ingredientService = new IngredientServiceAdapter(
                maltRepository,
                hopRepository,
                extraIngredientRepository,
                yeastRepository,
                new IngredientCommonMapper(
                        new MaltMapper(),
                        new HopMapper(),
                        new YeastMapper(),
                        new ExtraIngredientMapper(),
                        new JuiceMapper(),
                        new LactoseMapper()
                )
        );

        IngredientFacade ingredientFacade = new IngredientFacadeAdapter(ingredientService);

        recipeParametersCalculator = new RecipeCalculatorDecoratorAdapter(
                recipeRepository,
                recipeMapper,
                ingredientFacade,
                hopUtilisation);
    }

    @Test
    void should_correctly_calculate_recipe() {
        //given
        Recipe recipe = createValidRecipe();
        when(maltRepository.findById(any(UUID.class))).thenReturn(getMaltFromRepository());
        when(hopRepository.findById(any(UUID.class))).thenReturn(getHopFromRepository());

        //when
        RecipeCalculatedParametersDto calcParam = recipeParametersCalculator.calculateAllParameters(recipe);

        //then
        assertThat(calcParam.overallAmountOfMaltInKg())
                .isCloseTo(BigDecimal.valueOf(6.67), Percentage.withPercentage(2));

        assertThat(calcParam.amountOfHotWort())
                .isCloseTo(BigDecimal.valueOf(24.2), Percentage.withPercentage(1));

        assertThat(calcParam.waterRequiredForMashingInLiters())
                .isCloseTo(BigDecimal.valueOf(20), Percentage.withPercentage(2));

        assertThat(calcParam.waterRequiredForSpargingInLiters())
                .isCloseTo(BigDecimal.valueOf(13.1), Percentage.withPercentage(2));

        assertThat(calcParam.waterRequiredForWholeProcessInLiters())
                .isCloseTo(BigDecimal.valueOf(33.1), Percentage.withPercentage(2));

        assertThat(calcParam.amountOfWaterBeforeBoilingInLiters())
                .isCloseTo(BigDecimal.valueOf(28.5), Percentage.withPercentage(2));

        assertThat(calcParam.theoreticalExtractInGrams())
                .isCloseTo(BigDecimal.valueOf(5503), Percentage.withPercentage(2));

        assertThat(calcParam.realExtractInGrams())
                .isCloseTo(BigDecimal.valueOf(4402), Percentage.withPercentage(2));

        assertThat(calcParam.wortWeightInGrams())
                .isCloseTo(BigDecimal.valueOf(25828), Percentage.withPercentage(2));

        assertThat(calcParam.extractBeforeBoilingInPercentage())
                .isCloseTo(BigDecimal.valueOf(14.6), Percentage.withPercentage(2));

        assertThat(calcParam.calculatedExtractInPercentage())
                .isCloseTo(BigDecimal.valueOf(17), Percentage.withPercentage(2));

        assertThat(calcParam.calculatedIbu())
                .isCloseTo(BigDecimal.valueOf(10.5), Percentage.withPercentage(2));

        assertThat(calcParam.estimatedAmountOfAlcoholAfterFermentation())
                .isCloseTo(BigDecimal.valueOf(7.3), Percentage.withPercentage(2));

        assertThat(calcParam.calculatedColourEBC())
                .isCloseTo(BigDecimal.valueOf(6.62), Percentage.withPercentage(2));

    }

    @Test
    void should_return_empty_calc_dto_when_malts_empty() {
        // given
        Recipe recipe = createValidRecipe();
        recipe.setRecipeMalts(new HashSet<>());

        // when
        RecipeCalculatedParametersDto calcParam = recipeParametersCalculator.calculateAllParameters(recipe);

        // then
        assertThat(calcParam).isEqualTo(RecipeCalculatedParametersDto.builder().build());
        Mockito.verifyNoInteractions(maltRepository);
        Mockito.verifyNoInteractions(hopRepository);
    }

    @Test
    void should_return_empty_calc_dto_when_expected_amount_of_bear_is_zero_or_lower() {
        // given
        Recipe recipe = createValidRecipe();
        recipe.setExpectedAmountOfBeerInLiters(BigDecimal.valueOf(0));

        // when
        RecipeCalculatedParametersDto calcParam = recipeParametersCalculator.calculateAllParameters(recipe);

        // then
        assertThat(calcParam).isEqualTo(RecipeCalculatedParametersDto.builder().build());
        Mockito.verifyNoInteractions(maltRepository);
        Mockito.verifyNoInteractions(hopRepository);
    }

    @Test
    void should_return_empty_calc_dto_when_mashing_percentage_not_in_range() {
        // given
        Recipe recipe = createValidRecipe();
        recipe.setMashingPerformanceInPercentage(BigDecimal.valueOf(101));

        // when
        RecipeCalculatedParametersDto calcParam = recipeParametersCalculator.calculateAllParameters(recipe);

        // then
        assertThat(calcParam).isEqualTo(RecipeCalculatedParametersDto.builder().build());
        Mockito.verifyNoInteractions(maltRepository);
        Mockito.verifyNoInteractions(hopRepository);
    }

    @Test
    void should_return_empty_calc_dto_when_boiling_process_time_is_zero_or_lower() {
        // given
        Recipe recipe = createValidRecipe();
        recipe.setBoilingProcessTime(Duration.ofMinutes(0));

        // when
        RecipeCalculatedParametersDto calcParam = recipeParametersCalculator.calculateAllParameters(recipe);

        // then
        assertThat(calcParam).isEqualTo(RecipeCalculatedParametersDto.builder().build());
        Mockito.verifyNoInteractions(maltRepository);
        Mockito.verifyNoInteractions(hopRepository);
    }

    @Test
    void should_return_empty_calc_dto_when_water_evaporation_percentage_not_in_range() {
        // given
        Recipe recipe = createValidRecipe();
        recipe.setWaterEvaporationInPercentagePerHour(BigDecimal.valueOf(100));

        // when
        RecipeCalculatedParametersDto calcParam = recipeParametersCalculator.calculateAllParameters(recipe);

        // then
        assertThat(calcParam).isEqualTo(RecipeCalculatedParametersDto.builder().build());
        Mockito.verifyNoInteractions(maltRepository);
        Mockito.verifyNoInteractions(hopRepository);
    }

    @Test
    void should_return_empty_calc_dto_when_boiling_process_loss_percentage_not_in_range() {
        // given
        Recipe recipe = createValidRecipe();
        recipe.setBoilingProcessLossInPercentage(BigDecimal.valueOf(0));

        // when
        RecipeCalculatedParametersDto calcParam = recipeParametersCalculator.calculateAllParameters(recipe);

        // then
        assertThat(calcParam).isEqualTo(RecipeCalculatedParametersDto.builder().build());
        Mockito.verifyNoInteractions(maltRepository);
        Mockito.verifyNoInteractions(hopRepository);
    }

    @Test
    void should_return_empty_calc_dto_when_fermentation_process_loss_percentage_not_in_range() {
        // given
        Recipe recipe = createValidRecipe();
        recipe.setFermentationProcessLossInPercentage(BigDecimal.valueOf(100));

        // when
        RecipeCalculatedParametersDto calcParam = recipeParametersCalculator.calculateAllParameters(recipe);

        // then
        assertThat(calcParam).isEqualTo(RecipeCalculatedParametersDto.builder().build());
        Mockito.verifyNoInteractions(maltRepository);
        Mockito.verifyNoInteractions(hopRepository);
    }

    @Test
    void should_return_empty_calc_dto_when_mashing_factor_not_in_range() {
        // given
        Recipe recipe = createValidRecipe();
        recipe.setMashingFactorInLitersPerKg(BigDecimal.valueOf(11));

        // when
        RecipeCalculatedParametersDto calcParam = recipeParametersCalculator.calculateAllParameters(recipe);

        // then
        assertThat(calcParam).isEqualTo(RecipeCalculatedParametersDto.builder().build());
        Mockito.verifyNoInteractions(maltRepository);
        Mockito.verifyNoInteractions(hopRepository);
    }

    @Test
    void should_use_repository_expected_amount_of_times() {
        //given
        Recipe recipe = createValidRecipe();
        when(maltRepository.findById(any(UUID.class))).thenReturn(getMaltFromRepository());
        when(hopRepository.findById(any(UUID.class))).thenReturn(getHopFromRepository());

        //when
        recipeParametersCalculator.calculateAllParameters(recipe);
        //then
        verify(maltRepository, times(2)).findById(UUID.fromString(MALT_ID));
        verify(hopRepository, times(1)).findById(UUID.fromString(HOP_ID));
    }

    private Recipe createValidRecipe () {
        Recipe recipe = new Recipe();
        recipe.setRecipeMalts(new HashSet<>());
        recipe.setRecipeHops(new HashSet<>());
        recipe.setRecipeExtraIngredients(new HashSet<>());
        recipe.setRecipeCalculatedParameter(new RecipeCalculatedParameter());

        recipe.setRecipeName("Test recipe");
        recipe.setId(UUID.fromString("33c88450-af17-4d3e-8d9b-eaeb68bafdc3"));
        recipe.setExpectedAmountOfBeerInLiters(BigDecimal.valueOf(20.8));
        recipe.setMashingPerformanceInPercentage(BigDecimal.valueOf(80));
        recipe.setBoilingProcessTime(Duration.ofMinutes(60));
        recipe.setWaterEvaporationInPercentagePerHour(BigDecimal.valueOf(15));
        recipe.setBoilingProcessLossInPercentage(BigDecimal.valueOf(15));
        recipe.setFermentationProcessLossInPercentage(BigDecimal.valueOf(10));
        recipe.setMashingFactorInLitersPerKg(BigDecimal.valueOf(3));

        recipe.setRecipeCalculatedParameter(new RecipeCalculatedParameter());

        RecipeMalt recipeMalt = new RecipeMalt();
        recipeMalt.setId(UUID.fromString("5afff103-2591-4a2c-af64-60aab1e322c1"));
        recipeMalt.setMaltId(UUID.fromString(MALT_ID));
        recipeMalt.setMaltAmountInKilos(BigDecimal.valueOf(6.67));
        recipe.addRecipeMalt(recipeMalt);

        RecipeHop recipeHop = new RecipeHop();
        recipeHop.setId(UUID.fromString("e2a7056f-d4c6-49b9-b5da-44db0566b664"));
        recipeHop.setHopId(UUID.fromString(HOP_ID));
        recipeHop.setHopAmountInGrams(BigDecimal.valueOf(10));
        recipeHop.setBoilingTimeInMinutes(Duration.ofMinutes(60));
        recipe.addRecipeHop(recipeHop);

        return recipe;
    }

    private Optional<Malt> getMaltFromRepository () {
        Malt malt = new Malt();
        malt.setId(UUID.fromString(MALT_ID));
        malt.setName("TestMalt");
        malt.setExtractionRateInPercentage(BigDecimal.valueOf(82.5));
        malt.setMeanColorInEbcScale(BigDecimal.valueOf(2.8));
        return Optional.of(malt);
    }

    private Optional<Hop> getHopFromRepository () {
        Hop hop = new Hop();
        hop.setId(UUID.fromString(HOP_ID));
        hop.setName("TestHop");
        hop.setAlfaAcidInPercentage(BigDecimal.valueOf(8.5));
        return Optional.of(hop);
    }
}
