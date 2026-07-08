package pl.pwola.recommendation.nlp;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class StopWordsProvider {

    private final Set<String> stopWords = Set.of(
            "i", "oraz", "a", "w", "we", "z", "ze", "do", "na", "po", "pod", "nad",
            "dla", "od", "o", "u", "za", "przez", "bez", "się", "sie", "jest",
            "są", "sa", "to", "ten", "ta", "te", "tych", "który", "ktora",
            "które", "jak", "np", "np.", "lub", "albo"
    );

    public boolean isStopWord(String word) {
        return stopWords.contains(word);
    }
}