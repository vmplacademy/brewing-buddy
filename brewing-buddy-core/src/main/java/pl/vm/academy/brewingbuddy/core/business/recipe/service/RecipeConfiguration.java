package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.CalculatedParametersMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeCalculatedParametersRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;

@Configuration
public class RecipeConfiguration {

    @Bean
    RecipeFacade recipeFacade(RecipeRepository recipeRepository, RecipeMapper recipeMapper,
                              RecipeCalculatedParametersRepository recipeCalculatedParametersRepository,
                              CalculatedParametersMapper calculatedParametersMapper) {

        RecipeServiceAdapter recipeService = new RecipeServiceAdapter(recipeRepository, recipeCalculatedParametersRepository,
                recipeMapper, calculatedParametersMapper);

        return new RecipeFacade(recipeService);
    }
}
