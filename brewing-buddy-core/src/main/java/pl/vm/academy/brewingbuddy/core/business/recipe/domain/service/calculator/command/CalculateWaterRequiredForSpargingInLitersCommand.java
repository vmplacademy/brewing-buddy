package pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator.command;

import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record CalculateWaterRequiredForSpargingInLitersCommand() implements RecipeCalculatorCommand {
    @Override
    public RecipeDetailedDto execute(RecipeDetailedDto recipe) {
        BigDecimal overallAmountOfMalt = recipe.recipeCalculatedParametersDto()
            .overallAmountOfMaltInKg();

        BigDecimal hotWort = recipe.recipeCalculatedParametersDto().amountOfHotWort();

        BigDecimal waterThatLeftAfterFiltrationInLiters = overallAmountOfMalt.multiply(
            BigDecimal.valueOf(0.7));

        BigDecimal waterLostDuringBoilingInLiter = calculateWaterLostDuringBoilingInLiter(hotWort,
            recipe.boilingProcessLossInPercentage());

        BigDecimal waterForMashing = recipe.recipeCalculatedParametersDto()
            .waterRequiredForMashingInLiters();

        BigDecimal waterForSparging = calculateWaterForSparging(hotWort,
            waterThatLeftAfterFiltrationInLiters, waterLostDuringBoilingInLiter, waterForMashing);

        RecipeCalculatedParametersDto recipeCalculatedParametersDto;

        if (waterForSparging.compareTo(BigDecimal.valueOf(0)) <= 0) {
            recipeCalculatedParametersDto = recipe.recipeCalculatedParametersDto()
                .withWaterRequiredForSpargingInLiters(BigDecimal.valueOf(0));
        } else {
            recipeCalculatedParametersDto = recipe.recipeCalculatedParametersDto()
                .withWaterRequiredForSpargingInLiters(waterForSparging);
        }
        return recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto);
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
}
