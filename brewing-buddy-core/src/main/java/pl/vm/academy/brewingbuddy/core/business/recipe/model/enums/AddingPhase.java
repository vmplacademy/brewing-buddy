package pl.vm.academy.brewingbuddy.core.business.recipe.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AddingPhase {
    MASHING("Mashing phase"),
    BOILING("Boiling phase"),
    FERMENTATION("Fermentation");

    private final String addingPhase;
}