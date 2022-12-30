package pl.vm.academy.brewingbuddy.core.recipe.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import pl.vm.academy.brewingbuddy.core.extraIngredient.repository.entity.RecipeExtraIngredient;
import pl.vm.academy.brewingbuddy.core.hop.model.entity.RecipeHop;
import pl.vm.academy.brewingbuddy.core.malt.model.entity.RecipeMalt;
import pl.vm.academy.brewingbuddy.core.user.model.entity.User;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recipeName;
    private String beerStyle; // style that can be chosen from the list
    // required parameters
    private BigDecimal mashingEfficiency; // wydajność zacierania podana w %
    private BigDecimal amountOfHotWort; // ilość gorącej brzeczki jaką chcesz uzyskać w [l]
    private BigDecimal mashingFactor; // współczynnik zacierania - ile litrów wody na 1 kg słodu

    // calculated parameters [water] - to be displayed
    private BigDecimal waterRequiredForMashing; // woda użyta do zacierania w [l]
    private BigDecimal waterRequiredForSparging; // woda potrzebna do wysładzania w [l]
    private BigDecimal waterRequiredForWholeProcess; // ilość potrzebnej wody docałego procesu w [l]

    // calculated parameters - to be displayed
    private BigDecimal amountOfWaterBeforeBoiling; // Tyle brzeczki będzie gotowane z chmielem w [l]
    private BigDecimal extractBeforeBoiling; // Orientacyjny % ekstraktu brzeczki przed gotowaniem
    private BigDecimal expectedAmountOfBeer; // Orientacyjna ilość gotowego piwa

    private String yeast;
    private BigDecimal amountOfYeast;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<RecipeMalt> recipeMalts;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<RecipeHop> recipeHops;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<RecipeExtraIngredient> recipeExtraIngredients;

}

