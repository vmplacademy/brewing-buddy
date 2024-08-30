package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "t_hop")
public class Hop extends Ingredient{
    private BigDecimal alfaAcidInPercentage;
}