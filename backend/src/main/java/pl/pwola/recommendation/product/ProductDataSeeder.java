package pl.pwola.recommendation.product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Component
public class ProductDataSeeder implements CommandLineRunner {

    private static final int TARGET_PRODUCTS = 1000;

    private final ProductRepository productRepository;

    public ProductDataSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        long existingProducts = productRepository.count();

        if (existingProducts >= TARGET_PRODUCTS) {
            System.out.println("ProductDataSeeder: database already contains " + existingProducts + " products.");
            return;
        }

        productRepository.deleteAll();

        List<Product> generatedProducts = generateProducts();

        productRepository.saveAll(generatedProducts);

        System.out.println("ProductDataSeeder: generated " + generatedProducts.size() + " synthetic products.");
    }

    private List<Product> generateProducts() {
        List<Product> products = new ArrayList<>();
        List<ProductTemplate> templates = createProductTemplates();

        int variant = 1;

        for (ProductTemplate template : templates) {
            for (String gender : template.genders()) {
                for (String color : colors()) {
                    for (String season : template.seasons()) {
                        String model = selectModel(template, gender, color, season, variant);
                        boolean waterproof = determineWaterproof(template, season, variant);

                        Product product = new Product(
                                buildName(template, gender, color, model),
                                buildDescription(template, gender, color, season, waterproof, model),
                                template.mainCategory(),
                                template.subCategory(),
                                template.productType(),
                                color,
                                gender,
                                waterproof,
                                season,
                                generatePrice(template, gender, color, season, variant),
                                generateRating(template, gender, color, season, variant),
                                generateReviewsCount(template, gender, color, season, variant),
                                "https://example.com/products/generated-" + variant
                        );

                        products.add(product);
                        variant++;
                    }
                }
            }
        }

        Collections.shuffle(products, new Random(42));

        if (products.size() > TARGET_PRODUCTS) {
            return new ArrayList<>(products.subList(0, TARGET_PRODUCTS));
        }

        return products;
    }

    private List<ProductTemplate> createProductTemplates() {
        return List.of(
                new ProductTemplate(
                        "obuwie",
                        "outdoor",
                        "buty trekkingowe",
                        List.of("męskie", "damskie", "unisex"),
                        List.of("całoroczne", "jesienne", "zimowe"),
                        true,
                        219,
                        599,
                        List.of("góry", "szlak", "trekking", "wędrówka", "teren", "outdoor")
                ),
                new ProductTemplate(
                        "obuwie",
                        "miejskie",
                        "sneakersy",
                        List.of("męskie", "damskie", "unisex"),
                        List.of("całoroczne", "letnie"),
                        false,
                        149,
                        399,
                        List.of("miasto", "codzienne chodzenie", "casual", "spacer", "styl miejski")
                ),
                new ProductTemplate(
                        "obuwie",
                        "miejskie",
                        "trampki",
                        List.of("męskie", "damskie", "unisex"),
                        List.of("letnie", "całoroczne"),
                        false,
                        99,
                        249,
                        List.of("miasto", "szkoła", "spacer", "casual", "codzienne użytkowanie")
                ),
                new ProductTemplate(
                        "obuwie",
                        "letnie",
                        "sandały",
                        List.of("męskie", "damskie", "unisex"),
                        List.of("letnie"),
                        false,
                        89,
                        249,
                        List.of("lato", "wakacje", "spacer", "ciepłe dni", "codzienne chodzenie")
                ),
                new ProductTemplate(
                        "obuwie",
                        "deszczowe",
                        "kalosze",
                        List.of("męskie", "damskie", "unisex"),
                        List.of("jesienne", "całoroczne"),
                        true,
                        79,
                        229,
                        List.of("deszcz", "błoto", "ogród", "mokry teren", "praca w terenie")
                ),
                new ProductTemplate(
                        "obuwie",
                        "sportowe",
                        "buty biegowe",
                        List.of("męskie", "damskie", "unisex"),
                        List.of("całoroczne", "letnie"),
                        false,
                        179,
                        549,
                        List.of("bieganie", "trening", "sport", "asfalt", "aktywność fizyczna")
                ),
                new ProductTemplate(
                        "obuwie",
                        "zimowe",
                        "buty zimowe",
                        List.of("męskie", "damskie", "unisex"),
                        List.of("zimowe"),
                        true,
                        199,
                        499,
                        List.of("zima", "śnieg", "mróz", "ciepło", "codzienne chodzenie zimą")
                ),
                new ProductTemplate(
                        "obuwie",
                        "robocze",
                        "buty robocze",
                        List.of("męskie", "unisex"),
                        List.of("całoroczne"),
                        true,
                        159,
                        399,
                        List.of("praca", "ochrona", "bezpieczeństwo", "wzmocniony nosek", "magazyn")
                ),
                new ProductTemplate(
                        "obuwie",
                        "eleganckie",
                        "półbuty",
                        List.of("męskie"),
                        List.of("całoroczne"),
                        false,
                        199,
                        599,
                        List.of("praca", "garnitur", "spotkanie biznesowe", "formalna okazja", "randka")
                ),
                new ProductTemplate(
                        "obuwie",
                        "eleganckie",
                        "szpilki",
                        List.of("damskie"),
                        List.of("całoroczne"),
                        false,
                        129,
                        399,
                        List.of("randka", "wieczorne wyjście", "impreza", "elegancka stylizacja", "formalna okazja")
                ),
                new ProductTemplate(
                        "obuwie",
                        "eleganckie",
                        "czółenka",
                        List.of("damskie"),
                        List.of("całoroczne"),
                        false,
                        139,
                        349,
                        List.of("biuro", "praca", "elegancka stylizacja", "spotkanie", "codzienna elegancja")
                ),
                new ProductTemplate(
                        "obuwie",
                        "jesienne",
                        "botki",
                        List.of("damskie"),
                        List.of("jesienne", "całoroczne"),
                        false,
                        179,
                        449,
                        List.of("jesień", "miasto", "spacer", "praca", "styl casual")
                ),
                new ProductTemplate(
                        "obuwie",
                        "zimowe",
                        "kozaki",
                        List.of("damskie"),
                        List.of("zimowe"),
                        false,
                        219,
                        599,
                        List.of("zima", "mróz", "ciepło", "miasto", "elegancka stylizacja")
                ),

                new ProductTemplate(
                        "odzież",
                        "kurtki",
                        "kurtka przeciwdeszczowa",
                        List.of("męskie", "damskie", "unisex"),
                        List.of("jesienne", "wiosenne", "całoroczne"),
                        true,
                        199,
                        699,
                        List.of("deszcz", "wiatr", "góry", "trekking", "miasto", "ochrona przed wodą")
                ),
                new ProductTemplate(
                        "odzież",
                        "kurtki",
                        "kurtka zimowa",
                        List.of("męskie", "damskie", "unisex"),
                        List.of("zimowe"),
                        true,
                        249,
                        899,
                        List.of("zima", "mróz", "śnieg", "ciepło", "codzienne użytkowanie")
                ),
                new ProductTemplate(
                        "odzież",
                        "bluzy",
                        "bluza",
                        List.of("męskie", "damskie", "unisex"),
                        List.of("całoroczne", "jesienne"),
                        false,
                        89,
                        299,
                        List.of("codzienne noszenie", "miasto", "sport", "spacer", "casual")
                ),
                new ProductTemplate(
                        "odzież",
                        "koszulki",
                        "koszulka",
                        List.of("męskie", "damskie", "unisex"),
                        List.of("letnie", "całoroczne"),
                        false,
                        39,
                        159,
                        List.of("lato", "trening", "codzienne użytkowanie", "sport", "casual")
                ),
                new ProductTemplate(
                        "odzież",
                        "spodnie",
                        "spodnie trekkingowe",
                        List.of("męskie", "damskie", "unisex"),
                        List.of("całoroczne", "jesienne"),
                        true,
                        149,
                        449,
                        List.of("góry", "trekking", "szlak", "outdoor", "wędrówka")
                ),
                new ProductTemplate(
                        "odzież",
                        "spodnie",
                        "jeansy",
                        List.of("męskie", "damskie", "unisex"),
                        List.of("całoroczne"),
                        false,
                        99,
                        349,
                        List.of("miasto", "codzienne noszenie", "casual", "praca", "spotkanie")
                ),
                new ProductTemplate(
                        "odzież",
                        "sukienki",
                        "sukienka",
                        List.of("damskie"),
                        List.of("letnie", "całoroczne"),
                        false,
                        99,
                        499,
                        List.of("randka", "wieczorne wyjście", "elegancka okazja", "impreza", "lato")
                ),
                new ProductTemplate(
                        "odzież",
                        "spódnice",
                        "spódnica",
                        List.of("damskie"),
                        List.of("letnie", "całoroczne"),
                        false,
                        79,
                        299,
                        List.of("lato", "praca", "elegancka stylizacja", "miasto", "spotkanie")
                ),
                new ProductTemplate(
                        "odzież",
                        "sportowe",
                        "legginsy sportowe",
                        List.of("damskie", "unisex"),
                        List.of("całoroczne"),
                        false,
                        79,
                        249,
                        List.of("trening", "siłownia", "bieganie", "sport", "aktywność fizyczna")
                ),
                new ProductTemplate(
                        "odzież",
                        "outdoor",
                        "softshell",
                        List.of("męskie", "damskie", "unisex"),
                        List.of("jesienne", "całoroczne"),
                        true,
                        179,
                        499,
                        List.of("wiatr", "góry", "trekking", "outdoor", "spacer")
                ),

                new ProductTemplate(
                        "akcesoria",
                        "plecaki",
                        "plecak trekkingowy",
                        List.of("męskie", "damskie", "unisex"),
                        List.of("całoroczne"),
                        true,
                        129,
                        499,
                        List.of("góry", "szlak", "trekking", "wycieczka", "outdoor")
                ),
                new ProductTemplate(
                        "akcesoria",
                        "plecaki",
                        "plecak miejski",
                        List.of("męskie", "damskie", "unisex"),
                        List.of("całoroczne"),
                        false,
                        79,
                        299,
                        List.of("miasto", "praca", "szkoła", "codzienne użytkowanie", "laptop")
                ),
                new ProductTemplate(
                        "akcesoria",
                        "czapki",
                        "czapka",
                        List.of("męskie", "damskie", "unisex"),
                        List.of("zimowe", "jesienne"),
                        false,
                        29,
                        149,
                        List.of("zima", "mróz", "ciepło", "spacer", "codzienne użytkowanie")
                ),
                new ProductTemplate(
                        "akcesoria",
                        "rękawiczki",
                        "rękawiczki",
                        List.of("męskie", "damskie", "unisex"),
                        List.of("zimowe"),
                        false,
                        39,
                        199,
                        List.of("zima", "mróz", "śnieg", "ciepło", "spacer")
                ),
                new ProductTemplate(
                        "akcesoria",
                        "skarpety",
                        "skarpety trekkingowe",
                        List.of("męskie", "damskie", "unisex"),
                        List.of("całoroczne", "zimowe"),
                        false,
                        19,
                        99,
                        List.of("góry", "trekking", "długie chodzenie", "wędrówka", "komfort")
                ),
                new ProductTemplate(
                        "akcesoria",
                        "torby",
                        "torba sportowa",
                        List.of("męskie", "damskie", "unisex"),
                        List.of("całoroczne"),
                        false,
                        79,
                        299,
                        List.of("siłownia", "trening", "podróż", "sport", "codzienne użytkowanie")
                ),
                new ProductTemplate(
                        "akcesoria",
                        "torby",
                        "torebka",
                        List.of("damskie"),
                        List.of("całoroczne"),
                        false,
                        99,
                        699,
                        List.of("miasto", "randka", "elegancka stylizacja", "praca", "spotkanie")
                ),
                new ProductTemplate(
                        "akcesoria",
                        "sportowe",
                        "komin sportowy",
                        List.of("męskie", "damskie", "unisex"),
                        List.of("jesienne", "zimowe"),
                        false,
                        29,
                        129,
                        List.of("bieganie", "rower", "wiatr", "zima", "sport")
                ),
                new ProductTemplate(
                        "akcesoria",
                        "sportowe",
                        "okulary sportowe",
                        List.of("męskie", "damskie", "unisex"),
                        List.of("letnie", "całoroczne"),
                        false,
                        59,
                        399,
                        List.of("rower", "bieganie", "słońce", "góry", "sport")
                )
        );
    }

    private List<String> colors() {
        return List.of(
                "czarny",
                "brązowy",
                "biały",
                "beżowy",
                "czerwony",
                "niebieski",
                "zielony",
                "szary",
                "różowy",
                "granatowy"
        );
    }

    private List<String> modelNames() {
        return List.of(
                "Aero",
                "Urban",
                "Trail",
                "Mountain",
                "Classic",
                "Storm",
                "Comfort",
                "Active",
                "Daily",
                "Prime",
                "Nova",
                "Flex",
                "Pro",
                "Lite",
                "Essential"
        );
    }

    private String selectModel(ProductTemplate template, String gender, String color, String season, int variant) {
        List<String> models = modelNames();
        int index = hashMod(models.size(), template.productType(), gender, color, season, variant);
        return models.get(index);
    }

    private String buildName(ProductTemplate template, String gender, String color, String model) {
        return capitalize(template.productType()) + " " + model + " (" + genderLabel(gender) + ", " + color + ")";
    }

    private String buildDescription(
            ProductTemplate template,
            String gender,
            String color,
            String season,
            boolean waterproof,
            String model
    ) {
        String waterproofDescription = waterproof
                ? " Produkt posiada cechy wodoodporne, nieprzemakalne i dobrze sprawdza się podczas deszczu."
                : " Produkt nie jest oznaczony jako wodoodporny.";

        return capitalize(template.productType()) + " " + model
                + " w kolorze " + color
                + ". Kategoria: " + template.mainCategory()
                + ", podkategoria: " + template.subCategory()
                + ". Produkt przeznaczony: " + genderDescription(gender)
                + ". Sezon: " + season
                + ". Zastosowanie: " + String.join(", ", template.useCases())
                + ". " + waterproofDescription;
    }

    private BigDecimal generatePrice(ProductTemplate template, String gender, String color, String season, int variant) {
        int range = template.maxPrice() - template.minPrice() + 1;
        int price = template.minPrice() + hashMod(range, template.productType(), gender, color, season, variant);

        return BigDecimal.valueOf(price)
                .add(BigDecimal.valueOf(0.99))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private Double generateRating(ProductTemplate template, String gender, String color, String season, int variant) {
        int ratingStep = hashMod(13, template.mainCategory(), template.productType(), gender, color, season, variant);
        double rating = 3.8 + ratingStep / 10.0;

        return Math.min(5.0, Math.round(rating * 10.0) / 10.0);
    }

    private Integer generateReviewsCount(ProductTemplate template, String gender, String color, String season, int variant) {
        return 5 + hashMod(296, template.subCategory(), template.productType(), gender, color, season, variant);
    }

    private boolean determineWaterproof(ProductTemplate template, String season, int variant) {
        if (!template.waterproofSupported()) {
            return false;
        }

        if ("kalosze".equals(template.productType())
                || "kurtka przeciwdeszczowa".equals(template.productType())) {
            return true;
        }

        if ("zimowe".equals(season)) {
            return hashMod(3, template.productType(), season, variant) != 0;
        }

        return hashMod(2, template.productType(), season, variant) == 0;
    }

    private int hashMod(int range, Object... values) {
        return Math.floorMod(Objects.hash(values), range);
    }

    private String genderLabel(String gender) {
        return switch (gender) {
            case "męskie" -> "mężczyzna";
            case "damskie" -> "kobieta";
            default -> "unisex";
        };
    }

    private String genderDescription(String gender) {
        return switch (gender) {
            case "męskie" -> "dla mężczyzn";
            case "damskie" -> "dla kobiet";
            default -> "unisex";
        };
    }

    private String capitalize(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }

        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    private record ProductTemplate(
            String mainCategory,
            String subCategory,
            String productType,
            List<String> genders,
            List<String> seasons,
            boolean waterproofSupported,
            int minPrice,
            int maxPrice,
            List<String> useCases
    ) {
    }
}