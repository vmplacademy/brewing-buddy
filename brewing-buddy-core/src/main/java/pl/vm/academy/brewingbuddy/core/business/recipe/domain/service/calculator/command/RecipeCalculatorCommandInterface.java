package pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator.command;

import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.Recipe;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeCalculatedParametersDto;

public interface RecipeCalculatorCommandInterface {
  RecipeCalculatedParametersDto calculateParametersCommand(Recipe recipe);
}
