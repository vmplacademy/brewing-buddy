package pl.vm.academy.brewingbuddy.core.business.recipe.service.builder;

import lombok.Builder;
import lombok.With;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

@With
@Builder
public record RecipeCalculatorBuilder(
        BigDecimal overallAmountOfMaltInKg,
        BigDecimal amountOfHotWort,
        BigDecimal waterRequiredForMashingInLiters,
        BigDecimal waterRequiredForSpargingInLiters,
        BigDecimal waterRequiredForWholeProcessInLiters,
        BigDecimal amountOfWaterBeforeBoilingInLiters,
        BigDecimal theoreticalExtract,
        BigDecimal realExtract,
        BigDecimal wortWeightInGrams,
        BigDecimal extractBeforeBoilingInPercentage,
        BigDecimal calculatedIbu,
        BigDecimal calculatedColourEBC,
        BigDecimal calculatedExtractInPercentage,
        BigDecimal EstimatedAmountOfAlcoholAfterFermentation
) {
    RecipeCalculatorBuilder calculateOverallAmountOfMaltInKg(RecipeDetailedDto recipe) {
        BigDecimal overallAmountOfMalt = BigDecimal.valueOf(0);

        for (RecipeMaltDto recipeMalt : recipe.recipeMalts()) {
            overallAmountOfMalt = overallAmountOfMalt.add(recipeMalt.maltAmountInKilos());
        }

        return this.withOverallAmountOfMaltInKg(overallAmountOfMalt);
    }

    RecipeCalculatorBuilder calculateAmountOfHotWort(RecipeDetailedDto recipe) {
        BigDecimal amountOfHotWort =  recipe.expectedAmountOfBeerInLiters()
                .divide(BigDecimal.valueOf(0.96)
                        .subtract(recipe.fermentationProcessLossInPercentage()
                                .divide(BigDecimal.valueOf(100), 2, RoundingMode.FLOOR )), 2, RoundingMode.FLOOR);

        return this.withAmountOfHotWort(amountOfHotWort);
    }

    RecipeCalculatorBuilder calculateWaterRequiredForMashingInLiters(RecipeDetailedDto recipe) {
        BigDecimal overallAmountOfMalt = recipe.recipeCalculatedParametersDto().overallAmountOfMaltInKg();

        BigDecimal waterRequired = overallAmountOfMalt.multiply(recipe.mashingFactorInLitersPerKg());

        return this.withWaterRequiredForMashingInLiters(waterRequired);
    }


    RecipeCalculatorBuilder calculateWaterRequiredForSpargingInLiters(RecipeDetailedDto recipe) {
        BigDecimal overallAmountOfMalt = recipe.recipeCalculatedParametersDto().overallAmountOfMaltInKg();

        BigDecimal hotWort =  recipe.recipeCalculatedParametersDto().amountOfHotWort();

        BigDecimal waterThatLeftAfterFiltrationInLiters = overallAmountOfMalt.multiply(BigDecimal.valueOf(0.7));

        BigDecimal waterLostDuringBoilingInLiter = hotWort.divide(BigDecimal.valueOf(1).subtract(recipe.boilingProcessLossInPercentage()
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.FLOOR)), 2, RoundingMode.FLOOR).subtract(hotWort);

        BigDecimal waterForMashing = recipe.recipeCalculatedParametersDto().waterRequiredForMashingInLiters();

        BigDecimal waterForSparging = hotWort.add(waterThatLeftAfterFiltrationInLiters).add(waterLostDuringBoilingInLiter).subtract(waterForMashing);

        if (waterForSparging.compareTo(BigDecimal.valueOf(0)) <= 0) {
            return this.withWaterRequiredForSpargingInLiters(BigDecimal.valueOf(0));
        } else {
            return this.withWaterRequiredForSpargingInLiters(waterForSparging);
        }
    }

    RecipeCalculatedParametersDto build() {
        return RecipeCalculatedParametersDto.builder()
                .overallAmountOfMaltInKg(overallAmountOfMaltInKg)
                .amountOfHotWort(amountOfHotWort)
                .waterRequiredForMashingInLiters(waterRequiredForMashingInLiters)
                .waterRequiredForSpargingInLiters(waterRequiredForSpargingInLiters)
                .build();
    }
}
