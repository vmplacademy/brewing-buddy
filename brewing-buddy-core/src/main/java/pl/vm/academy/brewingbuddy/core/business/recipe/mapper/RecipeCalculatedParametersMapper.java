package pl.vm.academy.brewingbuddy.core.business.recipe.mapper;

import org.springframework.stereotype.Component;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeCalculatedParameter;

@Component
public class RecipeCalculatedParametersMapper {
    public RecipeCalculatedParametersDto mapParametersToDto(RecipeCalculatedParameter recipeCalculatedParameter) {

        return RecipeCalculatedParametersDto.builder()
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
    }
}
