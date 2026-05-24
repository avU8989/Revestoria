package integration.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import integration.shared.BaseFullContextTest;
import integration.shared.TestRESTOperations;
import integration.shared.TestRESTOperationsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.revestoria.product.application.dto.ProductResponse;
import org.revestoria.product.web.requests.CreateDraftProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ProductControllerTest extends BaseFullContextTest {
    private TestRESTOperations testRESTOperations;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach()
    void setUp(){
        testRESTOperations = new TestRESTOperationsImpl(mockMvc, objectMapper);
    }

    @Test
    void createProductDraft_ShouldReturnCreatedProduct_WhenRequestIsValid() {
        UUID categoryId = UUID.randomUUID();
        UUID sellerId = UUID.randomUUID();

        CreateDraftProductRequest request = new CreateDraftProductRequest(
                "Vintage Hoodie",
                "Nice second-hand hoodie",
                BigDecimal.valueOf(35),
                "EUR",
                "GOOD",
                "Nike",
                "M",
                "Black",
                "Cotton",
                categoryId,
                sellerId
        );

        ProductResponse response = testRESTOperations.doPost(
                "/api/products",
                request,
                ProductResponse.class
        );

        assertAll(
                () -> assertNotNull(response),
                () -> assertNotNull(response.id()),
                () ->assertEquals("Vintage Hoodie", response.title()),
                () ->assertEquals("Nice second-hand hoodie", response.description()),
                () -> assertEquals(BigDecimal.valueOf(35), response.priceAmount()),
                () ->assertEquals("EUR", response.priceCurrency()),
                () -> assertEquals("DRAFT", response.status()),
                () ->assertEquals("GOOD", response.condition()),
                () ->assertEquals("Nike", response.brand()),
                () -> assertEquals("M", response.size()),
                () -> assertEquals("Black", response.color()),
                () ->assertEquals("Cotton", response.material()),
                () ->assertEquals(categoryId, response.categoryId()),
                () -> assertEquals(sellerId, response.sellerId())
                );

    }



}
