package pl.vm.academy.brewingbuddy.core.business.recipe.domain.service.calculator;

import java.math.BigDecimal;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CalculatorConstants {
  public static final BigDecimal EXTRACT_GRAMS_MLITERS_RATIO = BigDecimal.valueOf(1.587);
  public static final BigDecimal EBC_COLOUR_CONSTANT = BigDecimal.valueOf(2.20462262184878);
  public static final BigDecimal EBC_TO_SRM_RATIO = BigDecimal.valueOf(1.97);
  public static final BigDecimal MCU_CONSTANT = BigDecimal.valueOf(1.4922);
  public static final double MCU_POW_CONSTANT = 0.6859;
  public static final BigDecimal FERMENTATION_FACTOR = BigDecimal.valueOf(0.8);
  public static final BigDecimal REFERMENTATION_FACTOR = BigDecimal.valueOf(0.4);
  public static final BigDecimal BLG_ABV_FACTOR = BigDecimal.valueOf(1.938);
  public static final BigDecimal GALLON_LITER_FACTOR = BigDecimal.valueOf(3.78541178);
  public static final BigDecimal WATER_PER_KG_AFTER_FILTRATION = BigDecimal.valueOf(0.7);
}
