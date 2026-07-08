package pl.pwola.recommendation.recommendation;

import org.springframework.stereotype.Service;
import pl.pwola.recommendation.nlp.TextProcessingService;
import pl.pwola.recommendation.product.Product;
import pl.pwola.recommendation.product.ProductRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
public class RecommendationService {

    private final ProductRepository productRepository;
    private final TextProcessingService textProcessingService;
    private final ScoringService scoringService;

    public RecommendationService(
            ProductRepository productRepository,
            TextProcessingService textProcessingService,
            ScoringService scoringService
    ) {
        this.productRepository = productRepository;
        this.textProcessingService = textProcessingService;
        this.scoringService = scoringService;
    }

    public List<RecommendationResponse> recommendProducts(RecommendationRequest request) {
        Set<String> queryTokens = textProcessingService.extractImportantTokens(request.getQuery());
        Set<String> expandedQueryTokens = textProcessingService.extractExpandedTokens(request.getQuery());

        int maxResults = request.getMaxResults() == null ? 5 : request.getMaxResults();

        return productRepository.findAll()
                .stream()
                .map(product -> buildRecommendationResponse(product, queryTokens, expandedQueryTokens))
                .filter(response -> response.getScore() > 0)
                .sorted(Comparator.comparingInt(RecommendationResponse::getScore).reversed())
                .limit(maxResults)
                .toList();
    }

    private RecommendationResponse buildRecommendationResponse(
            Product product,
            Set<String> queryTokens,
            Set<String> expandedQueryTokens
    ) {
        String productText = buildProductSearchText(product);
        Set<String> productTokens = textProcessingService.extractImportantTokens(productText);

        double textScore = scoringService.calculateTextScore(queryTokens, productTokens);
        int finalScore = scoringService.calculateFinalScore(textScore, product);

        Set<String> matchedKeywords = scoringService.findMatchedKeywords(expandedQueryTokens, productTokens);

        return new RecommendationResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getRating(),
                product.getReviewsCount(),
                product.getColor(),
                product.getTargetGender(),
                product.getShoeType(),
                product.getWaterproof(),
                product.getSeason(),
                finalScore,
                matchedKeywords,
                buildReason(matchedKeywords, finalScore)
        );
    }

    private String buildProductSearchText(Product product) {
        return String.join(" ",
                safe(product.getName()),
                safe(product.getCategory()),
                safe(product.getDescription()),
                safe(product.getColor()),
                safe(product.getTargetGender()),
                safe(product.getShoeType()),
                safe(product.getSeason()),
                waterproofText(product.getWaterproof())
        );
    }

    private String waterproofText(Boolean waterproof) {
        if (Boolean.TRUE.equals(waterproof)) {
            return "wodoodporne wodoodporny nieprzemakalne waterproof membrana deszcz";
        }

        return "";
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    private String buildReason(Set<String> matchedKeywords, int score) {
        if (matchedKeywords == null || matchedKeywords.isEmpty()) {
            return "Produkt ma niskie dopasowanie, ponieważ nie wykryto ważnych wspólnych cech.";
        }

        if (score >= 75) {
            return "Produkt mocno pasuje do zapytania, ponieważ zawiera cechy: "
                    + String.join(", ", matchedKeywords) + ".";
        }

        if (score >= 40) {
            return "Produkt częściowo pasuje do zapytania, ponieważ zawiera: "
                    + String.join(", ", matchedKeywords) + ".";
        }

        return "Produkt ma słabe dopasowanie, ale wykryto wspólne cechy: "
                + String.join(", ", matchedKeywords) + ".";
    }
}