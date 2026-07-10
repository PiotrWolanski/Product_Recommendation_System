package pl.pwola.recommendation.nlp;

import opennlp.tools.tokenize.SimpleTokenizer;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OpenNlpTokenizerService {

    private final SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;

    public List<String> tokenize(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }

        return Arrays.asList(tokenizer.tokenize(text));
    }
}