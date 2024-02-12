package pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MeasureUnit {
    ML ("milliliters"),
    L ("liters"),
    G ("grams");

    private final String measureUnit;
}
