package pl.pwola.recommendation.nlp;

import org.springframework.stereotype.Service;
import pl.pwola.recommendation.product.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TfidfSimilarityService {

    private final TextProcessingService textProcessingService;

    public TfidfSimilarityService(TextProcessingService textProcessingService) {
        this.textProcessingService = textProcessingService;
    }

    public Map<Long, Double> calculateSimilarities(
            String query,
            List<Product> products,
            Function<Product, String> productTextProvider
    ) {
        if (query == null || query.isBlank() || products == null || products.isEmpty()) {
            return Map.of();
        }

        List<String> queryTokens = textProcessingService.extractImportantTokenList(query);

        if (queryTokens.isEmpty()) {
            return Map.of();
        }

        Map<Long, List<String>> productTokensById = new HashMap<>();
        List<List<String>> corpusDocuments = new ArrayList<>();

        for (Product product : products) {
            String productText = productTextProvider.apply(product);
            List<String> productTokens = textProcessingService.extractImportantTokenList(productText);

            productTokensById.put(product.getId(), productTokens);
            corpusDocuments.add(productTokens);
        }

        Map<String, Double> idfValues = calculateIdfValues(corpusDocuments, queryTokens);
        Map<String, Double> queryVector = buildTfidfVector(queryTokens, idfValues);

        Map<Long, Double> similarities = new HashMap<>();

        for (Map.Entry<Long, List<String>> entry : productTokensById.entrySet()) {
            Map<String, Double> productVector = buildTfidfVector(entry.getValue(), idfValues);
            double similarity = cosineSimilarity(queryVector, productVector);

            similarities.put(entry.getKey(), similarity);
        }

        return similarities;
    }

    private Map<String, Double> calculateIdfValues(List<List<String>> documents, List<String> queryTokens) {
        Map<String, Double> idfValues = new HashMap<>();
        Set<String> vocabulary = new HashSet<>(queryTokens);

        for (List<String> document : documents) {
            vocabulary.addAll(document);
        }

        int documentCount = documents.size();

        for (String term : vocabulary) {
            int documentsContainingTerm = 0;

            for (List<String> document : documents) {
                Set<String> uniqueDocumentTerms = new HashSet<>(document);

                if (uniqueDocumentTerms.contains(term)) {
                    documentsContainingTerm++;
                }
            }

            double idf = Math.log((documentCount + 1.0) / (documentsContainingTerm + 1.0)) + 1.0;
            idfValues.put(term, idf);
        }

        return idfValues;
    }

    private Map<String, Double> buildTfidfVector(List<String> tokens, Map<String, Double> idfValues) {
        if (tokens == null || tokens.isEmpty()) {
            return Map.of();
        }

        Map<String, Long> termCounts = tokens.stream()
                .collect(Collectors.groupingBy(token -> token, Collectors.counting()));

        long maxTermFrequency = termCounts.values()
                .stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(1);

        Map<String, Double> vector = new HashMap<>();

        for (Map.Entry<String, Long> entry : termCounts.entrySet()) {
            String term = entry.getKey();
            double termFrequency = (double) entry.getValue() / maxTermFrequency;
            double idf = idfValues.getOrDefault(term, 1.0);

            vector.put(term, termFrequency * idf);
        }

        return vector;
    }

    private double cosineSimilarity(Map<String, Double> firstVector, Map<String, Double> secondVector) {
        if (firstVector.isEmpty() || secondVector.isEmpty()) {
            return 0.0;
        }

        Set<String> terms = new HashSet<>(firstVector.keySet());
        terms.addAll(secondVector.keySet());

        double dotProduct = 0.0;
        double firstNorm = 0.0;
        double secondNorm = 0.0;

        for (String term : terms) {
            double firstValue = firstVector.getOrDefault(term, 0.0);
            double secondValue = secondVector.getOrDefault(term, 0.0);

            dotProduct += firstValue * secondValue;
            firstNorm += firstValue * firstValue;
            secondNorm += secondValue * secondValue;
        }

        if (firstNorm == 0.0 || secondNorm == 0.0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(firstNorm) * Math.sqrt(secondNorm));
    }
}