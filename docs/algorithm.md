# Recommendation Algorithm

## Overview

The recommendation system compares a natural language user query with product data stored in the database.

The system uses basic NLP techniques and custom scoring logic to calculate how well each product matches the query.

## Text Processing Steps

For both user queries and product descriptions, the system performs the following operations:

1. Convert text to lowercase.
2. Remove special characters.
3. Split text into tokens.
4. Remove stop words.
5. Expand selected words using a synonym dictionary.
6. Compare query tokens with product tokens.

## Example

User query:

```text
buty wodoodporne do chodzenia po górach