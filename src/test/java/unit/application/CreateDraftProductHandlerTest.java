package unit.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.revestoria.product.application.command.createproduct.CreateDraftProductCommand;
import org.revestoria.product.application.command.createproduct.CreateDraftProductHandler;
import org.revestoria.product.application.dto.ProductResponse;
import org.revestoria.product.application.ports.ProductRepository;
import org.revestoria.product.domain.Product;
import org.revestoria.product.domain.ProductCondition;
import org.revestoria.product.domain.ProductFactory;
import org.revestoria.product.domain.ProductStatus;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateDraftProductHandlerTest {
    private ProductRepository productRepository;
    private CreateDraftProductHandler handler;

    @BeforeEach
    void setUp(){
        ProductFactory productFactory = new ProductFactory();
        productRepository = mock(ProductRepository.class);
        handler = new CreateDraftProductHandler(productRepository, productFactory);
    }

    private CreateDraftProductCommand createValidCommand(){
        UUID categoryId = UUID.randomUUID();
        UUID sellerId = UUID.randomUUID();

        return new CreateDraftProductCommand(
                "Vintage Hoodie",
                "Nice second-hand hoodie",
                BigDecimal.valueOf(35),
                "EUR",
                ProductCondition.GOOD,
                "Nike",
                "M",
                "Black",
                "Cotton",
                categoryId,
                sellerId
        );
    }

    @Test
    void handle_ShouldCreateDraftProduct_WhenCommandIsValid(){
        CreateDraftProductCommand command = createValidCommand();

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponse productResponse = handler.handle(command);

        assertAll(
                () -> assertNotNull(productResponse),
                () -> assertNotNull(productResponse.id()),
                () -> assertEquals(command.title(), productResponse.title()),
                () -> assertEquals(command.description(), productResponse.description()),
                () -> assertEquals(command.priceAmount(), productResponse.priceAmount()),
                () -> assertEquals(command.priceCurrency(), productResponse.priceCurrency()),
                () -> assertEquals(ProductStatus.DRAFT.name(), productResponse.status()),
                () -> assertEquals(command.productCondition().name(), productResponse.condition()),
                () -> assertEquals(command.brand(), productResponse.brand()),
                () -> assertEquals(command.size(), productResponse.size()),
                () -> assertEquals(command.color(), productResponse.color()),
                () -> assertEquals(command.material(), productResponse.material()),
                () -> assertEquals(command.categoryId(), productResponse.categoryId()),
                () -> assertEquals(command.sellerId(), productResponse.sellerId())
        );

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);

        verify(productRepository, times(1)).save(productCaptor.capture());

        Product savedProduct = productCaptor.getValue();

        assertAll(
                () -> assertEquals(command.title(), savedProduct.getTitle()),
                () -> assertEquals(command.description(), savedProduct.getDescription()),
                () -> assertEquals(command.priceAmount(), savedProduct.getPrice().getAmount()),
                () -> assertEquals(command.priceCurrency(), savedProduct.getPrice().getCurrency()),
                () -> assertEquals(ProductStatus.DRAFT, savedProduct.getProductStatus()),
                () -> assertEquals(ProductCondition.GOOD, savedProduct.getProductCondition()),
                () -> assertEquals(command.brand(), savedProduct.getBrand()),
                () -> assertEquals(command.size(), savedProduct.getSize()),
                () -> assertEquals(command.color(), savedProduct.getColor()),
                () -> assertEquals(command.material(), savedProduct.getMaterial()),
                () -> assertEquals(command.sellerId(), savedProduct.getSellerId()),
                () -> assertEquals(command.categoryId(), savedProduct.getCategoryId())
        );
    }

    @Test
    void handle_ShouldThrow_WhenRepositorySaveFails() {
        CreateDraftProductCommand command = createValidCommand();

        when(productRepository.save(any(Product.class)))
                .thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> handler.handle(command)
        );

        assertEquals("Database error", exception.getMessage());

        verify(productRepository, times(1)).save(any(Product.class));
        verifyNoMoreInteractions(productRepository);
    }
}
