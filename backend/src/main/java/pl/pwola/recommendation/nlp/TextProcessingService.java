package pl.pwola.recommendation.nlp;

import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TextProcessingService {

    private final StopWordsProvider stopWordsProvider;
    private final SynonymService synonymService;
    private final OpenNlpTokenizerService openNlpTokenizerService;

    public TextProcessingService(
            StopWordsProvider stopWordsProvider,
            SynonymService synonymService,
            OpenNlpTokenizerService openNlpTokenizerService
    ) {
        this.stopWordsProvider = stopWordsProvider;
        this.synonymService = synonymService;
        this.openNlpTokenizerService = openNlpTokenizerService;
    }

    public Set<String> extractImportantTokens(String text) {
        if (text == null || text.isBlank()) {
            return Set.of();
        }

        String normalizedText = normalizeText(text);

        return openNlpTokenizerService.tokenize(normalizedText)
                .stream()
                .map(this::cleanToken)
                .filter(token -> token.length() > 1)
                .filter(token -> !isNumber(token))
                .filter(token -> !stopWordsProvider.getStopWords().contains(token))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Set<String> extractExpandedTokens(String text) {
        Set<String> importantTokens = extractImportantTokens(text);
        return synonymService.expandTokens(importantTokens);
    }

    private String normalizeText(String text) {
        return text
                .toLowerCase(Locale.ROOT)
                .replace("-", " ")
                .replace("/", " ")
                .replace("\\", " ");
    }

    private String cleanToken(String token) {
        if (token == null) {
            return "";
        }

        return token
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^\\p{L}\\p{N}]", "")
                .trim();
    }

    private boolean isNumber(String token) {
        return token.matches("\\d+");
    }
}