# Dokumentacja techniczna projektu


1. Cel projektu

Celem projektu jest stworzenie systemu rekomendacji produktów obsługującego zapytania tekstowe użytkownika z wykorzystaniem technik NLP.

System pozwala użytkownikowi wpisać zapytanie w języku naturalnym, np.:

```text
buty wodoodporne w góry do 390 zł
```


2. Wykorzystane technologie

Backend
* Java 17
* Spring Boot
* Spring Data JPA
* Hibernate
* PostgreSQL
* Maven

NLP i algorytm rekomendacji
* Apache OpenNLP
* TF-IDF
* cosine similarity
* własne procedury przetwarzania tekstu
* content-based recommendation

Pozostałe narzędzia
* Docker
* Git / GitHub
* Swagger UI
* REST API


3. Architektura aplikacji

Projekt wykorzystuje architekturę warstwową:

UI
↓
REST Controller
↓
Service
↓
Repository
↓
PostgreSQL

Główne warstwy systemu:

    * warstwa prezentacji — index.html,
    * warstwa API — kontrolery REST,
    * warstwa logiki biznesowej — serwisy,
    * warstwa dostępu do danych — Spring Data JPA,
    * warstwa bazy danych — PostgreSQL.


4. Główne moduły systemu

4.1. Moduł product

Pakiet:
pl.pwola.recommendation.product
Odpowiada za obsługę produktów oraz generowanie danych testowych.

Najważniejsze klasy:

* Product
* ProductRepository
* ProductService
* ProductController
* ProductRequest
* ProductResponse
* ProductDataSeeder

4.2. Moduł nlp

Pakiet:
pl.pwola.recommendation.nlp
Odpowiada za analizę tekstu użytkownika.

Najważniejsze klasy:

* OpenNlpTokenizerService
* TextProcessingService
* StopWordsProvider
* SynonymService
* PriceExtractionService
* PriceConstraint
* TfidfSimilarityService

Moduł NLP realizuje:

* tokenizację tekstu,
* normalizację tokenów,
* usuwanie znaków specjalnych,
* usuwanie stop words,
* rozszerzanie zapytania o synonimy,
* rozpoznawanie ograniczeń cenowych,
* obliczanie podobieństwa TF-IDF.

4.3. Moduł recommendation

Pakiet:
pl.pwola.recommendation.recommendation
Odpowiada za wyznaczanie rankingu rekomendowanych produktów.

Najważniejsze klasy:

RecommendationController
RecommendationService
RecommendationRequest
RecommendationResponse
ScoringService


5. Model danych

Główna encja systemu to Product.

Najważniejsze pola:
| Pole           | Opis                                            |
| -------------- | ----------------------------------------------- |
| `id`           | identyfikator produktu                          |
| `name`         | nazwa produktu                                  |
| `description`  | opis produktu                                   |
| `mainCategory` | główna kategoria, np. obuwie, odzież, akcesoria |
| `subCategory`  | podkategoria produktu                           |
| `productType`  | rodzaj produktu                                 |
| `color`        | kolor produktu                                  |
| `targetGender` | grupa docelowa: męskie, damskie, unisex         |
| `waterproof`   | informacja, czy produkt jest wodoodporny        |
| `season`       | sezonowość produktu                             |
| `price`        | cena                                            |
| `rating`       | ocena produktu                                  |
| `reviewsCount` | liczba opinii                                   |
| `url`          | adres URL produktu                              |


6. Przepływ rekomendacji

Proces rekomendacji przebiega następująco:

Zapytanie użytkownika
↓
Tokenizacja OpenNLP
↓
Normalizacja tekstu
↓
Usunięcie stop words
↓
Rozpoznanie ceny
↓
Rozszerzenie zapytania o synonimy
↓
Filtrowanie produktów
↓
Obliczenie podobieństwa TF-IDF
↓
Scoring końcowy
↓
Ranking produktów
↓
Odpowiedź API / widok UI


7. Tokenizacja OpenNLP

Do tokenizacji zapytania użytkownika wykorzystywana jest biblioteka Apache OpenNLP.

Za integrację odpowiada klasa:
OpenNlpTokenizerService

Przykład zapytania:
buty wodoodporne w góry do 390 zł

