package integration.shared;

import org.junit.jupiter.api.TestInstance;
import org.revestoria.RevestoriaSpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(
        classes = RevestoriaSpringApplication.class,
        webEnvironment = MOCK
)
@AutoConfigureMockMvc
@DirtiesContext(classMode = BEFORE_CLASS)
@ActiveProfiles("test")
public class BaseFullContextTest {
}
