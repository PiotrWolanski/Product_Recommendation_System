package pl.pwola.recommendation.nlp;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PriceExtractionService {

    private static final Pattern MAX_PRICE_PATTERN = Pattern.compile(
            "(do|poniżej|ponizej|max|maksymalnie|nie więcej niż|nie wiecej niz)\\s*(\\d+)",
            Pattern.CASE_INSENSITIVE
    );

    private static final Pattern MIN_PRICE_PATTERN = Pattern.compile(
            "(od|powyżej|powyzej|min|minimum)\\s*(\\d+)",
            Pattern.CASE_INSENSITIVE
    );

    public PriceConstraint extractPriceConstraint(String query) {
        if (query == null || query.isBlank()) {
            return new PriceConstraint(null, null);
        }

        String normalizedQuery = query.toLowerCase(Locale.ROOT);

        BigDecimal minPrice = extractPrice(normalizedQuery, MIN_PRICE_PATTERN);
        BigDecimal maxPrice = extractPrice(normalizedQuery, MAX_PRICE_PATTERN);

        return new PriceConstraint(minPrice, maxPrice);
    }

    private BigDecimal extractPrice(String query, Pattern pattern) {
        Matcher matcher = pattern.matcher(query);

        if (matcher.find()) {
            return new BigDecimal(matcher.group(2));
        }

        return null;
    }
}