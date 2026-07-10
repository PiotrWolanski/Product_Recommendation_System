package pl.pwola.recommendation.recommendation;

import org.springframework.stereotype.Service;
import pl.pwola.recommendation.nlp.SynonymService;
import pl.pwola.recommendation.product.Product;

import java.util.HashSet;
import java.util.Set;

@Service
public class ScoringService {

    private final SynonymService synonymService;

    public ScoringService(SynonymService synonymService) {
        this.synonymService = synonymService;
    }

    public double calculateTextScore(Set<String> queryTokens, Set<String> productTokens) {
        if (queryTokens == null || queryTokens.isEmpty()) {
            return 0.0;
        }

        int matchedIntentions = 0;

        for (String queryToken : queryTokens) {
            Set<String> queryTokenSynonyms = synonymService.getSynonyms(queryToken);

            boolean hasMatch = queryTokenSynonyms.stream()
                    .anyMatch(productTokens::contains);

            if (hasMatch) {
                matchedIntentions++;
            }
        }

        return (double) matchedIntentions / queryTokens.size();
    }

    public Set<String> findMatchedKeywords(Set<String> expandedQueryTokens, Set<String> productTokens) {
        Set<String> matchedKeywords = new HashSet<>(expandedQueryTokens);
        matchedKeywords.retainAll(productTokens);
        return matchedKeywords;
    }

    public int calculateFinalScore(double textScore, double tfidfSimilarity, Product product) {
        if (textScore <= 0.0 && tfidfSimilarity <= 0.0) {
            return 0;
        }

        double ratingScore = calculateRatingScore(product.getRating());
        double reviewsScore = calculateReviewsScore(product.getReviewsCount());

        double finalScore = tfidfSimilarity * 0.50
                + textScore * 0.30
                + ratingScore * 0.12
                + reviewsScore * 0.08;

        return (int) Math.round(finalScore * 100);
    }

    private double calculateRatingScore(Double rating) {
        if (rating == null) {
            return 0.0;
        }

        return Math.min(Math.max(rating / 5.0, 0.0), 1.0);
    }

    private double calculateReviewsScore(Integer reviewsCount) {
        if (reviewsCount == null || reviewsCount <= 0) {
            return 0.0;
        }

        int maxExpectedReviews = 300;

        return Math.min((double) reviewsCount / maxExpectedReviews, 1.0);
    }
}