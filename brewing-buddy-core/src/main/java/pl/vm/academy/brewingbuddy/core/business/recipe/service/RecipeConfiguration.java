package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.CalculatedParametersMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeHopMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeCalculatedParametersRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeHopRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;

@Configuration
class RecipeConfiguration {

    @Bean
    RecipeFacadeAdapter recipeFacade(RecipeRepository recipeRepository,
                                     RecipeCalculatedParametersRepository recipeCalculatedParametersRepository,
                                     RecipeHopRepository recipeHopRepository) {

        CalculatedParametersMapper calculatedParametersMapper = new CalculatedParametersMapper();

        RecipeMapper recipeMapper = new RecipeMapper(calculatedParametersMapper);

        RecipeHopMapper recipeHopMapper = new RecipeHopMapper();

        RecipeServiceAdapter recipeService = new RecipeServiceAdapter(
                recipeRepository,
                recipeCalculatedParametersRepository,
                recipeHopRepository,
                recipeMapper,
                calculatedParametersMapper,
                recipeHopMapper);

        return new RecipeFacadeAdapter(recipeService);
    }
}
