package pl.vm.academy.brewingbuddy.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.repository.ExtraIngredientRepository;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.ingredient.ExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.ingredient.JuiceDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.ingredient.LactoseDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeBasicDto;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class IngredientIntegrationTest extends IntegrationTest{

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  ExtraIngredientRepository extraIngredientRepository;

  @Test
  @Sql(value = "/insert_extra_ingredients.sql" , executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(value = "/clear_extra_ingredients.sql" , executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
  void should_return_all_juices() throws Exception {
    // given
    // when
    MvcResult result = mockMvc.perform(get("/ingredients/extraIngredients/juices"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    String responseBody = result.getResponse().getContentAsString();

    ObjectMapper mapper = new ObjectMapper();
    Set<JuiceDto> juiceDtos = mapper.readValue(responseBody, Set.class);

    // then
    assertThat(result.getResponse().getStatus()).isEqualTo(200);
    assertThat(juiceDtos).hasSize(1);
  }

  @Test
  @Sql(value = "/insert_extra_ingredients.sql" , executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(value = "/clear_extra_ingredients.sql" , executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
  void should_return_all_lactose() throws Exception {
    // given
    // when
    MvcResult result = mockMvc.perform(get("/ingredients/extraIngredients/lactose"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    String responseBody = result.getResponse().getContentAsString();

    ObjectMapper mapper = new ObjectMapper();
    Set<LactoseDto> lactoseDtos = mapper.readValue(responseBody, Set.class);

    // then
    assertThat(result.getResponse().getStatus()).isEqualTo(200);
    assertThat(lactoseDtos).hasSize(1);
  }

  @Test
  @Sql(value = "/insert_extra_ingredients.sql" , executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(value = "/clear_extra_ingredients.sql" , executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
  void should_return_all_extra_ingredients() throws Exception {
    // given
    // when
    MvcResult result = mockMvc.perform(get("/ingredients/extraIngredients"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    String responseBody = result.getResponse().getContentAsString();

    ObjectMapper mapper = new ObjectMapper();
    Set<ExtraIngredientDto> extraIngredientDtos = mapper.readValue(responseBody, Set.class);

    // then
    assertThat(result.getResponse().getStatus()).isEqualTo(200);
    assertThat(extraIngredientDtos).hasSize(2);
  }

}
