package pl.pwola.recommendation.recommendation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class RecommendationRequest {

    @NotBlank(message = "Query is required")
    private String query;

    @Min(value = 1, message = "maxResults must be at least 1")
    @Max(value = 50, message = "maxResults cannot be greater than 50")
    private Integer maxResults = 5;

    private String targetGender;
    private String color;
    private String mainCategory;
    private String productType;

    public RecommendationRequest() {
    }

    public String getQuery() {
        return query;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public String getTargetGender() {
        return targetGender;
    }

    public String getColor() {
        return color;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public String getProductType() {
        return productType;
    }
}