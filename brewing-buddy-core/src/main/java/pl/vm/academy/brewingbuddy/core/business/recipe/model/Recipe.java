package pl.vm.academy.brewingbuddy.core.business.recipe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.enums.BeerStyle;
import pl.vm.academy.brewingbuddy.core.business.user.model.User;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_recipe")
public class Recipe {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "recipe")
    private Set<RecipeMalt> recipeMalts;

    @OneToMany(mappedBy = "recipe")
    private Set<RecipeHop> recipeHops;

    @OneToMany(mappedBy = "recipe")
    private Set<RecipeExtraIngredient> recipeExtraIngredients;

    @OneToMany(mappedBy = "recipe")
    private Set<RecipeYeast> recipeYeasts;

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String recipeName;
    @Enumerated(EnumType.STRING)
    private BeerStyle beerStyle;
    // required parameters
    private BigDecimal mashingEfficiencyInPercentage;
    private BigDecimal amountOfHotWort;
    private BigDecimal mashingFactorInLitersPerKg; //

    private BigDecimal waterRequiredForMashingInLiters;
    private BigDecimal waterRequiredForSpargingInLiters;
    private BigDecimal waterRequiredForWholeProcessInLiters;

    private BigDecimal amountOfWaterBeforeBoilingInLiters;
    private BigDecimal extractBeforeBoilingInPercentage;
    private BigDecimal expectedAmountOfBeerInLiters;
}