package pl.pwola.recommendation.recommendation;

import java.math.BigDecimal;
import java.util.Set;

public class RecommendationResponse {

    private Long productId;
    private String productName;
    private String category;
    private BigDecimal price;
    private Double rating;
    private Integer reviewsCount;
    private String color;
    private String targetGender;
    private String shoeType;
    private Boolean waterproof;
    private String season;
    private int score;
    private Set<String> matchedKeywords;
    private String reason;

    public RecommendationResponse(
            Long productId,
            String productName,
            String category,
            BigDecimal price,
            Double rating,
            Integer reviewsCount,
            String color,
            String targetGender,
            String shoeType,
            Boolean waterproof,
            String season,
            int score,
            Set<String> matchedKeywords,
            String reason
    ) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.rating = rating;
        this.reviewsCount = reviewsCount;
        this.color = color;
        this.targetGender = targetGender;
        this.shoeType = shoeType;
        this.waterproof = waterproof;
        this.season = season;
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