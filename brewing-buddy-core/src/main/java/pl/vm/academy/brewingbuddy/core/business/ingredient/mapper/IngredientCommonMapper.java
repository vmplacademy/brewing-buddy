package pl.vm.academy.brewingbuddy.core.business.ingredient.mapper;

public record IngredientCommonMapper(MaltMapper maltMapper,
                                     HopMapper hopMapper,
                                     ExtraIngredientMapper extraIngredientMapper,
                                     YeastMapper yeastMapper) {
}
