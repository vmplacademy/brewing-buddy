package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.RecipeMalt;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
public class Malt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal extractionRate; // ekstrakcja wyrażona w %
    private BigDecimal ebcColor; // Uśredniona barwa składnika w EBC

    @OneToMany(mappedBy = "malt")
    private Set<RecipeMalt> recipeMalts;

}
