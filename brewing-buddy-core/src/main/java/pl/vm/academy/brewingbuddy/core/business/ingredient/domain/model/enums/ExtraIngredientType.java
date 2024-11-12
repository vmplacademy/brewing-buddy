package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExtraIngredientType {
    JUICE("Juice"),
    LACTOSE("Lactose"),
    SEASONING("Seasoning"),
    FRUIT("Fruit"),
    CARBONATION("Carbonation");

    private final String extraIngredientType;
}