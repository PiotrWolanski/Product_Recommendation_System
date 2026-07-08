package pl.pwola.recommendation.recommendation;

import java.math.BigDecimal;
import java.util.Set;

public class RecommendationResponse {

    private Long productId;
    private String productName;
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
    private int score;
    private Set<String> matchedKeywords;
    private String reason;

    public RecommendationResponse(
            Long productId,
            String productName,
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
            int score,
            Set<String> matchedKeywords,
            String reason
    ) {
        this.productId = productId;
        this.productName = productName;
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
        this.score = score;
        this.matchedKeywords = matchedKeywords;
        this.reason = reason;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
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

    public int getScore() {
        return score;
    }

    public Set<String> getMatchedKeywords() {
        return matchedKeywords;
    }

    public String getReason() {
        return reason;
    }
}