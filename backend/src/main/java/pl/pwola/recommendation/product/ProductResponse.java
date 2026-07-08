package pl.pwola.recommendation.product;

import java.math.BigDecimal;

public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private String mainCategory;
    private String subCategory;
    private String productType;
    private String color;
    private String targetGender;
    private Boolean waterproof;
    private String season;
    private BigDecimal price;
    private Double rating;
    private Integer reviewsCount;
    private String url;

    public ProductResponse(
            Long id,
            String name,
            String description,
            String mainCategory,
            String subCategory,
            String productType,
            String color,
            String targetGender,
            Boolean waterproof,
            String season,
            BigDecimal price,
            Double rating,
            Integer reviewsCount,
            String url
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
        this.productType = productType;
        this.color = color;
        this.targetGender = targetGender;
        this.waterproof = waterproof;
        this.season = season;
        this.price = price;
        this.rating = rating;
        this.reviewsCount = reviewsCount;
        this.url = url;
    }

    public static ProductResponse fromProduct(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getMainCategory(),
                product.getSubCategory(),
                product.getProductType(),
                product.getColor(),
                product.getTargetGender(),
                product.getWaterproof(),
                product.getSeason(),
                product.getPrice(),
                product.getRating(),
                product.getReviewsCount(),
                product.getUrl()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public String getProductType() {
        return productType;
    }

    public String getColor() {
        return color;
    }

    public String getTargetGender() {
        return targetGender;
    }

    public Boolean getWaterproof() {
        return waterproof;
    }

    public String getSeason() {
        return season;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Double getRating() {
        return rating;
    }

    public Integer getReviewsCount() {
        return reviewsCount;
    }

    public String getUrl() {
        return url;
    }
}