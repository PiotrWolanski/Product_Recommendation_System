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