package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum YeastType {
    DRY("Dry"),
    LIQUID("Liquid");

    private final String yeastType;
}