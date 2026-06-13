package pl.pwola.recommendation.product;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class ProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Product description is required")
    private String description;

    private String category;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @DecimalMin(value = "0.0", message = "Rating cannot be lower than 0")
    @DecimalMax(value = "5.0", message = "Rating cannot be greater than 5")
    private Double rating;

    @Min(value = 0, message = "Reviews count cannot be negative")
    private Integer reviewsCount;

    private String url;

    public ProductRequest() {
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
}