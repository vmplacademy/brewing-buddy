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
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeCalculatedParameter;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeHop;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeMalt;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.RecipeParametersCalculator;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.RecipeParametersCalculatorAdapter;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.RecipeServiceAdapter;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.utils.HopUtilisation;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecipeParametersCalculatorTest {

    private final HopUtilisation hopUtilisation = new HopUtilisation();

    @Mock
    private IngredientFacade ingredientFacade;

    private RecipeParametersCalculator recipeParametersCalculator;

    private MaltMapper maltMapper = new MaltMapper();

    private HopMapper hopMapper = new HopMapper();

    @BeforeEach
    void init() {
        recipeParametersCalculator = new RecipeParametersCalculatorAdapter(
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
        recipeParametersCalculator.calculateParameters(recipe);
        RecipeCalculatedParameter calcParam = recipe.getRecipeCalculatedParameter();
        //then

        assertThat(calcParam.getOverallAmountOfMaltInKg())
                .isCloseTo(BigDecimal.valueOf(6.67), Percentage.withPercentage(2));

        assertThat(calcParam.getAmountOfHotWort())
                .isCloseTo(BigDecimal.valueOf(24.2), Percentage.withPercentage(1));

        assertThat(calcParam.getWaterRequiredForMashingInLiters())
                .isCloseTo(BigDecimal.valueOf(20), Percentage.withPercentage(2));

        assertThat(calcParam.getWaterRequiredForSpargingInLiters())
                .isCloseTo(BigDecimal.valueOf(13.1), Percentage.withPercentage(2));

        assertThat(calcParam.getWaterRequiredForWholeProcessInLiters())
                .isCloseTo(BigDecimal.valueOf(33.1), Percentage.withPercentage(2));

        assertThat(calcParam.getAmountOfWaterBeforeBoilingInLiters())
                .isCloseTo(BigDecimal.valueOf(28.5), Percentage.withPercentage(2));

        assertThat(calcParam.getTheoreticalExtractInGrams())
                .isCloseTo(BigDecimal.valueOf(5503), Percentage.withPercentage(2));

        assertThat(calcParam.getRealExtractInGrams())
                .isCloseTo(BigDecimal.valueOf(4402), Percentage.withPercentage(2));

        assertThat(calcParam.getWortWeightInGrams())
                .isCloseTo(BigDecimal.valueOf(25828), Percentage.withPercentage(2));

        assertThat(calcParam.getExtractBeforeBoilingInPercentage())
               .isCloseTo(BigDecimal.valueOf(14.6), Percentage.withPercentage(2));

        assertThat(calcParam.getCalculatedExtractInPercentage())
                .isCloseTo(BigDecimal.valueOf(17), Percentage.withPercentage(2));

        assertThat(calcParam.getCalculatedIbu())
                .isCloseTo(BigDecimal.valueOf(10.5), Percentage.withPercentage(2));

        assertThat(calcParam.getEstimatedAmountOfAlcoholAfterFermentation())
                .isCloseTo(BigDecimal.valueOf(10.5), Percentage.withPercentage(2));


        //assertThat(calcParam.getCalculatedColourEBC())
        //        .isCloseTo(BigDecimal.valueOf(7), Percentage.withPercentage(2));

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
