package pl.pwola.recommendation.recommendation;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import pl.pwola.recommendation.nlp.SynonymService;
import pl.pwola.recommendation.product.Product;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ScoringServiceTest {

    private final ScoringService scoringService = new ScoringService(new SynonymService());

    @Test
    void shouldCalculateTextScoreForMatchingTokens() {
        double score = scoringService.calculateTextScore(
                Set.of("buty", "wodoodporne"),
                Set.of("obuwie", "wodoodporne", "trekkingowe")
        );

        assertTrue(score > 0.0);
    }

    @Test
    void shouldReturnZeroTextScoreWhenThereIsNoMatch() {
        double score = scoringService.calculateTextScore(
                Set.of("buty", "wodoodporne"),
                Set.of("sukienka", "elegancka")
        );

        assertEquals(0.0, score);
    }

    @Test
    void shouldFindMatchedKeywords() {
        Set<String> matchedKeywords = scoringService.findMatchedKeywords(
                Set.of("buty", "obuwie", "deszcz"),
                Set.of("obuwie", "czarny", "trekkingowe")
        );

        assertEquals(1, matchedKeywords.size());
        assertTrue(matchedKeywords.contains("obuwie"));
    }

    @Test
    void shouldReturnZeroFinalScoreWhenTextAndTfidfAreZero() {
        Product product = createProductWithRatingAndReviews(5.0, 300);

        int score = scoringService.calculateFinalScore(0.0, 0.0, product);

        assertEquals(0, score);
    }

    @Test
    void shouldCalculateMaximumFinalScoreForPerfectMatch() {
        Product product = createProductWithRatingAndReviews(5.0, 300);

        int score = scoringService.calculateFinalScore(1.0, 1.0, product);

        assertEquals(100, score);
    }

    private Product createProductWithRatingAndReviews(Double rating, Integer reviewsCount) {
        Product product = new Product();

        ReflectionTestUtils.setField(product, "rating", rating);
        ReflectionTestUtils.setField(product, "reviewsCount", reviewsCount);

        return product;
    }
}