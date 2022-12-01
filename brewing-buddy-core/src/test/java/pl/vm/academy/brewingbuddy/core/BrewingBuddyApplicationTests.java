package pl.vm.academy.brewingbuddy.core;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BrewingBuddyApplicationTests {

    @Test
    void contextLoads() {
        // given
        String vmAcademy = "VM Academy ";

        // when
        vmAcademy += "is the best!";

        // then
        assertThat(vmAcademy).isEqualTo("VM Academy is the best!");
    }
}
