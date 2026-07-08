package pl.pwola.recommendation.nlp;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SynonymService {

    private final Map<String, Set<String>> synonyms = new HashMap<>();

    public SynonymService() {
        addGroup("buty", "obuwie", "buciki");
        addGroup("wodoodporne", "wodoodporny", "waterproof", "nieprzemakalne", "membrana");
        addGroup("góry", "gory", "górskie", "gorskie", "trekking", "trekkingowe", "outdoor", "szlak", "szlaki");
        addGroup("chodzenie", "chodzenia", "spacer", "spacery", "wędrówka", "wedrowka", "wędrówki", "wedrowki");
        addGroup("bieganie", "biegania", "biegowe", "run", "running");
        addGroup("zimowe", "zima", "śnieg", "snieg", "ocieplane", "ciepłe", "cieple");
        addGroup("eleganckie", "formalny", "formalne", "biznesowe", "skórzane", "skorzane");
        addGroup("deszcz", "błoto", "bloto", "kalosze", "gumowe");
    }

    private void addGroup(String... words) {
        Set<String> group = new HashSet<>(Arrays.asList(words));

        for (String word : words) {
            synonyms.put(word, group);
        }
    }

    public Set<String> getSynonyms(String word) {
        return synonyms.getOrDefault(word, Set.of(word));
    }

    public Set<String> expandTokens(Set<String> tokens) {
        Set<String> expandedTokens = new HashSet<>();

        for (String token : tokens) {
            expandedTokens.add(token);
            expandedTokens.addAll(getSynonyms(token));
        }

        return expandedTokens;
    }
}