package pl.pwola.recommendation.nlp;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SynonymService {

    private final Map<String, Set<String>> synonyms = new HashMap<>();

    public SynonymService() {
        addGroup("buty", "but", "obuwie", "buciki");

        addGroup(
                "wodoodporne", "wodoodporny", "wodoodporna",
                "nieprzemakalne", "nieprzemakalny", "waterproof",
                "membrana", "membraną", "deszcz"
        );

        addGroup(
                "góry", "gory", "górach", "gorach", "górami", "gorami",
                "górskie", "gorskie", "górski", "gorski",
                "trekking", "trekkingowe", "trekkingowy",
                "outdoor", "outdoorowe", "outdoorowy",
                "szlak", "szlaki", "szlaku", "teren", "terenowe", "terenowy"
        );

        addGroup(
                "chodzenie", "chodzenia", "chodzić", "chodzic",
                "spacer", "spacery", "spacerów", "spacerow",
                "wędrówka", "wedrowka", "wędrówki", "wedrowki",
                "turystyka", "turystyczne", "turystycznych"
        );

        addGroup(
                "bieganie", "biegania", "biegowe", "biegowy",
                "run", "running", "trail", "trailowe", "trailowy"
        );

        addGroup(
                "zimowe", "zimowy", "zima", "śnieg", "snieg",
                "ocieplane", "ocieplenie", "ciepłe", "cieple", "mróz", "mroz"
        );

        addGroup(
                "eleganckie", "elegancki", "formalny", "formalne",
                "biznesowe", "biznesowy", "skórzane", "skorzane", "skóra", "skora"
        );

        addGroup(
                "randka", "randkę", "randke", "wyjście", "wyjscie",
                "impreza", "wieczór", "wieczor", "eleganckie"
        );

        addGroup(
                "szpilki", "szpilka", "obcasy", "obcas", "heels",
                "czółenka", "czolenka"
        );

        addGroup(
                "sneakersy", "sneakers", "trampki", "casual", "miejskie", "codzienne"
        );

        addGroup(
                "botki", "kozaki", "boots", "jesienne", "jesień", "jesien"
        );

        addGroup(
                "sandały", "sandaly", "sandał", "sandal", "letnie", "lato"
        );

        addGroup(
                "deszcz", "deszczowe", "błoto", "bloto",
                "kalosze", "gumowe", "gumowy", "ogród", "ogrod", "ogrodowe"
        );

        addGroup(
                "robocze", "roboczy", "praca", "ochronne",
                "bezpieczne", "safety", "wzmocnione", "nosek"
        );

        addGroup(
                "damskie", "damski", "kobieta", "kobiety", "women", "female"
        );

        addGroup(
                "męskie", "meskie", "męski", "meski", "mężczyzna", "mezczyzna",
                "mężczyzny", "mezczyzny", "men", "male"
        );

        addGroup(
                "unisex", "uniwersalne", "uniwersalny"
        );

        addGroup(
                "czarne", "czarny", "black"
        );

        addGroup(
                "brązowe", "brazowe", "brązowy", "brazowy", "brown"
        );

        addGroup(
                "białe", "biale", "biały", "bialy", "white"
        );

        addGroup(
                "beżowe", "bezowe", "beżowy", "bezowy", "beige"
        );

        addGroup(
                "czerwone", "czerwony", "red"
        );

        addGroup(
                "niebieskie", "niebieski", "blue", "granatowe", "granatowy"
        );
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