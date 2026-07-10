package pl.pwola.recommendation.nlp;

import java.math.BigDecimal;

public record PriceConstraint(
        BigDecimal minPrice,
        BigDecimal maxPrice
) {
    public boolean hasAnyConstraint() {
        return minPrice != null || maxPrice != null;
    }
}