package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_hop")
public class Hop {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String name;
    private BigDecimal alfaAcidInPercentage;
}