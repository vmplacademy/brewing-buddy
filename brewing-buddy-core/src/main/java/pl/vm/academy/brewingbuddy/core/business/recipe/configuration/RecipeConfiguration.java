package pl.vm.academy.brewingbuddy.core.business.recipe.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.RecipeFacadeAdapter;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.RecipeIngredientServiceAdapter;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.RecipeServiceAdapter;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeCalculatedParametersMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeCommonMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeExtraIngredientMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeHopMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMaltMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.mapper.RecipeYeastMapper;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.repository.RecipeCalculatedParametersRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.repository.RecipeRepository;

@Configuration
class RecipeConfiguration {
    @Bean
    RecipeFacadeAdapter recipeFacade(RecipeRepository recipeRepository,
                                     RecipeCalculatedParametersRepository recipeCalculatedParametersRepository
                                     ) {

        RecipeCalculatedParametersMapper recipeCalculatedParametersMapper = new RecipeCalculatedParametersMapper();
        RecipeHopMapper recipeHopMapper = new RecipeHopMapper();
        RecipeMaltMapper recipeMaltMapper = new RecipeMaltMapper();
        RecipeExtraIngredientMapper recipeExtraIngredientMapper = new RecipeExtraIngredientMapper();
        RecipeYeastMapper recipeYeastMapper = new RecipeYeastMapper();
        RecipeMapper recipeMapper = new RecipeMapper(recipeCalculatedParametersMapper, recipeHopMapper, recipeMaltMapper,
                recipeExtraIngredientMapper, recipeYeastMapper);
        RecipeCommonMapper recipeCommonMapper = new RecipeCommonMapper(recipeMapper, recipeHopMapper, recipeMaltMapper,
                recipeExtraIngredientMapper, recipeYeastMapper, recipeCalculatedParametersMapper);

        RecipeServiceAdapter recipeService = new RecipeServiceAdapter(
                recipeRepository,
                recipeCalculatedParametersRepository,
                recipeMapper,
                recipeCalculatedParametersMapper);

        RecipeIngredientServiceAdapter recipeIngredientService = new RecipeIngredientServiceAdapter(
                recipeRepository,
                recipeCommonMapper
        );

        return new RecipeFacadeAdapter(recipeService, recipeIngredientService);
    }
}
