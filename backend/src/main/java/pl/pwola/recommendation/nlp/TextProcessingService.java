package pl.pwola.recommendation.nlp;

import opennlp.tools.tokenize.SimpleTokenizer;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TextProcessingService {

    private final StopWordsProvider stopWordsProvider;
    private final SynonymService synonymService;

    public TextProcessingService(
            StopWordsProvider stopWordsProvider,
            SynonymService synonymService
    ) {
        this.stopWordsProvider = stopWordsProvider;
        this.synonymService = synonymService;
    }

    public Set<String> extractImportantTokens(String text) {
        if (text == null || text.isBlank()) {
            return Set.of();
        }

        String normalizedText = normalizeText(text);
        String[] tokens = SimpleTokenizer.INSTANCE.tokenize(normalizedText);

        return Arrays.stream(tokens)
                .map(String::trim)
                .filter(token -> !token.isBlank())
                .filter(token -> token.length() > 1)
                .filter(token -> !stopWordsProvider.isStopWord(token))
                .collect(Collectors.toSet());
    }

    public Set<String> extractExpandedTokens(String text) {
        Set<String> importantTokens = extractImportantTokens(text);
        return synonymService.expandTokens(importantTokens);
    }

    private String normalizeText(String text) {
        return text
                .toLowerCase()
                .replaceAll("[^\\p{L}\\p{N}\\s]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }
}