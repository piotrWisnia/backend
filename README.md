# Skoda App

## Opis aplikacji

Skoda App

### Aktualne funkcjonalności:

1. Możliwość dodania nowego klienta (Customer).
2. Wyświetlanie informacji o konkretnym kliencie wraz z jego saldem punktowym.
3. Dodawanie zamówienia w tym naliczanie punktów z przelicznikiem 1 złoty = 1 punkt.

Pobieranie Klienta: GET http://localhost:8080/customers/{id}

Dodawanie Klienta: POST http://localhost:8080/customers
```json
{
  "name": "Arnold Boczek",
  "email": "boczek@gmail.com"
}
  ```
Dodawanie zamówienia: POST http://localhost:8080/orders

  ```json
  {
  "customerId": 1,
  "amount": 100.15
  }
  ```

## Baza danych (zmiana z H2 na PostgreSQL) - Opcjonalne
Aplikacja domyślnie korzysta z bazy danych H2, która przechowuje dane w pamięci (po każdym uruchomieniu aplikacji dane są usuwane).
Aplikacja posiada również konfigurację umożliwiającą korzystanie z bazy danych PostgreSQL.
Jeśli chcesz zmienić bazę danych na PostgreSQL, wystarczy, że w pliku [application.properties](./src/main/resources/application.properties) zmienisz profil z 'h2' na 'postgres'.
Dodatkowo, do aplikacji dołączony jest plik [docker-compose](./src/main/resources/docker-compose.yml), który umożliwia uruchomienie bazy danych PostgreSQL z dostosowaną konfiguracją.

W testach domyślnie również wykorzystywana jest baza danych H2.
Jeśli chcesz, aby w testach używana była baza danych PostgreSQL, tworzona za pomocą Testcontainers, możesz to łatwo zmienić.
Instrukcja, jak to zrobić, znajduje się w dokumentacji Javadoc w klasie [IntegrationTest](./src/test/java/com/lojalnik/IntegrationTest.java).

