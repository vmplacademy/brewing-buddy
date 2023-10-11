package pl.vm.academy.brewingbuddy.core.business.recipe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.enums.BeerStyle;

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

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private UUID userId;

    @OneToMany(mappedBy = "recipe", orphanRemoval = true)
    private Set<RecipeMalt> recipeMalts;

    public void addRecipeMalt (RecipeMalt recipeMalt) {
        recipeMalts.add(recipeMalt);
        recipeMalt.setRecipe(this);
    }
    public void removeRecipeMalt (RecipeMalt recipeMalt) {
        recipeMalts.remove(recipeMalt);
        recipeMalt.setRecipe(null);
    }

    @OneToMany(mappedBy = "recipe", orphanRemoval = true)
    private Set<RecipeHop> recipeHops;

    public void addRecipeHop (RecipeHop recipeHop) {
        recipeHops.add(recipeHop);
        recipeHop.setRecipe(this);
    }
    public void deleteRecipeHop (RecipeHop recipeHop) {
        recipeHops.remove(recipeHop);
        recipeHop.setRecipe(null);
    }

    @OneToMany(mappedBy = "recipe", orphanRemoval = true)
    private Set<RecipeExtraIngredient> recipeExtraIngredients;

    public void addRecipeExtraIngredient (RecipeExtraIngredient recipeExtraIngredient) {
        recipeExtraIngredients.add(recipeExtraIngredient);
        recipeExtraIngredient.setRecipe(this);
    }
    public void deleteRecipeExtraIngredient (RecipeExtraIngredient recipeExtraIngredient) {
        recipeExtraIngredients.remove(recipeExtraIngredient);
        recipeExtraIngredient.setRecipe(null);
    }

    @OneToOne(mappedBy = "recipe", orphanRemoval = true)
    private RecipeYeast recipeYeast;

    public void setRecipeYeast (RecipeYeast recipeYeastInput) {
        recipeYeast = recipeYeastInput;
        recipeYeast.setRecipe(this);
    }

    @OneToOne
    private RecipeCalculatedParameter recipeCalculatedParameter;

    private boolean isPublic;

    private String recipeName;
    @Enumerated(EnumType.STRING)
    private BeerStyle beerStyle;
    // required parameters
    private BigDecimal expectedAmountOfBeerInLiters;
    private BigDecimal boilingProcessTime;
    private BigDecimal waterEvaporationInPercentagePerHour;
    private BigDecimal boilingProcessLossInPercentage;
    private BigDecimal fermentationProcessLossInPercentage;
    private BigDecimal mashingFactorInLitersPerKg;

}