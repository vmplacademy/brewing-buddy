package pl.vm.academy.brewingbuddy.core;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.enums.BeerStyle;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.repository.RecipeCalculatedParametersRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.repository.RecipeRepository;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeBasicDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeDetailedDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeSimpleDto;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class RecipeIntegrationTestWIthMockMVC extends IntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  RecipeRepository recipeRepository;

  @Autowired
  RecipeCalculatedParametersRepository recipeCalculatedParametersRepository;

  @Test
  @Sql(value = "/test_data.sql" , executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(value = "/clear_recipes.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
  public void should_return_all_recipes() throws Exception {
    // given
    // when
    MvcResult result = mockMvc.perform(get("/recipes"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    String responseBody = result.getResponse().getContentAsString();

    ObjectMapper mapper = new ObjectMapper();
    List<RecipeBasicDto> recipeBasicDtos = mapper.readValue(responseBody, List.class);

    // then
    assertThat(result.getResponse().getStatus()).isEqualTo(200);
    assertThat(recipeBasicDtos.size()).isEqualTo(2);
  }

  @Test
  void should_create_valid_recipe() throws Exception {
    //given
    RecipeSimpleDto recipeSimpleDto = RecipeSimpleDto
        .builder()
        .recipeName("Test Recipe 1")
        .beerStyle(BeerStyle.LAGGER)
        .isPublic(true)
        //.boilingProcessTime(Duration.ofMinutes(60))
        .build();

    //when
    ObjectMapper objectMapper = new ObjectMapper();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders
            .post("/recipes")
            .content(objectMapper.writeValueAsString(recipeSimpleDto))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    ObjectMapper mapper = new ObjectMapper();
    RecipeDetailedDto recipeDetailedDto = mapper.readValue(result.getResponse().getContentAsString(), RecipeDetailedDto.class);

    //then
    assertThat(result.getResponse().getStatus()).isEqualTo(200);
    assertThat(recipeDetailedDto.recipeName()).isEqualTo("Test Recipe 1");

    recipeCalculatedParametersRepository.deleteAll();
    recipeRepository.deleteAll();
  }

  @Test
  @Sql(value = "/test_data.sql" , executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(value = "/clear_recipes.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
  void should_throw_an_error_while_creating_recipe_with_existing_name() throws Exception {
    //given
    RecipeSimpleDto recipeSimpleDto = RecipeSimpleDto
        .builder()
        .recipeName("Test Recipe 1") // already exists
        .beerStyle(BeerStyle.LAGGER)
        .isPublic(true)
        .build();

    //when
    ObjectMapper objectMapper = new ObjectMapper();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders
            .post("/recipes")
            .content(objectMapper.writeValueAsString(recipeSimpleDto))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andReturn();

    System.out.println(result.getResponse().getErrorMessage());

    //then
    assertThat(result.getResponse().getStatus()).isEqualTo(409);
    assertThat(result.getResponse().getContentAsString()).contains("Recipe with such name already exists!");
  }

  @Test
  @Sql(value = "/test_data.sql" , executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
  @Sql(value = "/clear_recipes.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
  void should_throw_an_error_while_creating_recipe_with_invalid_data() throws Exception {
    //given
    RecipeSimpleDto recipeSimpleDto = RecipeSimpleDto
        .builder()
        .recipeName("Test Recipe 3")
        .beerStyle(BeerStyle.LAGGER)
        .isPublic(true)
        .expectedAmountOfBeerInLiters(BigDecimal.valueOf(1500)) // Max value 1000
        .build();

    //when
    ObjectMapper objectMapper = new ObjectMapper();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders
            .post("/recipes")
            .content(objectMapper.writeValueAsString(recipeSimpleDto))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andReturn();

    //then
    assertThat(result.getResponse().getStatus()).isEqualTo(400);
    assertThat(result.getResponse().getContentAsString()).contains("Validation failed for argument");
  }
}
