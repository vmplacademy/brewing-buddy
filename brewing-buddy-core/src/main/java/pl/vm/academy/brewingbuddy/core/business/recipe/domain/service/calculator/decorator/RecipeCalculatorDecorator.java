package pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator.decorator;

import lombok.With;
import org.springframework.util.CollectionUtils;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.service.IngredientFacade;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator.CalculatorConstants;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.utils.HopUtilisation;

import java.math.BigDecimal;
import java.math.RoundingMode;

@With
public record RecipeCalculatorDecorator(
        RecipeDetailedDto recipe,
        IngredientFacade ingredientFacade,
        HopUtilisation hopUtilisation
) {

    public RecipeCalculatorDecorator calculateOverallAmountOfMaltInKg() {
        BigDecimal overallAmountOfMalt = BigDecimal.valueOf(0);

        for (RecipeMaltDto recipeMalt : recipe.recipeMalts()) {
            overallAmountOfMalt = overallAmountOfMalt.add(recipeMalt.maltAmountInKilos());
        }

        RecipeCalculatedParametersDto recipeCalculatedParametersDto = recipe.recipeCalculatedParametersDto()
                .withOverallAmountOfMaltInKg(overallAmountOfMalt);

        return this.withRecipe(recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto));
    }

    public RecipeCalculatorDecorator calculateWaterRequiredForMashingInLiters() {
        BigDecimal overallAmountOfMalt = recipe.recipeCalculatedParametersDto().overallAmountOfMaltInKg();

        BigDecimal waterRequired = overallAmountOfMalt.multiply(recipe.mashingFactorInLitersPerKg());

        RecipeCalculatedParametersDto recipeCalculatedParametersDto = recipe.recipeCalculatedParametersDto()
                .withWaterRequiredForMashingInLiters(waterRequired);

        return this.withRecipe(recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto));
    }

    public RecipeCalculatorDecorator calculateWaterRequiredForSpargingInLiters() {
        BigDecimal overallAmountOfMalt = recipe.recipeCalculatedParametersDto().overallAmountOfMaltInKg();

        BigDecimal hotWort =  recipe.recipeCalculatedParametersDto().amountOfHotWort();

        BigDecimal waterThatLeftAfterFiltrationInLiters = overallAmountOfMalt.multiply(BigDecimal.valueOf(0.7));

        BigDecimal waterLostDuringBoilingInLiter = calculateWaterLostDuringBoilingInLiter(hotWort,
            recipe.boilingProcessLossInPercentage());

        BigDecimal waterForMashing = recipe.recipeCalculatedParametersDto().waterRequiredForMashingInLiters();

        BigDecimal waterForSparging = calculateWaterForSparging(hotWort,
            waterThatLeftAfterFiltrationInLiters, waterLostDuringBoilingInLiter, waterForMashing);

        RecipeCalculatedParametersDto recipeCalculatedParametersDto;

        if (waterForSparging.compareTo(BigDecimal.valueOf(0)) <= 0) {
            recipeCalculatedParametersDto = recipe.recipeCalculatedParametersDto().withWaterRequiredForSpargingInLiters(BigDecimal.valueOf(0));
        } else {
            recipeCalculatedParametersDto = recipe.recipeCalculatedParametersDto().withWaterRequiredForSpargingInLiters(waterForSparging);
        }
        return this.withRecipe(recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto));
    }

    private BigDecimal calculateWaterLostDuringBoilingInLiter(BigDecimal hotWort,
        BigDecimal boilingProcessLossInPercentage) {
        return hotWort.divide(BigDecimal.valueOf(1).subtract(boilingProcessLossInPercentage
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.FLOOR)), 2, RoundingMode.FLOOR)
            .subtract(hotWort);
    }

    private BigDecimal calculateWaterForSparging(
        BigDecimal hotWort,
        BigDecimal waterThatLeftAfterFiltrationInLiters,
        BigDecimal waterLostDuringBoilingInLiter,
        BigDecimal waterForMashing) {
        return hotWort.add(waterThatLeftAfterFiltrationInLiters).add(waterLostDuringBoilingInLiter)
            .subtract(waterForMashing);
    }

    public RecipeCalculatorDecorator calculateWaterRequiredForWholeProcessInLiters() {

        BigDecimal waterRequiredForWholeProcessInLiters = recipe.recipeCalculatedParametersDto().waterRequiredForSpargingInLiters()
                .add(recipe.recipeCalculatedParametersDto().waterRequiredForMashingInLiters());

        RecipeCalculatedParametersDto recipeCalculatedParametersDto
                = recipe.recipeCalculatedParametersDto().withWaterRequiredForWholeProcessInLiters(waterRequiredForWholeProcessInLiters);

        return this.withRecipe(recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto));
    }

    public RecipeCalculatorDecorator calculateAmountOfWaterBeforeBoilingInLiters() {

        BigDecimal amountOfWaterBeforeBoilingInLiters
                = recipe.recipeCalculatedParametersDto().waterRequiredForWholeProcessInLiters()
                .subtract(recipe.recipeCalculatedParametersDto().overallAmountOfMaltInKg().multiply(CalculatorConstants.WATER_PER_KG_AFTER_FILTRATION));

        RecipeCalculatedParametersDto recipeCalculatedParametersDto
                = recipe.recipeCalculatedParametersDto().withAmountOfWaterBeforeBoilingInLiters(amountOfWaterBeforeBoilingInLiters);

        return this.withRecipe(recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto));
    }

    public RecipeCalculatorDecorator calculateTheoreticalExtract () {
        BigDecimal theoreticalExtractInGrams = BigDecimal.valueOf(0);

        if (!CollectionUtils.isEmpty(recipe.recipeMalts())) {
            for (RecipeMaltDto recipeMaltDto : recipe.recipeMalts()) {
                theoreticalExtractInGrams = theoreticalExtractInGrams.add(recipeMaltDto.maltAmountInKilos().
                        multiply(ingredientFacade.getMaltById(recipeMaltDto.maltId()).extractionRateInPercentage().multiply(BigDecimal.valueOf(10))));
            }
        }

        RecipeCalculatedParametersDto recipeCalculatedParametersDto
                = recipe.recipeCalculatedParametersDto().withTheoreticalExtractInGrams(theoreticalExtractInGrams);

        return this.withRecipe(recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto));
    }

    public RecipeCalculatorDecorator calculateRealExtract () {
        BigDecimal realExtract = recipe.recipeCalculatedParametersDto().theoreticalExtractInGrams().
                multiply(recipe.mashingPerformanceInPercentage().divide(BigDecimal.valueOf(100), 2, RoundingMode.FLOOR));

        RecipeCalculatedParametersDto recipeCalculatedParametersDto
                = recipe.recipeCalculatedParametersDto().withRealExtractInGrams(realExtract);

        return this.withRecipe(recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto));
    }

    public RecipeCalculatorDecorator calculateAmountOfHotWort() {
        BigDecimal amountOfHotWort =  recipe.expectedAmountOfBeerInLiters()
                .divide(BigDecimal.valueOf(0.96)
                        .subtract(recipe.fermentationProcessLossInPercentage()
                                .divide(BigDecimal.valueOf(100), 2, RoundingMode.FLOOR )), 2, RoundingMode.FLOOR);

        RecipeCalculatedParametersDto recipeCalculatedParametersDto
                = recipe.recipeCalculatedParametersDto().withAmountOfHotWort(amountOfHotWort);

        return this.withRecipe(recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto));
    }

    public RecipeCalculatorDecorator calculateWortWeightInGrams () {
        BigDecimal amountOfHotWort = recipe.recipeCalculatedParametersDto().amountOfHotWort();
        BigDecimal extractInMilliliters = recipe.recipeCalculatedParametersDto().realExtractInGrams().divide(
            CalculatorConstants.EXTRACT_GRAMS_MLITERS_RATIO, 2, RoundingMode.FLOOR);
        BigDecimal amountOfWaterInWortInMilliliters = amountOfHotWort.multiply(BigDecimal.valueOf(1000)).subtract(extractInMilliliters);

        BigDecimal wortWeight =  amountOfWaterInWortInMilliliters.add(recipe.recipeCalculatedParametersDto().realExtractInGrams());

        RecipeCalculatedParametersDto recipeCalculatedParametersDto
                = recipe.recipeCalculatedParametersDto().withWortWeightInGrams(wortWeight);

        return this.withRecipe(recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto));
    }

    public RecipeCalculatorDecorator calculateExtractBeforeBoilingInPercentage() {
        BigDecimal extractBeforeBoilingInPercentage;
        BigDecimal amountOfHotWort = recipe.recipeCalculatedParametersDto().amountOfHotWort();
        BigDecimal wortWeightInGrams = recipe.recipeCalculatedParametersDto().wortWeightInGrams();
        // 15%
        BigDecimal waterEvaporatedDuringMashing = amountOfHotWort.divide(BigDecimal.valueOf(0.85), 2, RoundingMode.FLOOR).subtract(amountOfHotWort);

        extractBeforeBoilingInPercentage = recipe.recipeCalculatedParametersDto().realExtractInGrams()
                .divide(wortWeightInGrams.divide(BigDecimal.valueOf(1000), 2, RoundingMode.FLOOR).add(waterEvaporatedDuringMashing), 2, RoundingMode.FLOOR)
                .divide(BigDecimal.valueOf(10), 2, RoundingMode.FLOOR);

        RecipeCalculatedParametersDto recipeCalculatedParametersDto
                = recipe.recipeCalculatedParametersDto().withExtractBeforeBoilingInPercentage(extractBeforeBoilingInPercentage);

        return this.withRecipe(recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto));
    }

    public RecipeCalculatorDecorator calculateCalculatedIbu() {

        if (!CollectionUtils.isEmpty(recipe.recipeHops())) {

            BigDecimal ibu = BigDecimal.valueOf(0);

            for (RecipeHopDto recipeHopDto : recipe.recipeHops()) {
                ibu = ibu.add(recipeHopDto.hopAmountInGrams()
                        .multiply(ingredientFacade.getHopById(recipeHopDto.hopId()).alfaAcidInPercentage())
                        .multiply(hopUtilisation.getHopUtilisation((int)recipeHopDto.boilingTimeInMinutes().toMinutes()))
                        .divide(recipe.recipeCalculatedParametersDto().amountOfHotWort(), 2, RoundingMode.FLOOR)
                        .divide(BigDecimal.valueOf(10), 2, RoundingMode.FLOOR));
            }
            RecipeCalculatedParametersDto recipeCalculatedParametersDto
                    = recipe.recipeCalculatedParametersDto().withCalculatedIbu(ibu);

            return this.withRecipe(recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto));
        }
        return this;
    }

    public RecipeCalculatorDecorator calculateCalculatedColourEBC() {

        if (!CollectionUtils.isEmpty(recipe.recipeMalts())) {
            BigDecimal sumEbc = BigDecimal.valueOf(0);
            BigDecimal overallAmountOfMaltInKg = recipe.recipeCalculatedParametersDto().overallAmountOfMaltInKg();
            for (RecipeMaltDto recipeMaltDto : recipe.recipeMalts()) {
                sumEbc = sumEbc.add(overallAmountOfMaltInKg
                        .multiply(CalculatorConstants.EBC_COLOUR_CONSTANT)
                        .multiply(ingredientFacade.getMaltById(recipeMaltDto.maltId()).meanColorInEbcScale())
                        .divide(CalculatorConstants.EBC_TO_SRM_RATIO, 2, RoundingMode.FLOOR));
            }

            BigDecimal helpBD = BigDecimal.valueOf(Math.pow(sumEbc
                .divide(recipe.recipeCalculatedParametersDto().amountOfHotWort()
                    .divide(CalculatorConstants.GALLON_LITER_FACTOR, RoundingMode.FLOOR), 5, RoundingMode.FLOOR).doubleValue(), CalculatorConstants.MCU_POW_CONSTANT));

            BigDecimal calculatedColourEBC = (CalculatorConstants.EBC_TO_SRM_RATIO
                    .multiply(CalculatorConstants.MCU_CONSTANT).multiply(helpBD));

            RecipeCalculatedParametersDto recipeCalculatedParametersDto
                    = recipe.recipeCalculatedParametersDto().withCalculatedColourEBC(calculatedColourEBC);

            return this.withRecipe(recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto));
        }
        return this;
    }

    public RecipeCalculatorDecorator calculateCalculatedExtractInPercentage() {
        BigDecimal realExtract = recipe.recipeCalculatedParametersDto().realExtractInGrams();
        BigDecimal wortWeight = recipe.recipeCalculatedParametersDto().wortWeightInGrams();

        BigDecimal calculatedExtractInPercentage = (realExtract
                .divide(wortWeight, 2, RoundingMode.FLOOR)
                .multiply(BigDecimal.valueOf(100)));

        RecipeCalculatedParametersDto recipeCalculatedParametersDto
                = recipe.recipeCalculatedParametersDto().withCalculatedExtractInPercentage(calculatedExtractInPercentage);

        return this.withRecipe(recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto));
    }

    public RecipeCalculatorDecorator calculateEstimatedAmountOfAlcoholAfterFermentation() {

        RecipeCalculatedParametersDto calcParam = recipe.recipeCalculatedParametersDto();
        BigDecimal startExtract = calcParam.calculatedExtractInPercentage();
        BigDecimal endExtract = startExtract.multiply(BigDecimal.valueOf(1).subtract(CalculatorConstants.FERMENTATION_FACTOR));

        BigDecimal estimatedAmountOfAlcoholAfterFermentation = ((startExtract.subtract(endExtract))
                .divide(CalculatorConstants.BLG_ABV_FACTOR, 2, RoundingMode.FLOOR)
                .add(CalculatorConstants.REFERMENTATION_FACTOR));

        RecipeCalculatedParametersDto recipeCalculatedParametersDto
                = recipe.recipeCalculatedParametersDto().withEstimatedAmountOfAlcoholAfterFermentation(estimatedAmountOfAlcoholAfterFermentation);

        return this.withRecipe(recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto));
    }

    public RecipeCalculatedParametersDto getCalculatedParameters() {
        return recipe.recipeCalculatedParametersDto();
    }
}
