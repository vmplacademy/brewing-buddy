package pl.vm.academy.brewingbuddy.core.business.ingredient.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.ingredient.ExtraIngredientMapper;
import pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.ingredient.JuiceMapper;
import pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.ingredient.LactoseMapper;

@Configuration
public class IngredientConfiguration {
    @Bean
    IngredientFacadeAdapter ingredientFacadeAdapter (MaltRepository maltRepository,
                                                     HopRepository hopRepository,
                                                     ExtraIngredientRepository extraIngredientRepository,
                                                     YeastRepository yeastRepository) {

        MaltMapper maltMapper = new MaltMapper();
        HopMapper hopMapper = new HopMapper();
        YeastMapper yeastMapper = new YeastMapper();
        ExtraIngredientMapper extraIngredientMapper = new ExtraIngredientMapper();
        JuiceMapper juiceMapper = new JuiceMapper();
        LactoseMapper lactoseMapper = new LactoseMapper();
        IngredientCommonMapper ingredientCommonMapper = new IngredientCommonMapper(
                maltMapper,
                hopMapper,
                yeastMapper,
                extraIngredientMapper,
                juiceMapper,
                lactoseMapper
        );

        IngredientServiceAdapter ingredientServiceAdapter = new IngredientServiceAdapter(
                maltRepository,
                hopRepository,
                extraIngredientRepository,
                yeastRepository,
                ingredientCommonMapper);

        return new IngredientFacadeAdapter(ingredientServiceAdapter);

    }

}
