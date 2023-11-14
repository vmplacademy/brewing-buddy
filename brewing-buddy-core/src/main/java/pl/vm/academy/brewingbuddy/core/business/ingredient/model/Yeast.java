package pl.vm.academy.brewingbuddy.core.business.ingredient.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import pl.vm.academy.brewingbuddy.core.business.ingredient.model.enums.YeastType;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_yeast")
public class Yeast {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String name;
    private String description;
    @Enumerated
    private YeastType yeastType;
}
