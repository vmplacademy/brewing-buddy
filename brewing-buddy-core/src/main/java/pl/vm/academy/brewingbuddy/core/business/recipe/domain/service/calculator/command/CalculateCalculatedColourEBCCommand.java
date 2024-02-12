package pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator.command;

import org.springframework.util.CollectionUtils;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.service.IngredientFacade;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record CalculateCalculatedColourEBCCommand
        (
                IngredientFacade ingredientFacade
        )
        implements RecipeCalculatorCommand{
    @Override
    public RecipeDetailedDto execute(RecipeDetailedDto recipe) {
        if (!CollectionUtils.isEmpty(recipe.recipeMalts())) {
            BigDecimal sumEbc = BigDecimal.valueOf(0);
            BigDecimal overallAmountOfMaltInKg = recipe.recipeCalculatedParametersDto().overallAmountOfMaltInKg();
            for (RecipeMaltDto recipeMaltDto : recipe.recipeMalts()) {
                sumEbc = sumEbc.add(overallAmountOfMaltInKg
                        .multiply(BigDecimal.valueOf(2.20462262184878))
                        .multiply(ingredientFacade.getMaltById(recipeMaltDto.maltId()).meanColorInEbcScale())
                        .divide(BigDecimal.valueOf(1.97), 2, RoundingMode.FLOOR));
            }

            BigDecimal helpBD = BigDecimal.valueOf(Math.pow(sumEbc
                    .divide(recipe.recipeCalculatedParametersDto().amountOfHotWort()
                            .divide(BigDecimal.valueOf(3.78541178), RoundingMode.FLOOR),5, RoundingMode.FLOOR).doubleValue(), 0.6859));

            BigDecimal calculatedColourEBC = (BigDecimal.valueOf(1.97)
                    .multiply(BigDecimal.valueOf(1.4922)).multiply(helpBD));

            RecipeCalculatedParametersDto recipeCalculatedParametersDto
                    = recipe.recipeCalculatedParametersDto().withCalculatedColourEBC(calculatedColourEBC);

            return recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto);
        }
        return recipe;
    }
}
