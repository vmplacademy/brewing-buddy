package pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator.command;

import org.springframework.util.CollectionUtils;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.service.IngredientFacade;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;

import java.math.BigDecimal;

public record CalculateTheoreticalExtractCommand
        (
                IngredientFacade ingredientFacade
        )
        implements RecipeCalculatorCommand{
    @Override
    public RecipeDetailedDto execute(RecipeDetailedDto recipe) {
        BigDecimal theoreticalExtractInGrams = BigDecimal.valueOf(0);

        if (!CollectionUtils.isEmpty(recipe.recipeMalts())) {
            for (RecipeMaltDto recipeMaltDto : recipe.recipeMalts()) {
                theoreticalExtractInGrams = theoreticalExtractInGrams.add(recipeMaltDto.maltAmountInKilos().
                        multiply(ingredientFacade.getMaltById(recipeMaltDto.maltId()).extractionRateInPercentage().multiply(BigDecimal.valueOf(10))));
            }
        }

        RecipeCalculatedParametersDto recipeCalculatedParametersDto
                = recipe.recipeCalculatedParametersDto().withTheoreticalExtractInGrams(theoreticalExtractInGrams);

        return recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto);
    }
}
