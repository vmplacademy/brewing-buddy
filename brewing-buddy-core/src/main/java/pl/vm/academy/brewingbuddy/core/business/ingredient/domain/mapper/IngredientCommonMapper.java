package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.mapper;

import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.ExtraIngredient;

public record IngredientCommonMapper(MaltMapper maltMapper,
                                     HopMapper hopMapper,
                                     ExtraIngredientMapper extraIngredientMapper,
                                     YeastMapper yeastMapper) {
}
