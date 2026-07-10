package pl.pwola.recommendation.nlp;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class StopWordsProvider {

    private final Set<String> stopWords = Set.of(
            "i",
            "oraz",
            "a",
            "ale",
            "czy",
            "to",
            "te",
            "ten",
            "ta",
            "w",
            "we",
            "na",
            "do",
            "dla",
            "z",
            "ze",
            "po",
            "pod",
            "nad",
            "przy",
            "od",
            "o",
            "u",
            "się",
            "sie",
            "jest",
            "są",
            "sa",
            "ma",
            "mają",
            "maja",
            "potrzebuję",
            "potrzebuje",
            "szukam",
            "chcę",
            "chce",
            "żeby",
            "zeby",
            "jakieś",
            "jakies",
            "jakiś",
            "jakis"
    );

    public Set<String> getStopWords() {
        return stopWords;
    }
}