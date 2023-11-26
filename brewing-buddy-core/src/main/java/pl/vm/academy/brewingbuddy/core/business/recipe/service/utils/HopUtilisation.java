package pl.vm.academy.brewingbuddy.core.business.recipe.service.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;

@Component
public class HopUtilisation {

    private final HashMap<Integer, BigDecimal> utilisation;

    public HopUtilisation() {
        utilisation = new HashMap<Integer, BigDecimal>();
        utilisation.put(0, BigDecimal.valueOf(5.00));
        utilisation.put(1, BigDecimal.valueOf(5.11));
        utilisation.put(2, BigDecimal.valueOf(5.22));
        utilisation.put(3, BigDecimal.valueOf(5.33));
        utilisation.put(4, BigDecimal.valueOf(5.44));
        utilisation.put(5, BigDecimal.valueOf(5.56));
        utilisation.put(6, BigDecimal.valueOf(5.67));
        utilisation.put(7, BigDecimal.valueOf(5.78));
        utilisation.put(8, BigDecimal.valueOf(5.89));
        utilisation.put(9, BigDecimal.valueOf(6.00));
        utilisation.put(10, BigDecimal.valueOf(7.50));
        utilisation.put(11, BigDecimal.valueOf(9.00));
        utilisation.put(12, BigDecimal.valueOf(10.50));
        utilisation.put(13, BigDecimal.valueOf(12.00));
        utilisation.put(14, BigDecimal.valueOf(13.50));
        utilisation.put(15, BigDecimal.valueOf(15.00));
        utilisation.put(16, BigDecimal.valueOf(15.40));
        utilisation.put(17, BigDecimal.valueOf(15.80));
        utilisation.put(18, BigDecimal.valueOf(16.20));
        utilisation.put(19, BigDecimal.valueOf(16.60));
        utilisation.put(20, BigDecimal.valueOf(17.00));
        utilisation.put(21, BigDecimal.valueOf(17.40));
        utilisation.put(22, BigDecimal.valueOf(17.80));
        utilisation.put(23, BigDecimal.valueOf(18.20));
        utilisation.put(24, BigDecimal.valueOf(18.60));
        utilisation.put(25, BigDecimal.valueOf(19.00));
        utilisation.put(26, BigDecimal.valueOf(19.42));
        utilisation.put(27, BigDecimal.valueOf(19.83));
        utilisation.put(28, BigDecimal.valueOf(20.25));
        utilisation.put(29, BigDecimal.valueOf(20.67));
        utilisation.put(30, BigDecimal.valueOf(21.08));
        utilisation.put(31, BigDecimal.valueOf(21.50));
        utilisation.put(32, BigDecimal.valueOf(21.92));
        utilisation.put(33, BigDecimal.valueOf(22.33));
        utilisation.put(34, BigDecimal.valueOf(22.75));
        utilisation.put(35, BigDecimal.valueOf(23.17));
        utilisation.put(36, BigDecimal.valueOf(23.58));
        utilisation.put(37, BigDecimal.valueOf(24.00));
        utilisation.put(38, BigDecimal.valueOf(24.20));
        utilisation.put(39, BigDecimal.valueOf(24.40));
        utilisation.put(40, BigDecimal.valueOf(24.60));
        utilisation.put(41, BigDecimal.valueOf(24.80));
        utilisation.put(42, BigDecimal.valueOf(25.00));
        utilisation.put(43, BigDecimal.valueOf(25.20));
        utilisation.put(44, BigDecimal.valueOf(25.40));
        utilisation.put(45, BigDecimal.valueOf(25.60));
        utilisation.put(46, BigDecimal.valueOf(25.80));
        utilisation.put(47, BigDecimal.valueOf(26.00));
        utilisation.put(48, BigDecimal.valueOf(26.20));
        utilisation.put(49, BigDecimal.valueOf(26.40));
        utilisation.put(50, BigDecimal.valueOf(26.60));
        utilisation.put(51, BigDecimal.valueOf(26.80));
        utilisation.put(52, BigDecimal.valueOf(27.00));
        utilisation.put(53, BigDecimal.valueOf(27.38));
        utilisation.put(54, BigDecimal.valueOf(27.75));
        utilisation.put(55, BigDecimal.valueOf(28.13));
        utilisation.put(56, BigDecimal.valueOf(28.50));
        utilisation.put(57, BigDecimal.valueOf(28.88));
        utilisation.put(58, BigDecimal.valueOf(29.25));
        utilisation.put(59, BigDecimal.valueOf(29.63));
        utilisation.put(60, BigDecimal.valueOf(30.00));
        utilisation.put(61, BigDecimal.valueOf(30.27));
        utilisation.put(62, BigDecimal.valueOf(30.53));
        utilisation.put(63, BigDecimal.valueOf(30.80));
        utilisation.put(64, BigDecimal.valueOf(31.07));
        utilisation.put(65, BigDecimal.valueOf(31.33));
        utilisation.put(66, BigDecimal.valueOf(31.60));
        utilisation.put(67, BigDecimal.valueOf(31.87));
        utilisation.put(68, BigDecimal.valueOf(32.13));
        utilisation.put(69, BigDecimal.valueOf(32.40));
        utilisation.put(70, BigDecimal.valueOf(32.67));
        utilisation.put(71, BigDecimal.valueOf(32.93));
        utilisation.put(72, BigDecimal.valueOf(33.20));
        utilisation.put(73, BigDecimal.valueOf(33.47));
        utilisation.put(74, BigDecimal.valueOf(33.73));
        utilisation.put(75, BigDecimal.valueOf(34.00));
    }

    public BigDecimal getHopUtilisation (int minutes) {
        if (minutes < 0) {
            return BigDecimal.ZERO;
        } else if (minutes > 75) {
            return BigDecimal.valueOf(34.00);
        }
        return utilisation.get(minutes);
    }

}
