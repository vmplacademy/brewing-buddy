package pl.vm.academy.brewingbuddy.core.business.recipe.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @NotBlank
    private String recipeName;
    private BeerStyle beerStyle;
    @DecimalMin("1")
    @DecimalMax("1000")
    private BigDecimal expectedAmountOfBeerInLiters;
    @DecimalMin("10")
    @DecimalMax("100")
    private BigDecimal boilingProcessTime;
    @DecimalMin("1")
    @DecimalMax("99")
    private BigDecimal waterEvaporationInPercentagePerHour;
    @DecimalMin("1")
    @DecimalMax("99")
    private BigDecimal boilingProcessLossInPercentage;
    @DecimalMin("1")
    @DecimalMax("99")
    private BigDecimal fermentationProcessLossInPercentage;
    private CalculatedParametersDto calculatedParametersDto;

}
