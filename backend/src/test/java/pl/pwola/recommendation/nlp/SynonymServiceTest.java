package pl.pwola.recommendation.nlp;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SynonymServiceTest {

    private final SynonymService synonymService = new SynonymService();

    @Test
    void shouldReturnOriginalTokenAsSynonym() {
        Set<String> synonyms = synonymService.getSynonyms("buty");

        assertTrue(synonyms.contains("buty"));
    }

    @Test
    void shouldExpandKnownProductSynonym() {
        Set<String> synonyms = synonymService.getSynonyms("buty");

        assertTrue(synonyms.contains("obuwie"));
    }

    @Test
    void shouldExpandTokenSetWithSynonyms() {
        Set<String> expandedTokens = synonymService.expandTokens(Set.of("buty"));

        assertTrue(expandedTokens.contains("buty"));
        assertTrue(expandedTokens.contains("obuwie"));
    }

    @Test
    void shouldReturnUnknownTokenWithoutRemovingIt() {
        Set<String> synonyms = synonymService.getSynonyms("unikalnetestoweslowo");

        assertTrue(synonyms.contains("unikalnetestoweslowo"));
    }
}