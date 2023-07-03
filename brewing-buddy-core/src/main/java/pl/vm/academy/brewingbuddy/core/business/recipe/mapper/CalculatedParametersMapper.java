package pl.vm.academy.brewingbuddy.core.business.recipe.mapper;

import org.springframework.stereotype.Component;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.CalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeCalculatedParameter;

@Component
public class CalculatedParametersMapper {
    public CalculatedParametersDto mapParametersToDto(RecipeCalculatedParameter recipeCalculatedParameter) {

        CalculatedParametersDto calculatedParametersDto = CalculatedParametersDto.builder()
                .mashingFactorInLitersPerKg(recipeCalculatedParameter.getMashingFactorInLitersPerKg())
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
                .build();

        return calculatedParametersDto;
    }
}
