package pl.vm.academy.brewingbuddy.core.business.recipe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.enums.BeerStyle;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class RecipeDto {
    private String id;
    private Boolean isPublic;
    private String recipeName;
    private BeerStyle beerStyle;
    private BigDecimal expectedAmountOfBeerInLiters;
    private BigDecimal boilingProcessTime;
    private BigDecimal waterEvaporationInPercentagePerHour;
    private BigDecimal boilingProcessLossInPercentage;
    private BigDecimal fermentationProcessLossInPercentage;
    private CalculatedParametersDto calculatedParametersDto;

}
