package pl.pwola.recommendation.product;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "main_category")
    private String mainCategory;

    @Column(name = "sub_category")
    private String subCategory;

    @Column(name = "product_type")
    private String productType;

    private String color;

    @Column(name = "target_gender")
    private String targetGender;

    private Boolean waterproof;

    private String season;

    private BigDecimal price;

    private Double rating;

    @Column(name = "reviews_count")
    private Integer reviewsCount;

    @Column(columnDefinition = "TEXT")
    private String url;

    public Product() {
    }

    public Product(
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