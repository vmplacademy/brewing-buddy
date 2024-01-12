package pl.vm.academy.brewingbuddy.core.business.recipe.service.builder;

import lombok.Builder;
import lombok.With;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;

import java.math.BigDecimal;
import java.util.Set;

@With
@Builder
public record RecipeCalculatorBuilder(
        BigDecimal waterRequiredForMashingInLiters,
        BigDecimal overallAmountOfMaltInKg,
        Long additionalParameter
) {
    RecipeCalculatorBuilder calculateOverallAmountOfMaltInKg(RecipeDetailedDto recipe) {
        BigDecimal overallAmountOfMalt = BigDecimal.valueOf(0);

        for (RecipeMaltDto recipeMalt : recipe.recipeMalts()) {
            overallAmountOfMalt = overallAmountOfMalt.add(recipeMalt.maltAmountInKilos());
        }

        return this.withOverallAmountOfMaltInKg(overallAmountOfMaltInKg);
    }

    RecipeCalculatorBuilder calculateWaterRequiredForMashingInLiters(RecipeDetailedDto recipe) {
        BigDecimal overallAmountOfMalt = recipe.recipeCalculatedParametersDto().overallAmountOfMaltInKg();

        BigDecimal waterRequired = overallAmountOfMalt.multiply(recipe.mashingFactorInLitersPerKg());

        return this.withWaterRequiredForMashingInLiters(waterRequired);
    }

    RecipeCalculatedParametersDto build() {
        return RecipeCalculatedParametersDto.builder()
                .overallAmountOfMaltInKg(overallAmountOfMaltInKg)
                .waterRequiredForMashingInLiters(waterRequiredForMashingInLiters)
                .build();

        /*
        calculateOverallAmountOfMaltInKg (recipe);
        calculateAmountOfHotWort(recipe);
        calculateWaterRequiredForMashingInLiters(recipe);
        calculateWaterRequiredForSpargingInLiters(recipe);
        calculateWaterRequiredForWholeProcessInLiters(recipe);
        calculateAmountOfWaterBeforeBoilingInLiters(recipe);
        calculateTheoreticalExtract(recipe);
        calculateRealExtract(recipe);
        calculateWortWeightInGrams(recipe);
        calculateExtractBeforeBoilingInPercentage(recipe);
        calculateCalculatedIbu(recipe);
        calculateCalculatedColourEBC(recipe);
        calculateCalculatedExtractInPercentage(recipe);
        calculateEstimatedAmountOfAlcoholAfterFermentation(recipe);
         */
    }
}