Po tokenizacji, normalizacji i usunięciu stop words system może otrzymać tokeny:
* buty
* wodoodporne
* góry
Liczby i frazy cenowe są obsługiwane osobno przez moduł rozpoznawania ceny.


8. Rozpoznawanie ceny z tekstu

Za rozpoznawanie ceny odpowiada klasa:
PriceExtractionService

System obsługuje przykładowe frazy:
do 390 zł
poniżej 400 zł
max 500 zł
maksymalnie 300 zł
od 100 zł
powyżej 200 zł

Przykład:
buty wodoodporne do 390 zł

System rozpoznaje:
maxPrice = 390
Następnie produkty droższe niż wskazana wartość są odrzucane przed obliczeniem rankingu.


9. TF-IDF i podobieństwo cosinusowe

System wykorzystuje metodę TF-IDF do porównywania zapytania użytkownika z opisami produktów.

TF-IDF pozwala określić wagę słów w tekście. Słowa często występujące w wielu dokumentach mają mniejszą wagę, natomiast słowa bardziej charakterystyczne dla danego produktu mają większe znaczenie.

W projekcie:

1. zapytanie użytkownika jest przekształcane do wektora TF-IDF,
2. opisy produktów są przekształcane do wektorów TF-IDF,
3. system oblicza podobieństwo cosinusowe między wektorami,
4. wynik podobieństwa jest uwzględniany w końcowym scoringu rekomendacji.

W odpowiedzi API wynik jest dostępny jako:
tfidfSimilarity


10. Scoring rekomendacji

Końcowy wynik rekomendacji uwzględnia:

* podobieństwo TF-IDF,
* dopasowanie słów kluczowych i synonimów,
* ocenę produktu,
* liczbę opinii.

Przykładowe wagi:
50% podobieństwo TF-IDF
30% dopasowanie tekstowe
12% ocena produktu
8% liczba opinii
Dzięki temu system nie działa wyłącznie jako prosty filtr, lecz tworzy ranking produktów na podstawie wielu czynników.


11. Generator danych

Za generowanie danych odpowiada klasa:
ProductDataSeeder

Generator tworzy syntetyczną bazę produktów obejmującą:

* obuwie,
* odzież,
* akcesoria,
* różne kolory,
* różne grupy docelowe,
* różne sezony,
* różne ceny,
* oceny i liczby opinii.

Dzięki temu system może być testowany na większym zbiorze produktów bez ręcznego przygotowywania dużego pliku SQL.


12. REST API

System udostępnia REST API.

Najważniejsze endpointy:
| Metoda | Endpoint               | Opis                       |
| ------ | ---------------------- | -------------------------- |
| `GET`  | `/api/products`        | pobranie listy produktów   |
| `GET`  | `/api/products/{id}`   | pobranie produktu po ID    |
| `POST` | `/api/products`        | dodanie produktu           |
| `POST` | `/api/recommendations` | wygenerowanie rekomendacji |

Swagger UI jest dostępny pod adresem:
http://localhost:8080/swagger-ui.html


13. Interfejs użytkownika

Interfejs webowy jest dostępny pod adresem:
http://localhost:8080

UI umożliwia:

* wpisanie zapytania tekstowego,
* wybór kategorii produktu,
* wybór grupy docelowej,
* wybór rodzaju produktu,
* opcjonalny wybór koloru,
* przełączanie trybu jasnego i ciemnego,
* przeglądanie wyników wraz z uzasadnieniem rekomendacji.


14. Uruchomienie lokalne
Uruchomienie PostgreSQL

Jeżeli kontener jeszcze nie istnieje:
docker run --name nlp-postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=product_recommendation_nlp -p 5432:5432 -d postgres:16

Jeżeli kontener już istnieje:
docker start nlp-postgres

Uruchomienie backendu
cd backend
mvn clean spring-boot:run

Adres aplikacji
http://localhost:8080

Swagger UI
http://localhost:8080/swagger-ui.html


15. Znaczenie projektu

Projekt obejmuje najważniejsze elementy systemu rekomendacji produktów:

* analizę zapytań tekstowych,
* przetwarzanie języka naturalnego,
* ranking produktów,
* bazę danych,
* REST API,
* interfejs użytkownika,
* dokumentację,
* generator danych testowych.

System może zostać opisany jako content-based recommendation system wykorzystujący NLP do analizy zapytań użytkownika.