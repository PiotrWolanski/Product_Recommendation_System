package pl.pwola.recommendation.nlp;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import pl.pwola.recommendation.product.Product;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TfidfSimilarityServiceTest {

    private final TextProcessingService textProcessingService = new TextProcessingService(
            new StopWordsProvider(),
            new SynonymService(),
            new OpenNlpTokenizerService()
    );

    private final TfidfSimilarityService tfidfSimilarityService = new TfidfSimilarityService(
            textProcessingService
    );

    @Test
    void shouldReturnHigherSimilarityForMoreRelevantProduct() {
        Product matchingProduct = createProduct(
                1L,
                "Buty trekkingowe wodoodporne",
                "Wodoodporne buty trekkingowe przeznaczone w góry i na deszcz.",
                "buty trekkingowe"
        );

        Product unrelatedProduct = createProduct(
                2L,
                "Elegancka sukienka",
                "Czarna sukienka na randkę i spotkanie wieczorowe.",
                "sukienka"
        );

        Map<Long, Double> similarities = tfidfSimilarityService.calculateSimilarities(
                "buty wodoodporne w góry",
                List.of(matchingProduct, unrelatedProduct),
                product -> String.join(" ",
                        product.getName(),
                        product.getDescription(),
                        product.getProductType()
                )
        );

        assertTrue(similarities.get(1L) > 0.0);
        assertTrue(similarities.get(1L) > similarities.get(2L));
    }

    @Test
    void shouldReturnEmptyMapForBlankQuery() {
        Product product = createProduct(
                1L,
                "Buty trekkingowe",
                "Buty w góry.",
                "buty trekkingowe"
        );

        Map<Long, Double> similarities = tfidfSimilarityService.calculateSimilarities(
                "",
                List.of(product),
                p -> p.getName() + " " + p.getDescription()
        );

        assertTrue(similarities.isEmpty());
    }

    private Product createProduct(Long id, String name, String description, String productType) {
        Product product = new Product();

        ReflectionTestUtils.setField(product, "id", id);
        ReflectionTestUtils.setField(product, "name", name);
        ReflectionTestUtils.setField(product, "description", description);
        ReflectionTestUtils.setField(product, "productType", productType);

        return product;
    }
}