package pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator.command;

import org.springframework.util.CollectionUtils;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.service.IngredientFacade;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.utils.HopUtilisation;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeHopDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record CalculateCalculatedIbuCommand
        (
                IngredientFacade ingredientFacade,
                HopUtilisation hopUtilisation
        )
        implements RecipeCalculatorCommand {
    @Override
    public RecipeDetailedDto execute(RecipeDetailedDto recipe) {
        if (!CollectionUtils.isEmpty(recipe.recipeHops())) {

            BigDecimal ibu = BigDecimal.valueOf(0);

            for (RecipeHopDto recipeHopDto : recipe.recipeHops()) {
                ibu = ibu.add(recipeHopDto.hopAmountInGrams()
                        .multiply(ingredientFacade.getHopById(recipeHopDto.hopId()).alfaAcidInPercentage())
                        .multiply(hopUtilisation.getHopUtilisation((int)recipeHopDto.boilingTimeInMinutes().toMinutes()))
                        .divide(recipe.recipeCalculatedParametersDto().amountOfHotWort(), 2, RoundingMode.FLOOR)
                        .divide(BigDecimal.valueOf(10), 2, RoundingMode.FLOOR));
            }
            RecipeCalculatedParametersDto recipeCalculatedParametersDto
                    = recipe.recipeCalculatedParametersDto().withCalculatedIbu(ibu);

            return recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto);
        }
        return recipe;
    }
}
