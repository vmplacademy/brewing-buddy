package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.vm.academy.brewingbuddy.core.business.recipe.converter.CalculatedParametersConverter;
import pl.vm.academy.brewingbuddy.core.business.recipe.converter.RecipeConverter;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeCalculatedParametersRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;

@Configuration
public class RecipeConfiguration {
    @Bean
    RecipeFacade recipeFacade(RecipeRepository recipeRepository, RecipeConverter recipeConverter,
                              RecipeCalculatedParametersRepository recipeCalculatedParametersRepository,
                              CalculatedParametersConverter calculatedParametersConverter) {

        RecipeService recipeService = new RecipeService(recipeRepository, recipeCalculatedParametersRepository,
                recipeConverter, calculatedParametersConverter);

        return new RecipeFacade(recipeService);
    }
}
