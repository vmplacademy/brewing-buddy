package pl.vm.academy.brewingbuddy.core;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.vm.academy.brewingbuddy.core.business.recipe.controller.RecipeController;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private RecipeController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
}
