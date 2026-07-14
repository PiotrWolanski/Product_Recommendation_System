# Product Recommendation NLP

System rekomendacji produktów z obsługą zapytań tekstowych w języku naturalnym.

Projekt został przygotowany jako część pracy inżynierskiej pt.:

**„Projekt i implementacja systemu rekomendacji produktów z obsługą zapytań tekstowych przy użyciu NLP”**

Aplikacja umożliwia wyszukiwanie produktów na podstawie zapytania tekstowego, filtrów strukturalnych oraz algorytmu rekomendacji wykorzystującego techniki NLP, OpenNLP, TF-IDF i podobieństwo cosinusowe.

---

## Główne funkcje

- wyszukiwanie produktów za pomocą zapytań tekstowych,
- analiza tekstu z użyciem Apache OpenNLP,
- tokenizacja, usuwanie stop words i normalizacja tekstu,
- rozszerzanie zapytań o synonimy,
- rozpoznawanie ograniczeń cenowych, np. `do 390 zł`,
- obliczanie podobieństwa tekstowego metodą TF-IDF,
- ranking produktów na podstawie scoringu rekomendacji,
- filtrowanie po kategorii, grupie docelowej, typie produktu i kolorze,
- automatyczny generator syntetycznej bazy około 1000 produktów,
- REST API,
- dokumentacja API przez Swagger UI,
- prosty interfejs webowy z trybem jasnym i ciemnym.

---

## Technologie

### Backend

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven

### NLP i rekomendacje

- Apache OpenNLP
- TF-IDF
- cosine similarity
- własne procedury przetwarzania tekstu
- content-based recommendation

### Narzędzia

- Git / GitHub
- Docker
- Swagger UI
- PowerShell / REST requests

---

## Wymagania

Przed uruchomieniem projektu należy mieć zainstalowane:

- Java 17 lub nowsza,
- Maven,
- Docker Desktop,
- Git.

Projekt był przygotowany dla Java 17. Jeżeli lokalnie używana jest nowsza wersja JDK, Maven nadal kompiluje projekt z ustawieniem `release 17`.

---

## Uruchomienie projektu

### 1. Sklonowanie repozytorium

```powershell
git clone https://github.com/PiotrWolanski/Product_Recommendation_System.git
cd Product_Recommendation_System/backend
