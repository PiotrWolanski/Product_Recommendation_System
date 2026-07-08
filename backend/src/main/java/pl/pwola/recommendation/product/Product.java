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

    private String category;

    private BigDecimal price;

    private Double rating;

    @Column(name = "reviews_count")
    private Integer reviewsCount;

    @Column(columnDefinition = "TEXT")
    private String url;

    private String color;

    @Column(name = "target_gender")
    private String targetGender;

    @Column(name = "shoe_type")
    private String shoeType;

    private Boolean waterproof;

    private String season;

    public Product() {
    }

    public Product(
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setReviewsCount(Integer reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setTargetGender(String targetGender) {
        this.targetGender = targetGender;
    }

    public void setShoeType(String shoeType) {
        this.shoeType = shoeType;
    }

    public void setWaterproof(Boolean waterproof) {
        this.waterproof = waterproof;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}