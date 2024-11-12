package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.util.UUID;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Data
@MappedSuperclass
public abstract class Ingredient {
  @Id
  @GeneratedValue
  @UuidGenerator
  private UUID id;

  private String name;
}
