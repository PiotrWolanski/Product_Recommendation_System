package pl.pwola.recommendation.recommendation;

import org.springframework.stereotype.Service;
import pl.pwola.recommendation.nlp.PriceConstraint;
import pl.pwola.recommendation.nlp.PriceExtractionService;
import pl.pwola.recommendation.nlp.TextProcessingService;
import pl.pwola.recommendation.nlp.TfidfSimilarityService;
import pl.pwola.recommendation.product.Product;
import pl.pwola.recommendation.product.ProductRepository;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RecommendationService {

    private final ProductRepository productRepository;
    private final TextProcessingService textProcessingService;
    private final ScoringService scoringService;
    private final PriceExtractionService priceExtractionService;
    private final TfidfSimilarityService tfidfSimilarityService;

    public RecommendationService(
            ProductRepository productRepository,
            TextProcessingService textProcessingService,
            ScoringService scoringService,
            PriceExtractionService priceExtractionService,
            TfidfSimilarityService tfidfSimilarityService
    ) {
        this.productRepository = productRepository;
        this.textProcessingService = textProcessingService;
        this.scoringService = scoringService;
        this.priceExtractionService = priceExtractionService;
        this.tfidfSimilarityService = tfidfSimilarityService;
    }

    public List<RecommendationResponse> recommendProducts(RecommendationRequest request) {
        Set<String> queryTokens = textProcessingService.extractImportantTokens(request.getQuery());
        Set<String> expandedQueryTokens = textProcessingService.extractExpandedTokens(request.getQuery());
        PriceConstraint priceConstraint = priceExtractionService.extractPriceConstraint(request.getQuery());

        int maxResults = request.getMaxResults() == null ? 5 : request.getMaxResults();

        List<Product> candidateProducts = productRepository.findAll()
                .stream()
                .filter(product -> matchesFilters(product, request))
                .filter(product -> matchesPriceConstraint(product, priceConstraint))
                .toList();

        Map<Long, Double> tfidfSimilarities = tfidfSimilarityService.calculateSimilarities(
                request.getQuery(),
                candidateProducts,
                this::buildProductSearchText
        );

        return candidateProducts
                .stream()
                .map(product -> buildRecommendationResponse(
                        product,
                        queryTokens,
                        expandedQueryTokens,
                        tfidfSimilarities.getOrDefault(product.getId(), 0.0)
                ))
                .filter(response -> response.getScore() > 0)
                .sorted(Comparator.comparingInt(RecommendationResponse::getScore).reversed())
                .limit(maxResults)
                .toList();
    }

    private boolean matchesFilters(Product product, RecommendationRequest request) {
        return matchesGender(product.getTargetGender(), request.getTargetGender())
                && matchesTextFilter(product.getColor(), request.getColor())
                && matchesTextFilter(product.getMainCategory(), request.getMainCategory())
                && matchesTextFilter(product.getProductType(), request.getProductType());
    }

    private boolean matchesPriceConstraint(Product product, PriceConstraint priceConstraint) {
        if (priceConstraint == null || !priceConstraint.hasAnyConstraint()) {
            return true;
        }

        BigDecimal productPrice = product.getPrice();

        if (productPrice == null) {
            return false;
        }

        if (priceConstraint.minPrice() != null
                && productPrice.compareTo(priceConstraint.minPrice()) < 0) {
            return false;
        }

        return priceConstraint.maxPrice() == null
                || productPrice.compareTo(priceConstraint.maxPrice()) <= 0;
    }

    private boolean matchesGender(String productGender, String selectedGender) {
        if (isBlank(selectedGender)) {
            return true;
        }

        if (isBlank(productGender)) {
            return false;
        }

        if ("unisex".equalsIgnoreCase(selectedGender)) {
            return "unisex".equalsIgnoreCase(productGender);
        }

        return selectedGender.equalsIgnoreCase(productGender)
                || "unisex".equalsIgnoreCase(productGender);
    }

    private boolean matchesTextFilter(String productValue, String selectedValue) {
        if (isBlank(selectedValue)) {
            return true;
        }

        if (isBlank(productValue)) {
            return false;
        }

        return productValue.equalsIgnoreCase(selectedValue);
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private RecommendationResponse buildRecommendationResponse(
            Product product,
            Set<String> queryTokens,
            Set<String> expandedQueryTokens,
            double tfidfSimilarity
    ) {
        String productText = buildProductSearchText(product);
        Set<String> productTokens = textProcessingService.extractImportantTokens(productText);

        double textScore = scoringService.calculateTextScore(queryTokens, productTokens);
        int finalScore = scoringService.calculateFinalScore(textScore, tfidfSimilarity, product);

        Set<String> matchedKeywords = scoringService.findMatchedKeywords(expandedQueryTokens, productTokens);

        return new RecommendationResponse(
                product.getId(),
                product.getName(),
                product.getMainCategory(),
                product.getSubCategory(),
                product.getProductType(),
                product.getColor(),
                product.getTargetGender(),
                product.getWaterproof(),
                product.getSeason(),
                product.getPrice(),
                product.getRating(),
                product.getReviewsCount(),
                tfidfSimilarity,
                finalScore,
                matchedKeywords,
                buildReason(matchedKeywords, finalScore, tfidfSimilarity)
        );
    }

    private String buildProductSearchText(Product product) {
        return String.join(" ",
                safe(product.getName()),
                safe(product.getDescription()),
                safe(product.getMainCategory()),
                safe(product.getSubCategory()),
                safe(product.getProductType()),
                safe(product.getColor()),
                safe(product.getTargetGender()),
                safe(product.getSeason()),
                waterproofText(product.getWaterproof())
        );
    }

    private String waterproofText(Boolean waterproof) {
        if (Boolean.TRUE.equals(waterproof)) {
            return "wodoodporne wodoodporny nieprzemakalne waterproof membrana deszcz przeciwdeszczowe";
        }

        return "";
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    private String buildReason(Set<String> matchedKeywords, int score, double tfidfSimilarity) {
        int similarityPercent = (int) Math.round(tfidfSimilarity * 100);

        if (matchedKeywords == null || matchedKeywords.isEmpty()) {
            return "Produkt został oceniony na podstawie podobieństwa TF-IDF. Podobieństwo tekstowe wynosi "
                    + similarityPercent + "%.";
        }

        if (score >= 75) {
            return "Produkt mocno pasuje do zapytania. Wykryte cechy: "
                    + String.join(", ", matchedKeywords)
                    + ". Podobieństwo TF-IDF: " + similarityPercent + "%.";
        }

        if (score >= 40) {
            return "Produkt częściowo pasuje do zapytania. Wspólne cechy: "
                    + String.join(", ", matchedKeywords)
                    + ". Podobieństwo TF-IDF: " + similarityPercent + "%.";
        }

        return "Produkt ma słabsze dopasowanie, ale wykryto cechy: "
                + String.join(", ", matchedKeywords)
                + ". Podobieństwo TF-IDF: " + similarityPercent + "%.";
    }
}