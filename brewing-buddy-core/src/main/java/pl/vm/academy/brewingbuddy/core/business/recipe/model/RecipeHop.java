package pl.vm.academy.brewingbuddy.core.business.recipe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.lang.NonNull;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.Hop;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "recipe_hop")
public class RecipeHop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount; // Chmiel w g
    private BigDecimal boilingTime; // Czas gotowania w min
    private BigDecimal utilization; // Utylizacja chmielu w %

    @ManyToOne
    @JoinColumn(name = "HOP_ID")
    private Hop hop;

    @ManyToOne
    @JoinColumn(name = "RECIPE_ID")
    private Recipe recipe;

}
