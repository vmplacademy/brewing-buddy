package pl.vm.academy.brewingbuddy.core.business.ingredient.mapper;

import pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.ingredient.ExtraIngredientMapper;
import pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.ingredient.JuiceMapper;
import pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.ingredient.LactoseMapper;

public record IngredientCommonMapper(MaltMapper maltMapper,
                                     HopMapper hopMapper,
                                     YeastMapper yeastMapper,
                                     ExtraIngredientMapper extraIngredientMapper,
                                     JuiceMapper juiceMapper,
                                     LactoseMapper lactoseMapper) {
}
