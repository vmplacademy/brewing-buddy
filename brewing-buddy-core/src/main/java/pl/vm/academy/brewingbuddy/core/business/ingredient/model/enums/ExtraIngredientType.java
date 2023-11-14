package pl.vm.academy.brewingbuddy.core.business.ingredient.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExtraIngredientType {
    SEASONING("Seasoning"),
    FRUIT("Fruit"),
    CARBONATION("Carbonation");

    private final String extraIngredientType;
}