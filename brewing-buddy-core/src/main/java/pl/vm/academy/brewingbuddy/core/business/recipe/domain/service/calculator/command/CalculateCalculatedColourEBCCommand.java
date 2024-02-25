package pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator.command;

import org.springframework.util.CollectionUtils;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.service.IngredientFacade;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator.CalculatorConstants;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeMaltDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record CalculateCalculatedColourEBCCommand(
    IngredientFacade ingredientFacade
)
    implements RecipeCalculatorCommand {

  @Override
  public RecipeDetailedDto execute(RecipeDetailedDto recipe) {
    if (!CollectionUtils.isEmpty(recipe.recipeMalts())) {
      BigDecimal sumEbc = BigDecimal.valueOf(0);
      BigDecimal overallAmountOfMaltInKg = recipe.recipeCalculatedParametersDto()
          .overallAmountOfMaltInKg();
      for (RecipeMaltDto recipeMaltDto : recipe.recipeMalts()) {
        sumEbc = sumEbc.add(overallAmountOfMaltInKg
            .multiply(CalculatorConstants.EBC_COLOUR_CONSTANT)
            .multiply(ingredientFacade.getMaltById(recipeMaltDto.maltId()).meanColorInEbcScale())
            .divide(CalculatorConstants.EBC_TO_SRM_RATIO, 2, RoundingMode.FLOOR));
      }

      BigDecimal helpBD = BigDecimal.valueOf(Math.pow(sumEbc
          .divide(recipe.recipeCalculatedParametersDto().amountOfHotWort()
              .divide(CalculatorConstants.GALLON_LITER_FACTOR, RoundingMode.FLOOR), 5, RoundingMode.FLOOR)
          .doubleValue(), CalculatorConstants.MCU_POW_CONSTANT));

      BigDecimal calculatedColourEBC = (CalculatorConstants.EBC_TO_SRM_RATIO
          .multiply(CalculatorConstants.MCU_CONSTANT).multiply(helpBD));

      RecipeCalculatedParametersDto recipeCalculatedParametersDto
          = recipe.recipeCalculatedParametersDto().withCalculatedColourEBC(calculatedColourEBC);

      return recipe.withRecipeCalculatedParametersDto(recipeCalculatedParametersDto);
    }
    return recipe;
  }
}
