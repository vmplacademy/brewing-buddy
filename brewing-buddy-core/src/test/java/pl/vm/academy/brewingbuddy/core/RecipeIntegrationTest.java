package pl.vm.academy.brewingbuddy.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.vm.academy.brewingbuddy.core.business.recipe.domain.model.enums.BeerStyle;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeBasicDto;
import pl.vm.academy.brewingbuddy.core.business.recipe.dto.RecipeSimpleDto;

class RecipeIntegrationTest extends IntegrationTest {

  @LocalServerPort
  private int serverPort;

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  //@Sql(value = "/test_data.sql" , executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
  //@Sql(value = "/clear_recipes.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
  void should_return_all_recipes() {
    // given
    setupData();
    final String REQUEST_ADDRESS_RECIPES = "http://localhost:" + serverPort + "/recipes";
    System.out.println("Active app port:");
    System.out.println(serverPort);

    //when
    ResponseEntity<List<RecipeBasicDto>> recipeResponse =
        testRestTemplate.exchange(
            REQUEST_ADDRESS_RECIPES,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<RecipeBasicDto>>() {}
        );

    List<RecipeBasicDto> recipesDto = recipeResponse.getBody();
    System.out.println(recipesDto);

    // then
    assertThat(recipeResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(recipesDto).hasSize(2);
  }

  @Test
  void should_return_empty_set_of_recipes() {
    // given
    final String REQUEST_ADDRESS_RECIPES = "http://localhost:" + serverPort + "/recipes";

    ResponseEntity<List<RecipeBasicDto>> recipeResponse =
        testRestTemplate.exchange(
            REQUEST_ADDRESS_RECIPES,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<RecipeBasicDto>>() {}
        );
    List<RecipeBasicDto> recipesDto = recipeResponse.getBody();
    System.out.println(recipesDto);
    assertThat(recipeResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(recipesDto).isEmpty();
  }

  @Test
  //@Sql(value = "/test_data.sql" , executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
  //@Sql(value = "/clear_recipes.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
  void should_create_valid_recipe() {
    //given
    final String REQUEST_ADDRESS_RECIPES = "http://localhost:" + serverPort + "/recipes";
    RecipeSimpleDto recipeSimpleDto = RecipeSimpleDto
        .builder()
        .recipeName("Test Recipe")
        .beerStyle(BeerStyle.LAGGER)
        .isPublic(true)
        .boilingProcessTime(Duration.ofMinutes(60))
        .build();

    HttpEntity<RecipeSimpleDto> requestEntity = new HttpEntity<>(recipeSimpleDto);

    //when
    ResponseEntity<RecipeSimpleDto> responseEntity = testRestTemplate.exchange(
        REQUEST_ADDRESS_RECIPES,
        HttpMethod.POST,
        requestEntity,
        new ParameterizedTypeReference<RecipeSimpleDto>() {}
    );

    //then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody().recipeName()).isEqualTo("Test Recipe");
  }

  @Test
  //@Sql(value = "/test_data.sql" , executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
  //@Sql(value = "/clear_recipes.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
  void should_throw_an_error_while_creating_recipe_with_existing_name() {
    //given
    setupData();
    final String REQUEST_ADDRESS_RECIPES = "http://localhost:" + serverPort + "/recipes";
    RecipeSimpleDto recipeSimpleDto = RecipeSimpleDto
        .builder()
        .recipeName("Test Recipe 1") // already exists
        .beerStyle(BeerStyle.LAGGER)
        .isPublic(true)
        .boilingProcessTime(Duration.ofMinutes(60))
        .build();

    HttpEntity<RecipeSimpleDto> requestEntity = new HttpEntity<>(recipeSimpleDto);

    //when
    ResponseEntity<String> responseEntity = testRestTemplate.exchange(
        REQUEST_ADDRESS_RECIPES,
        HttpMethod.POST,
        requestEntity,
        String.class
    );

    System.out.println(responseEntity.getBody().toString());

    //then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT); // already exists
    assertThat(responseEntity.getBody()).isEqualTo("Recipe with such name already exists!"); // already exists
  }
  @Test
  //@Sql(value = "/test_data.sql" , executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
  //@Sql(value = "/clear_recipes.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
  void should_throw_an_error_while_creating_recipe_with_invalid_data() {
    //given
    setupData();
    final String REQUEST_ADDRESS_RECIPES = "http://localhost:" + serverPort + "/recipes";
    RecipeSimpleDto recipeSimpleDto = RecipeSimpleDto
        .builder()
        .recipeName("Test Recipe 3")
        .beerStyle(BeerStyle.LAGGER)
        .isPublic(true)
        .boilingProcessTime(Duration.ofMinutes(15000)) // Max Duration 1000
        .build();

    HttpEntity<RecipeSimpleDto> requestEntity = new HttpEntity<>(recipeSimpleDto);

    //when
    ResponseEntity<String> responseEntity = testRestTemplate.exchange(REQUEST_ADDRESS_RECIPES, HttpMethod.POST, requestEntity, String.class);

    //then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(responseEntity.getBody()).contains("Validation failed for argument");
  }

  private void setupData () {
    jdbcTemplate.update("INSERT INTO t_recipe (ID, RECIPE_NAME, IS_PUBLIC) VALUES ('1e28dd04-c26b-4e2e-800d-55d7a7746374', 'Test Recipe 1', TRUE),\n"
        + "                                                         ('1e28dd04-c26b-4e2e-800d-55d7a7746222', 'Test Recipe 2', TRUE)");
  }
}

