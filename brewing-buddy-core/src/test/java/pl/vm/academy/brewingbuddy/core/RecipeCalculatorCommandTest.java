package pl.vm.academy.brewingbuddy.core;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.HopDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.MaltDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.HopMapper;
import pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.MaltMapper;
import pl.vm.academy.brewingbuddy.core.business.ingredient.model.Hop;
import pl.vm.academy.brewingbuddy.core.business.ingredient.model.Malt;
import pl.vm.academy.brewingbuddy.core.business.ingredient.service.IngredientFacade;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeCalculatedParametersMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeExtraIngredientMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeHopMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMaltMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeYeastMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeCalculatedParameter;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeHop;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeMalt;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.command.RecipeCalculatorCommandAdapter;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.utils.HopUtilisation;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeCalculatorCommandTest {
    private final HopUtilisation hopUtilisation = new HopUtilisation();

    @Mock
    private IngredientFacade ingredientFacade;

    private RecipeCalculatorCommandAdapter recipeParametersCalculator;

    private final HopMapper hopMapper = new HopMapper();
    private final MaltMapper maltMapper = new MaltMapper();
    private final RecipeMapper recipeMapper = new RecipeMapper(
            new RecipeCalculatedParametersMapper(),
            new RecipeHopMapper(),
            new RecipeMaltMapper(),
            new RecipeExtraIngredientMapper() ,
            new RecipeYeastMapper());

    @BeforeEach
    void init() {
        recipeParametersCalculator = new RecipeCalculatorCommandAdapter(
                recipeMapper,
                ingredientFacade,
                hopUtilisation);
    }

    @Test
    void should_correctly_calculate_recipe() {
        //given
        Recipe recipe = createValidRecipe();
        when(ingredientFacade.getMaltById(any(UUID.class))).thenReturn(getMaltFromFacade());
        when(ingredientFacade.getHopById(any(UUID.class))).thenReturn(getHopFromFacade());

        //when
        RecipeCalculatedParametersDto calcParam = recipeParametersCalculator.calculateParametersCommand(recipe);
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
        recipeMalt.setMaltId(UUID.fromString("e3c8ef49-d78e-425e-b3b4-459c44db6456"));
        recipeMalt.setMaltAmountInKilos(BigDecimal.valueOf(6.67));
        recipe.addRecipeMalt(recipeMalt);

        RecipeHop recipeHop = new RecipeHop();
        recipeHop.setId(UUID.fromString("e2a7056f-d4c6-49b9-b5da-44db0566b664"));
        recipeHop.setHopId(UUID.fromString("9b4cad8b-cee6-4fb1-a80d-52354c9efd4f"));
        recipeHop.setHopAmountInGrams(BigDecimal.valueOf(10));
        recipeHop.setBoilingTimeInMinutes(Duration.ofMinutes(60));
        recipe.addRecipeHop(recipeHop);

        return recipe;
    }

    private MaltDto getMaltFromFacade () {
        Malt malt = new Malt();
        malt.setId(UUID.fromString("e3c8ef49-d78e-425e-b3b4-459c44db6456"));
        malt.setName("TestMalt");
        malt.setExtractionRateInPercentage(BigDecimal.valueOf(82.5));
        malt.setMeanColorInEbcScale(BigDecimal.valueOf(2.8));
        return maltMapper.mapMaltToDto(malt);
    }

    private HopDto getHopFromFacade () {
        Hop hop = new Hop();
        hop.setId(UUID.fromString("9b4cad8b-cee6-4fb1-a80d-52354c9efd4f"));
        hop.setName("TestHop");
        hop.setAlfaAcidInPercentage(BigDecimal.valueOf(8.5));
        return hopMapper.mapHopToDto(hop);
    }
}
