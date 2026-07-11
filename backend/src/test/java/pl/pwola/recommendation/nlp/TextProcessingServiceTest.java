package pl.pwola.recommendation.nlp;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TextProcessingServiceTest {

    private final TextProcessingService textProcessingService = new TextProcessingService(
            new StopWordsProvider(),
            new SynonymService(),
            new OpenNlpTokenizerService()
    );

    @Test
    void shouldExtractImportantTokensFromQuery() {
        List<String> tokens = textProcessingService.extractImportantTokenList(
                "szukam buty wodoodporne do 390 zł"
        );

        assertTrue(tokens.contains("buty"));
        assertTrue(tokens.contains("wodoodporne"));
        assertFalse(tokens.contains("szukam"));
        assertFalse(tokens.contains("do"));
        assertFalse(tokens.contains("390"));
        assertFalse(tokens.contains("zł"));
    }

    @Test
    void shouldRemoveDuplicateTokensWhenReturningSet() {
        Set<String> tokens = textProcessingService.extractImportantTokens(
                "buty buty wodoodporne wodoodporne"
        );

        assertEquals(2, tokens.size());
        assertTrue(tokens.contains("buty"));
        assertTrue(tokens.contains("wodoodporne"));
    }

    @Test
    void shouldReturnEmptyListForBlankText() {
        List<String> tokens = textProcessingService.extractImportantTokenList("");

        assertTrue(tokens.isEmpty());
    }
}