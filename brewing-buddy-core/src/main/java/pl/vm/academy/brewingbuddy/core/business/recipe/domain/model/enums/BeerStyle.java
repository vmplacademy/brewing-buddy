package pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BeerStyle {
    LAGGER("Lagger"),
    PILS("Pils"),
    IPA("India Pale Ale"),
    STOUT("Stout");

    private final String beerStyle;
}

