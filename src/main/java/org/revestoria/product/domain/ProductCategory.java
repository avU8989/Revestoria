package org.revestoria.product.domain;

import java.util.UUID;

public class ProductCategory {
    private final UUID id;
    private final String name;
    private final String slug;
    private final UUID parentCategoryId;
    private final boolean active;

    public ProductCategory(UUID id, String name, String slug, UUID parentCategoryId, boolean active) {

        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Category name is required");
        }

        if(slug == null || slug.isBlank()){
            throw new IllegalArgumentException("Category slug is required");
        }

        this.id = id;
        this.name = name;
        this.slug = slug;
        this.parentCategoryId = parentCategoryId;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public UUID getParentCategoryId() {
        return parentCategoryId;
    }

    public boolean isActive() {
        return active;
    }
}
