package pl.pwola.recommendation.nlp;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PriceExtractionServiceTest {

    private final PriceExtractionService priceExtractionService = new PriceExtractionService();

    @Test
    void shouldExtractMaxPriceFromDoPhrase() {
        PriceConstraint result = priceExtractionService.extractPriceConstraint(
                "buty wodoodporne do 390 zł"
        );

        assertNull(result.minPrice());
        assertEquals(new BigDecimal("390"), result.maxPrice());
    }

    @Test
    void shouldExtractMaxPriceFromPoniżejPhrase() {
        PriceConstraint result = priceExtractionService.extractPriceConstraint(
                "kurtka przeciwdeszczowa poniżej 500 zł"
        );

        assertNull(result.minPrice());
        assertEquals(new BigDecimal("500"), result.maxPrice());
    }

    @Test
    void shouldExtractMinPriceFromOdPhrase() {
        PriceConstraint result = priceExtractionService.extractPriceConstraint(
                "buty trekkingowe od 200 zł"
        );

        assertEquals(new BigDecimal("200"), result.minPrice());
        assertNull(result.maxPrice());
    }

    @Test
    void shouldReturnEmptyConstraintWhenPriceIsNotPresent() {
        PriceConstraint result = priceExtractionService.extractPriceConstraint(
                "buty wodoodporne w góry"
        );

        assertNull(result.minPrice());
        assertNull(result.maxPrice());
        assertFalse(result.hasAnyConstraint());
    }
}