package org.revestoria.product.domain;

import org.revestoria.core.shared.Money;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//can the product be published
//can the product be reserved
//can the product be sold
//can the product be archived

public class Product {
    private final UUID id;

    private String title;
    private String description;
    private Money price;
    private ProductStatus productStatus;
    private ProductCondition productCondition;

    private String brand;
    private String size;
    private String color;
    private String material;

    private UUID categoryId;

    private final List<ProductImage> images = new ArrayList<>();

    private Instant reservedUntil;
    private UUID reservedByUserId;
    private Instant publishedAt;

    public Product(
            UUID id,
            String title,
            String description,
            Money price,
            ProductStatus productStatus,
            ProductCondition productCondition,
            String brand,
            String size,
            String color,
            String material,
            UUID categoryId,
            Instant reservedUntil,
            UUID reservedByUserId,
            Instant publishedAt
    ) {
        validateRequiredFields(id, price, productStatus, productCondition, title);

        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.productStatus = productStatus;
        this.productCondition = productCondition;
        this.brand = brand;
        this.size = size;
        this.color = color;
        this.material = material;
        this.categoryId = categoryId;
        this.reservedUntil = reservedUntil;
        this.reservedByUserId = reservedByUserId;
        this.publishedAt = publishedAt;
    }

    private void validateRequiredFields(UUID id, Money price, ProductStatus status, ProductCondition condition, String title) {
        if (id == null) {
            throw new IllegalArgumentException("Product id is required");
        }

        if (price == null) {
            throw new IllegalArgumentException("Product price is required");
        }

        if (status == null) {
            throw new IllegalArgumentException("Product productStatus is required");
        }

        if (condition == null) {
            throw new IllegalArgumentException("Product productCondition is required");
        }

        if(title == null || title.isBlank()){
            throw new IllegalArgumentException("Product title is required");
        }
    }

    public void addImage(ProductImage image){
        if(image == null){
            throw new IllegalArgumentException("Image is required");
        }

        images.add(image);
    }

    public void publish(){
        if(productStatus != ProductStatus.DRAFT){
            throw new IllegalStateException("Only draft products can be published");
        }
        productStatus = ProductStatus.AVAILABLE;
        publishedAt = Instant.now();
    }

    public void reserve(UUID userId, Instant until){
        if(productStatus != ProductStatus.AVAILABLE){
            throw new IllegalStateException("Product is not available");
        }

        if(userId == null){
            throw new IllegalArgumentException("Reservation user id is required");
        }

        Instant now = Instant.now();

        if (until == null || !until.isAfter(now)) {
            throw new IllegalArgumentException("Reservation time frame must be in the future");
        }

        productStatus = ProductStatus.RESERVED;
        reservedByUserId = userId;
        reservedUntil = until;
    }

    public void releaseReservation(){
        if(productStatus != ProductStatus.RESERVED){
            throw new IllegalStateException("Product is not reserved");
        }

        productStatus = ProductStatus.AVAILABLE;
        reservedByUserId = null;
        reservedUntil = null;
    }

    //FLOW -> AVAILABLE -> RESERVED -> SOLD
    public void markAsSold() {
        if (productStatus != ProductStatus.RESERVED) {
            throw new IllegalStateException("Only reserved products can be marked as sold");
        }

        productStatus = ProductStatus.SOLD;
        reservedByUserId = null;
        reservedUntil = null;
    }

    public void archive() {
        if (productStatus == ProductStatus.SOLD) {
            throw new IllegalStateException("Sold products cannot be archived");
        }

        if (productStatus == ProductStatus.RESERVED) {
            throw new IllegalStateException("Reserved products cannot be archived");
        }

        productStatus = ProductStatus.ARCHIVED;
    }

    public void updateDetails(
            String title,
            String description,
            Money price,
            ProductCondition condition,
            String brand,
            String size,
            String color,
            String material,
            UUID categoryId
    ) {
        if (productStatus == ProductStatus.SOLD || productStatus == ProductStatus.RESERVED) {
            throw new IllegalStateException("Reserved or sold products cannot be edited");
        }

        validateRequiredFields(id, price, productStatus, condition, title);

        this.title = title;
        this.description = description;
        this.price = price;
        this.productCondition = condition;
        this.brand = brand;
        this.size = size;
        this.color = color;
        this.material = material;
        this.categoryId = categoryId;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Money getPrice() {
        return price;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public ProductCondition getProductCondition() {
        return productCondition;
    }

    public String getBrand() {
        return brand;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public String getMaterial() {
        return material;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public List<ProductImage> getImages() {
        return List.copyOf(images);
    }

    public Instant getReservedUntil() {
        return reservedUntil;
    }

    public UUID getReservedByUserId() {
        return reservedByUserId;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public boolean isAvailable() {
        return productStatus == ProductStatus.AVAILABLE;
    }

    public boolean isReserved() {
        return productStatus == ProductStatus.RESERVED;
    }

    public boolean isSold() {
        return productStatus == ProductStatus.SOLD;
    }

    public boolean isReservationExpired(Instant now) {
        if (now == null) {
            throw new IllegalArgumentException("Current time is required");
        }

        return productStatus == ProductStatus.RESERVED
                && reservedUntil != null
                && reservedUntil.isBefore(now);
    }
}
