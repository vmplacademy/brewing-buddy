package pl.vm.academy.brewingbuddy.core.business.recipe.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UnitMeasure {
    ML ("milliliters"),
    L ("liters"),
    G ("grams");

    private final String measureUnit;
}
