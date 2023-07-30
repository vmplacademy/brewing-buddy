package pl.vm.academy.brewingbuddy.core.business.recipe.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeCalculatedParametersMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeExtraIngredientMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeHopMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMaltMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeYeastMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeCalculatedParametersRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeExtraIngredientRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeHopRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeMaltRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.repository.RecipeYeastRepository;

@Configuration
class RecipeConfiguration {

    @Bean
    RecipeFacadeAdapter recipeFacade(RecipeRepository recipeRepository,
                                     RecipeCalculatedParametersRepository recipeCalculatedParametersRepository,
                                     RecipeHopRepository recipeHopRepository,
                                     RecipeMaltRepository recipeMaltRepository,
                                     RecipeExtraIngredientRepository recipeExtraIngredientRepository,
                                     RecipeYeastRepository recipeYeastRepository) {

        RecipeCalculatedParametersMapper recipeCalculatedParametersMapper = new RecipeCalculatedParametersMapper();
        RecipeHopMapper recipeHopMapper = new RecipeHopMapper();
        RecipeMaltMapper recipeMaltMapper = new RecipeMaltMapper();
        RecipeExtraIngredientMapper recipeExtraIngredientMapper = new RecipeExtraIngredientMapper();
        RecipeYeastMapper recipeYeastMapper = new RecipeYeastMapper();
        RecipeMapper recipeMapper = new RecipeMapper(recipeCalculatedParametersMapper, recipeHopMapper, recipeMaltMapper,
                recipeExtraIngredientMapper, recipeYeastMapper, recipeRepository);

        RecipeServiceAdapter recipeService = new RecipeServiceAdapter(
                recipeRepository,
                recipeCalculatedParametersRepository,
                recipeMapper,
                recipeCalculatedParametersMapper);

        RecipeIngredientServiceAdapter recipeIngredientService = new RecipeIngredientServiceAdapter(
                recipeRepository,
                recipeHopRepository,
                recipeMaltRepository,
                recipeExtraIngredientRepository,
                recipeYeastRepository,
                recipeMapper,
                recipeHopMapper,
                recipeMaltMapper,
                recipeExtraIngredientMapper,
                recipeYeastMapper
        );

        return new RecipeFacadeAdapter(recipeService, recipeIngredientService);
    }
}
