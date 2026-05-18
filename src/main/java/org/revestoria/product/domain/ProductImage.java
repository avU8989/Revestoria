package org.revestoria.product.domain;

import java.util.UUID;

public class ProductImage {
    private final UUID id;
    private final String originalUrl;
    private final String optimizedUrl;
    private final String transparentUrl;
    private final String providerPublicId;
    private final boolean mainImage;
    private final int sortOrder;

    public ProductImage(UUID id, String originalUrl, String optimizedUrl, String transparentUrl, String providerPublicId, boolean mainImage, int sortOrder) {
        if(originalUrl == null || originalUrl.isBlank()){
            throw new IllegalArgumentException("Original image url is required");
        }

        this.id = id;
        this.originalUrl = originalUrl;
        this.optimizedUrl = optimizedUrl;
        this.transparentUrl = transparentUrl;
        this.providerPublicId = providerPublicId;
        this.mainImage = mainImage;
        this.sortOrder = sortOrder;
    }

    public UUID getId() {
        return id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getOptimizedUrl() {
        return optimizedUrl;
    }

    public String getTransparentUrl() {
        return transparentUrl;
    }

    public String getProviderPublicId() {
        return providerPublicId;
    }

    public boolean isMainImage() {
        return mainImage;
    }

    public int getSortOrder() {
        return sortOrder;
    }
}
