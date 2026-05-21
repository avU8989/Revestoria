package domain;

import org.junit.jupiter.api.Test;
import org.revestoria.core.shared.domain.Money;
import org.revestoria.product.domain.Product;
import org.revestoria.product.domain.ProductCondition;
import org.revestoria.product.domain.ProductImage;
import org.revestoria.product.domain.ProductStatus;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/*
domain.ProductTest

Creation:
- should create product with valid required fields x
- should throw if id is null x
- should throw if title is null/blank x
- should throw if price is null x
- should throw if status is null x
- should throw if condition is null x

Publish:
- should publish draft product x
- should throw when publishing non-draft product x

Reserve:
- should reserve available product x
- should throw when reserving non-available product x
- should throw when userId is null x
- should throw when reservedUntil is in the past x

Release reservation:
- should release reserved product x
- should throw when product is not reserved x

Sold:
- should mark reserved product as sold x
- should throw when product is not reserved x

Archive:
- should archive draft/available product x
- should throw when product is sold x
- should throw when product is reserved x

Update:
- should update draft/available product x
- should throw when updating reserved product x
- should throw when updating sold product x

Images:
- should add image
- should throw when image is null
 */
public class ProductTest {

    private Product createValidProduct(ProductStatus status) {
        return new Product(
                UUID.randomUUID(),
                "Vintage Hoodie",
                "Nice second-hand hoodie",
                new Money(BigDecimal.valueOf(35), "EUR"),
                status,
                ProductCondition.GOOD,
                "Nike",
                "M",
                "Black",
                "Cotton",
                UUID.randomUUID(),
                UUID.randomUUID(),
                null,
                null,
                null
        );
    }

    @Test
    void createProduct_ShouldCreateProduct_WhenRequiredFieldsAreValid() {
        UUID id = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        UUID sellerId = UUID.randomUUID();
        Money price = new Money(BigDecimal.valueOf(35), "EUR");

        Product product = new Product(
                id,
                "Vintage Hoodie",
                "Nice second-hand hoodie",
                price,
                ProductStatus.DRAFT,
                ProductCondition.GOOD,
                "Nike",
                "M",
                "Black",
                "Cotton",
                categoryId,
                sellerId,
                null,
                null,
                null
        );

        assertAll(
                () -> assertEquals(id, product.getId()),
                () -> assertEquals("Vintage Hoodie", product.getTitle()),
                () -> assertEquals("Nice second-hand hoodie", product.getDescription()),
                () -> assertEquals(price, product.getPrice()),
                () -> assertEquals(ProductStatus.DRAFT, product.getProductStatus()),
                () -> assertEquals(ProductCondition.GOOD, product.getProductCondition()),
                () -> assertEquals("Nike", product.getBrand()),
                () -> assertEquals("M", product.getSize()),
                () -> assertEquals("Black", product.getColor()),
                () -> assertEquals("Cotton", product.getMaterial()),
                () -> assertEquals(categoryId, product.getCategoryId()),
                () -> assertEquals(sellerId, product.getSellerId())
        );
    }

