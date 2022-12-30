package pl.vm.academy.brewingbuddy.core.hop.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import pl.vm.academy.brewingbuddy.core.recipe.model.entity.Recipe;

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
