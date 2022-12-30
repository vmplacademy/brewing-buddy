package pl.vm.academy.brewingbuddy.core.malt.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import pl.vm.academy.brewingbuddy.core.recipe.model.entity.Recipe;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "recipe_malt")
public class RecipeMalt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount; // ilość słodu w przepisie w [kg]
    private BigDecimal teoreticalExtractAmount; // Teoretyczny ekstraktu w g wg zasypu
    private BigDecimal realExtractAmount; // Ekstrakt rzeczywisty w g wg zasypu

    @ManyToOne
    @JoinColumn(name = "MALT_ID")
    private Malt malt;

    @ManyToOne
    @JoinColumn(name = "RECIPE_ID")
    private Recipe recipe;

}