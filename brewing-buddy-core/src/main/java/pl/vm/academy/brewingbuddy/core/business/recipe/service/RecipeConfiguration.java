package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.CalculatedParametersMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeExtraIngredientMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeHopMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMaltMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeCalculatedParametersRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeExtraIngredientRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeHopRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeMaltRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;

@Configuration
class RecipeConfiguration {

    @Bean
    RecipeFacadeAdapter recipeFacade(RecipeRepository recipeRepository,
                                     RecipeCalculatedParametersRepository recipeCalculatedParametersRepository,
                                     RecipeHopRepository recipeHopRepository,
                                     RecipeMaltRepository recipeMaltRepository,
                                     RecipeExtraIngredientRepository recipeExtraIngredientRepository) {

        CalculatedParametersMapper calculatedParametersMapper = new CalculatedParametersMapper();

        RecipeMapper recipeMapper = new RecipeMapper(calculatedParametersMapper);
        RecipeHopMapper recipeHopMapper = new RecipeHopMapper();
        RecipeMaltMapper recipeMaltMapper = new RecipeMaltMapper();
        RecipeExtraIngredientMapper recipeExtraIngredientMapper = new RecipeExtraIngredientMapper();

        RecipeServiceAdapter recipeService = new RecipeServiceAdapter(
                recipeRepository,
                recipeCalculatedParametersRepository,
                recipeHopRepository,
                recipeMapper,
                calculatedParametersMapper,
                recipeHopMapper);

        RecipeIngredientServiceAdapter recipeIngredientService = new RecipeIngredientServiceAdapter(
                recipeRepository,
                recipeHopRepository,
                recipeMaltRepository,
                recipeExtraIngredientRepository,
                recipeMapper,
                recipeHopMapper,
                recipeMaltMapper,
                recipeExtraIngredientMapper
        );

        return new RecipeFacadeAdapter(recipeService, recipeIngredientService);
    }
}
