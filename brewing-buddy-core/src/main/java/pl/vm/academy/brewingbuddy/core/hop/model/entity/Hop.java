package pl.vm.academy.brewingbuddy.core.hop.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
public class Hop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private BigDecimal alfaAcid; // α-kwasy w %

    @OneToMany(mappedBy = "hop")
    private Set<RecipeHop> recipeHops;
}
