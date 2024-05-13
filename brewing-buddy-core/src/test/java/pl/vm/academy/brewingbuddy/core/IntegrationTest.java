package pl.vm.academy.brewingbuddy.core;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Transactional
@ActiveProfiles("integration-test")
public class IntegrationTest {

}
