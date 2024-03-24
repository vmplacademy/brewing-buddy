package pl.vm.academy.brewingbuddy.core.business.ingredient.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.ExtraIngredientMapper;
import pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.HopMapper;
import pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.IngredientCommonMapper;
import pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.MaltMapper;
import pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.YeastMapper;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.repository.ExtraIngredientRepository;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.repository.HopRepository;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.repository.MaltRepository;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.repository.YeastRepository;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.service.IngredientFacadeAdapter;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.service.IngredientServiceAdapter;

@Configuration
public class IngredientConfiguration {
    @Bean
    IngredientFacadeAdapter ingredientFacadeAdapter (MaltRepository maltRepository,
                                                     HopRepository hopRepository,
                                                     ExtraIngredientRepository extraIngredientRepository,
                                                     YeastRepository yeastRepository) {

        MaltMapper maltMapper = new MaltMapper();
        HopMapper hopMapper = new HopMapper();
        ExtraIngredientMapper extraIngredientMapper = new ExtraIngredientMapper();
        YeastMapper yeastMapper = new YeastMapper();
        IngredientCommonMapper ingredientCommonMapper = new IngredientCommonMapper(
                maltMapper,
                hopMapper,
                extraIngredientMapper,
                yeastMapper);

        IngredientServiceAdapter ingredientServiceAdapter = new IngredientServiceAdapter(
                maltRepository,
                hopRepository,
                extraIngredientRepository,
                yeastRepository,
                ingredientCommonMapper);

        return new IngredientFacadeAdapter(ingredientServiceAdapter);

    }

}
