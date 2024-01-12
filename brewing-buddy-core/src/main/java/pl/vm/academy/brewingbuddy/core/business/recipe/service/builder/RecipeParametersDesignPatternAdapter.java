package pl.vm.academy.brewingbuddy.core.business.recipe.service.builder;

import lombok.AllArgsConstructor;
import pl.vm.academy.brewingbuddy.core.business.ingredient.service.IngredientFacade;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.command.CalculateOverallAmountOfMaltInKgCommand;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.command.CalculateWaterRequiredForMashingInLitersCommand;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.command.RecipeCalculatorCommandExecutor;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.decorator.RecipeCalculatorDecorator;
import pl.vm.academy.brewingbuddy.core.business.recipe.service.utils.HopUtilisation;

import java.math.BigDecimal;
import java.util.List;

public record RecipeParametersDesignPatternAdapter(
        RecipeRepository recipeRepository,
        RecipeMapper recipeMapper,
        IngredientFacade ingredientFacade,
        HopUtilisation hopUtilisation

) {
    public RecipeParametersDesignPatternAdapter(RecipeRepository recipeRepository, RecipeMapper recipeMapper, IngredientFacade ingredientFacade, HopUtilisation hopUtilisation) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
        this.ingredientFacade = ingredientFacade;
        this.hopUtilisation = hopUtilisation;
    }

    public RecipeCalculatedParametersDto calculateParametersDecorator(Recipe recipe) {

        RecipeDetailedDto recipeDetailedDto = recipeMapper.mapRecipeToDetailedDto(recipe);

        return new RecipeCalculatorDecorator(recipeDetailedDto, ingredientFacade, hopUtilisation)
                .calculateOverallAmountOfMaltInKg()
                .calculateAmountOfHotWort()
                .calculateWaterRequiredForMashingInLiters()
                .calculateWaterRequiredForSpargingInLiters()
                .calculateWaterRequiredForWholeProcessInLiters()
                .calculateAmountOfWaterBeforeBoilingInLiters()
                .calculateTheoreticalExtract()
                .calculateRealExtract()
                .calculateWortWeightInGrams()
                .calculateExtractBeforeBoilingInPercentage()
                .calculateCalculatedIbu()
                .calculateCalculatedColourEBC()
                .calculateCalculatedExtractInPercentage()
                .calculateEstimatedAmountOfAlcoholAfterFermentation()
                .getCalculatedParameters();
    }

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



    public RecipeCalculatedParametersDto calculateParametersBuilder(Recipe recipe) {

        RecipeDetailedDto recipeDetailedDto = recipeMapper.mapRecipeToDetailedDto(recipe);

        RecipeCalculatedParametersDto paramters = recipeDetailedDto.recipeCalculatedParametersDto();

        return RecipeCalculatorBuilder.builder()
                .waterRequiredForMashingInLiters(paramters.waterRequiredForMashingInLiters())
                .overallAmountOfMaltInKg(paramters.overallAmountOfMaltInKg())
                .build()
                .calculateOverallAmountOfMaltInKg(recipeDetailedDto)
                .calculateWaterRequiredForMashingInLiters(recipeDetailedDto)
                .build();
    }
}
