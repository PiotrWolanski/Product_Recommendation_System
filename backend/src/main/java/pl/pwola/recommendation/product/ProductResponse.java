package pl.pwola.recommendation.product;

import java.math.BigDecimal;

public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private Double rating;
    private Integer reviewsCount;
    private String url;
    private String color;
    private String targetGender;
    private String shoeType;
    private Boolean waterproof;
    private String season;

    public ProductResponse(
            Long id,
            String name,
            String description,
            String category,
            BigDecimal price,
            Double rating,
            Integer reviewsCount,
            String url,
            String color,
            String targetGender,
            String shoeType,
            Boolean waterproof,
            String season
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.rating = rating;
        this.reviewsCount = reviewsCount;
        this.url = url;
        this.color = color;
        this.targetGender = targetGender;
        this.shoeType = shoeType;
        this.waterproof = waterproof;
        this.season = season;
    }

    public static ProductResponse fromProduct(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getPrice(),
                product.getRating(),
                product.getReviewsCount(),
                product.getUrl(),
                product.getColor(),
                product.getTargetGender(),
                product.getShoeType(),
                product.getWaterproof(),
                product.getSeason()
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

    public String getCategory() {
        return category;
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

    public String getColor() {
        return color;
    }

    public String getTargetGender() {
        return targetGender;
    }

    public String getShoeType() {
        return shoeType;
    }

    public Boolean getWaterproof() {
        return waterproof;
    }

    public String getSeason() {
        return season;
    }
}