package pl.pwola.recommendation.recommendation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class RecommendationRequest {

    @NotBlank(message = "Query is required")
    private String query;

    @Min(value = 1, message = "maxResults must be at least 1")
    @Max(value = 20, message = "maxResults cannot be greater than 20")
    private Integer maxResults = 5;

    public RecommendationRequest() {
    }

    public String getQuery() {
        return query;
    }

    public Integer getMaxResults() {
        return maxResults;
    }
}