    @Test
    void createProduct_ShouldThrow_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product(
                        null,
                        "Vintage Hoodie",
                        "Nice second-hand hoodie",
                        new Money(BigDecimal.valueOf(35), "EUR"),
                        ProductStatus.DRAFT,
                        ProductCondition.GOOD,
                        "Nike",
                        "M",
                        "Black",
                        "Cotton",
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        null,
                        null,
                        null
                )
        );
    }

    @Test
    void createProduct_ShouldThrow_WhenSellerIdIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product(
                        UUID.randomUUID(),
                        "Vintage Hoodie",
                        "Nice second-hand hoodie",
                        new Money(BigDecimal.valueOf(35), "EUR"),
                        ProductStatus.DRAFT,
                        ProductCondition.GOOD,
                        "Nike",
                        "M",
                        "Black",
                        "Cotton",
                        UUID.randomUUID(),
                        null,
                        null,
                        null,
                        null
                )
        );
    }

    @Test
    void createProduct_ShouldThrow_WhenTitleIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product(
                        UUID.randomUUID(),
                        null,
                        "Nice second-hand hoodie",
                        new Money(BigDecimal.valueOf(35), "EUR"),
                        ProductStatus.DRAFT,
                        ProductCondition.GOOD,
                        "Nike",
                        "M",
                        "Black",
                        "Cotton",
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        null,
                        null,
                        null
                )
        );
    }

    @Test
    void createProduct_ShouldThrow_WhenTitleIsBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product(
                        UUID.randomUUID(),
                        "   ",
                        "Nice second-hand hoodie",
                        new Money(BigDecimal.valueOf(35), "EUR"),
                        ProductStatus.DRAFT,
                        ProductCondition.GOOD,
                        "Nike",
                        "M",
                        "Black",
                        "Cotton",
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        null,
                        null,
                        null
                )
        );
    }

    @Test
    void createProduct_ShouldThrow_WhenPriceIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product(
                        UUID.randomUUID(),
                        "Vintage Hoodie",
                        "Nice second-hand hoodie",
                        null,
                        ProductStatus.DRAFT,
                        ProductCondition.GOOD,
                        "Nike",
                        "M",
                        "Black",
                        "Cotton",
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        null,
                        null,
                        null
                )
        );
    }

    @Test
    void createProduct_ShouldThrow_WhenStatusIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product(
                        UUID.randomUUID(),
                        "Vintage Hoodie",
                        "Nice second-hand hoodie",
                        new Money(BigDecimal.valueOf(35), "EUR"),
                        null,
                        ProductCondition.GOOD,
                        "Nike",
                        "M",
                        "Black",
                        "Cotton",
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        null,
                        null,
                        null
                )
        );
    }

    @Test
    void createProduct_ShouldThrow_WhenConditionIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Product(
                        UUID.randomUUID(),
                        "Vintage Hoodie",
                        "Nice second-hand hoodie",
                        new Money(BigDecimal.valueOf(35), "EUR"),
                        ProductStatus.DRAFT,
                        null,
                        "Nike",
                        "M",
                        "Black",
                        "Cotton",
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        null,
                        null,
                        null
                )
        );
    }

    @Test
    void publish_ShouldSetStatusToAvailable_WhenProductIsDraft(){
        Product product = createValidProduct(ProductStatus.DRAFT);

        product.publish();

        assertEquals(ProductStatus.AVAILABLE, product.getProductStatus());
        assertNotNull(product.getPublishedAt());
    }

    @Test
    void publish_ShouldThrow_WhenProductIsNotDraft(){
        Product product = createValidProduct(ProductStatus.DRAFT);
        Product soldProduct = createValidProduct(ProductStatus.SOLD);
        Product archivedProduct = createValidProduct(ProductStatus.ARCHIVED);
        Product reservedProduct = createValidProduct(ProductStatus.RESERVED);
        Product availableProduct = createValidProduct(ProductStatus.AVAILABLE);

        product.publish();

        assertThrows(IllegalStateException.class, product::publish);
        assertThrows(IllegalStateException.class, soldProduct::publish);
        assertThrows(IllegalStateException.class, archivedProduct::publish);
        assertThrows(IllegalStateException.class, reservedProduct::publish);
        assertThrows(IllegalStateException.class, availableProduct::publish);
    }

    @Test
    void reserve_ShouldSetStatusToReserved_WhenProductIsAvailable(){
        Product product = createValidProduct(ProductStatus.AVAILABLE);
        UUID userId = UUID.randomUUID();
        Instant until = Instant.now().plus(Duration.ofMinutes(10));

        product.reserve(userId, until);

        assertEquals(userId, product.getReservedByUserId());
        assertEquals(until, product.getReservedUntil());
        assertEquals(ProductStatus.RESERVED, product.getProductStatus());
    }

    //test how long we can exceed in the future test for unlimited values
    @Test
    void reserve_ShouldThrow_WhenReservationDurationExceedsMaximum(){
        Product product = createValidProduct(ProductStatus.AVAILABLE);
        UUID userId = UUID.randomUUID();
        Instant untilUnlimited = Instant.now().plus(Duration.ofDays(99999999));

        assertThrows(
                IllegalArgumentException.class,
                () -> product.reserve(userId, untilUnlimited));
    }

    @Test
    void reserve_ShouldThrow_WhenReservationIsNow(){
        Product product = createValidProduct(ProductStatus.AVAILABLE);
        UUID userId = UUID.randomUUID();
        Instant now = Instant.now();

        assertThrows(IllegalArgumentException.class, () -> product.reserve(userId, now));
    }

    @Test
    void reserve_ShouldThrow_WhenReservationIsInPast(){
        Product product = createValidProduct(ProductStatus.AVAILABLE);
        UUID userId = UUID.randomUUID();
        Instant past = Instant.now().minus(Duration.ofDays(2));

        assertThrows(IllegalArgumentException.class, () -> product.reserve(userId, past));
    }

    @Test
    void reserve_ShouldThrow_WhenProductIsNotAvailable(){
        Product draftProduct = createValidProduct(ProductStatus.DRAFT);
        Product soldProduct = createValidProduct(ProductStatus.SOLD);
        Product archivedProduct = createValidProduct(ProductStatus.ARCHIVED);
        Product reservedProduct = createValidProduct(ProductStatus.RESERVED);
        UUID userId = UUID.randomUUID();
        Instant until = Instant.now().plus(Duration.ofMinutes(10));

        assertThrows(
                IllegalStateException.class,
                () -> draftProduct.reserve(userId, until));
        assertThrows(
                IllegalStateException.class,
                () -> soldProduct.reserve(userId, until));
        assertThrows(
                IllegalStateException.class,
                () -> archivedProduct.reserve(userId, until));
        assertThrows(
                IllegalStateException.class,
                () -> reservedProduct.reserve(userId, until));
    }

    @Test
    void reserve_ShouldThrow_WhenReservationUntilIsNull() {
        Product product = createValidProduct(ProductStatus.AVAILABLE);

        assertThrows(
                IllegalArgumentException.class,
                () -> product.reserve(UUID.randomUUID(), null)
        );
    }

    @Test
    void reserve_ShouldThrow_WhenReserverIsNull(){
        Product product = createValidProduct(ProductStatus.AVAILABLE);
        Instant until = Instant.now().plus(Duration.ofMinutes(10));
        UUID userId = null;

        assertThrows(
                IllegalArgumentException.class,
                () -> product.reserve(userId, until)
        );
    }

    @Test
    void releaseReservation_ShouldSetStatusToAvailable_WhenProductIsReserved(){
        Product product = createValidProduct(ProductStatus.AVAILABLE);
        UUID userId = UUID.randomUUID();
        Instant until = Instant.now().plus(Duration.ofMinutes(10));
        product.reserve(userId, until);

        product.releaseReservation();

        assertNull(product.getReservedUntil());
        assertNull(product.getReservedByUserId());
        assertEquals(ProductStatus.AVAILABLE, product.getProductStatus());
    }

    @Test
    void releaseReservation_ShouldThrow_WhenProductIsNotReserved(){
        Product availableProduct = createValidProduct(ProductStatus.AVAILABLE);
        Product draftProduct = createValidProduct(ProductStatus.DRAFT);
        Product soldProduct = createValidProduct(ProductStatus.SOLD);
        Product archivedProduct = createValidProduct(ProductStatus.ARCHIVED);

        assertThrows(IllegalStateException.class, availableProduct::releaseReservation);
        assertThrows(IllegalStateException.class, draftProduct::releaseReservation);
        assertThrows(IllegalStateException.class, soldProduct::releaseReservation);
        assertThrows(IllegalStateException.class, archivedProduct::releaseReservation);
    }

    @Test
    void markAsSold_ShouldThrow_WhenProductIsNotReserved(){
        Product availableProduct = createValidProduct(ProductStatus.AVAILABLE);
        Product draftProduct = createValidProduct(ProductStatus.DRAFT);
        Product soldProduct = createValidProduct(ProductStatus.SOLD);
        Product archivedProduct = createValidProduct(ProductStatus.ARCHIVED);

        assertThrows(IllegalStateException.class, availableProduct::markAsSold);
        assertThrows(IllegalStateException.class, draftProduct::markAsSold);
        assertThrows(IllegalStateException.class, soldProduct::markAsSold);
        assertThrows(IllegalStateException.class, archivedProduct::markAsSold);
    }

    @Test
    void markAsSold_ShouldSetStatusToSold_WhenProductIsReserved(){
        Product product = createValidProduct(ProductStatus.AVAILABLE);
        UUID userId = UUID.randomUUID();
        Instant until = Instant.now().plus(Duration.ofMinutes(10));
        product.reserve(userId, until);

        product.markAsSold();

        assertNull(product.getReservedUntil());
        assertNull(product.getReservedByUserId());
        assertEquals(ProductStatus.SOLD, product.getProductStatus());
    }

    @Test
    void archive_ShouldSetStatusToArchived_WhenProductIsAvailable(){
        Product product = createValidProduct(ProductStatus.AVAILABLE);

        product.archive();

        assertEquals(ProductStatus.ARCHIVED, product.getProductStatus());
    }

    @Test
    void archive_ShouldSetStatusToArchived_WhenProductIsDraft(){
        Product draftProduct = createValidProduct(ProductStatus.DRAFT);

        draftProduct.archive();

        assertEquals(ProductStatus.ARCHIVED, draftProduct.getProductStatus());
    }

    @Test
    void archive_ShouldThrow_WhenProductIsSold(){
        Product product = createValidProduct(ProductStatus.RESERVED);
        product.markAsSold();

        assertThrows(IllegalStateException.class, product::archive);
    }

    @Test
    void archive_ShouldThrow_WhenProductIsReserved(){
        Product product = createValidProduct(ProductStatus.AVAILABLE);
        UUID userId = UUID.randomUUID();
        Instant until = Instant.now().plus(Duration.ofMinutes(10));
        product.reserve(userId, until);

        assertThrows(IllegalStateException.class, product::archive);
    }

    @Test
    void updateProduct_ShouldReflectChanges(){
        Product product = createValidProduct(ProductStatus.DRAFT);
        Money newPrice = new Money(BigDecimal.valueOf(49.99), "EUR");
        UUID newCategoryId = UUID.randomUUID();

        product.updateDetails(
                "Updated Hoodie",
                "Updated description",
                newPrice,
                ProductCondition.VERY_GOOD,
                "Adidas",
                "L",
                "Blue",
                "Cotton",
                newCategoryId
        );

        assertEquals("Updated Hoodie", product.getTitle());
        assertEquals("Updated description", product.getDescription());
        assertEquals(newPrice, product.getPrice());
        assertEquals(ProductCondition.VERY_GOOD, product.getProductCondition());
        assertEquals("Adidas", product.getBrand());
        assertEquals("L", product.getSize());
        assertEquals("Blue", product.getColor());
        assertEquals("Cotton", product.getMaterial());
        assertEquals(newCategoryId, product.getCategoryId());
    }

    @Test
    void updateDetails_ShouldFail_WhenProductIsSold(){
        Product product = createValidProduct(ProductStatus.AVAILABLE);
        UUID buyerId = UUID.randomUUID();
        product.reserve(buyerId, Instant.now().plus(Duration.ofMinutes(15)));
        product.markAsSold();

        assertThrows(IllegalStateException.class, () ->
                        product.updateDetails(
                                "Updated Hoodie",
                                "Updated description",
                                new Money(BigDecimal.valueOf(49.99), "EUR"),
                                ProductCondition.VERY_GOOD,
                                "Adidas",
                                "L",
                                "Blue",
                                "Cotton",
                                UUID.randomUUID()
                        )
        );
    }

    @Test
    void updateDetails_ShouldFail_WhenProductIsReserved(){
        Product product = createValidProduct(ProductStatus.AVAILABLE);
        UUID buyerId = UUID.randomUUID();
        product.reserve(buyerId, Instant.now().plus(Duration.ofMinutes(15)));

        assertThrows(IllegalStateException.class, () ->
                product.updateDetails(
                        "Updated Hoodie",
                        "Updated description",
                        new Money(BigDecimal.valueOf(49.99), "EUR"),
                        ProductCondition.VERY_GOOD,
                        "Adidas",
                        "L",
                        "Blue",
                        "Cotton",
                        UUID.randomUUID()
                )
        );
    }

    @Test
    void updateDetails_ShouldFail_WhenTitleIsEmpty() {
        Product product = createValidProduct(ProductStatus.DRAFT);

        assertThrows(IllegalArgumentException.class, () ->
                product.updateDetails(
                        "",
                        "Updated description",
                        new Money(BigDecimal.valueOf(49.99), "EUR"),
                        ProductCondition.VERY_GOOD,
                        "Adidas",
                        "L",
                        "Blue",
                        "Cotton",
                        UUID.randomUUID()
                )
        );
    }

    @Test
    void updateDetails_ShouldFail_WhenProductConditionIsEmpty() {
        Product product = createValidProduct(ProductStatus.DRAFT);

        assertThrows(IllegalArgumentException.class, () ->
                product.updateDetails(
                        "Updated Hoodie",
                        "Updated description",
                        new Money(BigDecimal.valueOf(49.99), "EUR"),
                        null,
                        "Adidas",
                        "L",
                        "Blue",
                        "Cotton",
                        UUID.randomUUID()
                )
        );
    }

    @Test
    void updateDetails_ShouldFail_WhenProductPriceIsEmpty() {
        Product product = createValidProduct(ProductStatus.DRAFT);

        assertThrows(IllegalArgumentException.class, () ->
                product.updateDetails(
                        "Updated Hoodie",
                        "Updated description",
                        null,
                        ProductCondition.VERY_GOOD,
                        "Adidas",
                        "L",
                        "Blue",
                        "Cotton",
                        UUID.randomUUID()
                )
        );
    }

    @Test
    void addImage_ShouldAddImage_WhenImageIsValid() {
        Product product = createValidProduct(ProductStatus.DRAFT);
        ProductImage image = new ProductImage(
                UUID.randomUUID(),
                "https://res.cloudinary.com/demo/image/upload/original-hoodie.jpg",
                "https://res.cloudinary.com/demo/image/upload/optimized-hoodie.webp",
                "https://res.cloudinary.com/demo/image/upload/transparent-hoodie.png",
                "products/original-hoodie",
                true,
                0
        );

        product.addImage(image);

        assertEquals(1, product.getImages().size());
        assertEquals(image, product.getImages().getFirst());
    }

    @Test
    void addImage_ShouldThrow_WhenImageIsNull() {
        Product product = createValidProduct(ProductStatus.DRAFT);

        assertThrows(IllegalArgumentException.class, () -> product.addImage(null));
    }








}
