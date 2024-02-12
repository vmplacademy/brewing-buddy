package pl.vm.academy.brewingbuddy.core.business.recipe.mapper;

import org.springframework.stereotype.Component;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.RecipeCalculatedParameter;

@Component
public record RecipeCalculatedParametersMapper() {
    public RecipeCalculatedParametersDto mapParametersToDto(RecipeCalculatedParameter recipeCalculatedParameter) {

        return RecipeCalculatedParametersDto.builder()
                .waterRequiredForMashingInLiters(recipeCalculatedParameter.getWaterRequiredForMashingInLiters())
                .waterRequiredForSpargingInLiters(recipeCalculatedParameter.getWaterRequiredForSpargingInLiters())
                .amountOfWaterBeforeBoilingInLiters(recipeCalculatedParameter.getWaterRequiredForWholeProcessInLiters())
                .amountOfWaterBeforeBoilingInLiters(recipeCalculatedParameter.getAmountOfWaterBeforeBoilingInLiters())
                .extractBeforeBoilingInPercentage(recipeCalculatedParameter.getExtractBeforeBoilingInPercentage())
                .amountOfHotWort(recipeCalculatedParameter.getAmountOfHotWort())
                .calculatedIbu(recipeCalculatedParameter.getCalculatedIbu())
                .calculatedColourEBC(recipeCalculatedParameter.getCalculatedColourEBC())
                .calculatedExtractInPercentage(recipeCalculatedParameter.getCalculatedExtractInPercentage())
                .estimatedAmountOfAlcoholAfterFermentation(recipeCalculatedParameter.getEstimatedAmountOfAlcoholAfterFermentation())
                .overallAmountOfMaltInKg(recipeCalculatedParameter.getOverallAmountOfMaltInKg())
                .theoreticalExtractInGrams(recipeCalculatedParameter.getTheoreticalExtractInGrams())
                .realExtractInGrams(recipeCalculatedParameter.getRealExtractInGrams())
                .wortWeightInGrams(recipeCalculatedParameter.getWortWeightInGrams())
                .build();
    }
}
