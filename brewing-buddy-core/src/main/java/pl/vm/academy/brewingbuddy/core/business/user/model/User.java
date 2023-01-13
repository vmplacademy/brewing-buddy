package pl.vm.academy.brewingbuddy.core.business.user.model;

import jakarta.persistence.*;
import lombok.Data;
import pl.vm.academy.brewingbuddy.core.business.recipe.model.Recipe;

import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @OneToMany(mappedBy = "user")
    private Set<Recipe> recipes;

}
