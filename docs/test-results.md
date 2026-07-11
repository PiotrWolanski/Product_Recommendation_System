# Wyniki testów

## Data wykonania

Testy zostały wykonane lokalnie za pomocą Maven.

Komenda:

```powershell
cd backend
mvn test
```


Zakres testów

W projekcie przygotowano testy jednostkowe dla kluczowych elementów systemu:
* PriceExtractionServiceTest — testy rozpoznawania ograniczeń cenowych z zapytań tekstowych,
* TextProcessingServiceTest — testy tokenizacji, usuwania stop words i czyszczenia tekstu,
* SynonymServiceTest — testy obsługi synonimów,
* TfidfSimilarityServiceTest — testy obliczania podobieństwa TF-IDF,
* ScoringServiceTest — testy końcowego scoringu rekomendacji.

Podsumowanie

Wszystkie testy zakończyły się wynikiem pozytywnym.

Łącznie wykonano:
18 testów jednostkowych
0 błędów
0 niepowodzeń
0 pominiętych testów

Wynik potwierdza poprawność działania najważniejszych komponentów systemu rekomendacji produktów.