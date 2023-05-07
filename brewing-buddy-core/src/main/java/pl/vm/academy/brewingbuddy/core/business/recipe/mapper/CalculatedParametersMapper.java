package pl.vm.academy.brewingbuddy.core.business.recipe.mapper;

import org.springframework.stereotype.Component;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.CalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeCalculatedParameters;

@Component
public class CalculatedParametersMapper {
    public CalculatedParametersDto parametersToDto (RecipeCalculatedParameters recipeCalculatedParameters) {

        CalculatedParametersDto calculatedParametersDto = CalculatedParametersDto.builder()
                .mashingFactorInLitersPerKg(recipeCalculatedParameters.getMashingFactorInLitersPerKg())
                .waterRequiredForMashingInLiters(recipeCalculatedParameters.getWaterRequiredForMashingInLiters())
                .waterRequiredForSpargingInLiters(recipeCalculatedParameters.getWaterRequiredForSpargingInLiters())
                .amountOfWaterBeforeBoilingInLiters(recipeCalculatedParameters.getWaterRequiredForWholeProcessInLiters())
                .amountOfWaterBeforeBoilingInLiters(recipeCalculatedParameters.getAmountOfWaterBeforeBoilingInLiters())
                .extractBeforeBoilingInPercentage(recipeCalculatedParameters.getExtractBeforeBoilingInPercentage())
                .amountOfHotWort(recipeCalculatedParameters.getAmountOfHotWort())
                .calculatedIbu(recipeCalculatedParameters.getCalculatedIbu())
                .calculatedColourEBC(recipeCalculatedParameters.getCalculatedColourEBC())
                .calculatedExtractInPercentage(recipeCalculatedParameters.getCalculatedExtractInPercentage())
                .estimatedAmountOfAlcoholAfterFermentation(recipeCalculatedParameters.getEstimatedAmountOfAlcoholAfterFermentation())
                .build();

        return calculatedParametersDto;
    }
}
