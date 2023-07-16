package pl.vm.academy.brewingbuddy.core.business.recipe.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UnitMeasure {
    ML ("milliliters"),
    G ("grams");

    private final String unitMeasure;
}
