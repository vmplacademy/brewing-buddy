package pl.vm.academy.brewingbuddy.core.business.recipe.converter;

import org.springframework.stereotype.Component;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.CalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeCalculatedParameters;

@Component
public class CalculatedParametersConverter {
    public CalculatedParametersDto parametersToDto (RecipeCalculatedParameters recipeCalculatedParameters) {
        CalculatedParametersDto calculatedParametersDto = new CalculatedParametersDto();

        calculatedParametersDto.setMashingFactorInLitersPerKg(recipeCalculatedParameters.getMashingFactorInLitersPerKg());
        calculatedParametersDto.setWaterRequiredForMashingInLiters(recipeCalculatedParameters.getWaterRequiredForMashingInLiters());
        calculatedParametersDto.setWaterRequiredForSpargingInLiters(recipeCalculatedParameters.getWaterRequiredForSpargingInLiters());
        calculatedParametersDto.setWaterRequiredForWholeProcessInLiters(recipeCalculatedParameters.getWaterRequiredForWholeProcessInLiters());
        calculatedParametersDto.setAmountOfWaterBeforeBoilingInLiters(recipeCalculatedParameters.getAmountOfWaterBeforeBoilingInLiters());
        calculatedParametersDto.setExtractBeforeBoilingInPercentage(recipeCalculatedParameters.getExtractBeforeBoilingInPercentage());
        calculatedParametersDto.setAmountOfHotWort(recipeCalculatedParameters.getAmountOfHotWort());
        calculatedParametersDto.setCalculatedIbu(recipeCalculatedParameters.getCalculatedIbu());
        calculatedParametersDto.setCalculatedColourEBC(recipeCalculatedParameters.getCalculatedColourEBC());
        calculatedParametersDto.setCalculatedExtractInPercentage(recipeCalculatedParameters.getCalculatedExtractInPercentage());
        calculatedParametersDto.setEstimatedAmountOfAlcoholAfterFermentation(recipeCalculatedParameters.getEstimatedAmountOfAlcoholAfterFermentation());

        return calculatedParametersDto;
    }
}
