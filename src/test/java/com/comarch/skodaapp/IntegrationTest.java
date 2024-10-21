package com.comarch.skodaapp;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Domyślnie testy są uruchamiane z użyciem bazy danych H2, która jest bazą danych trzymaną w pamięci.
 * Aby korzystać z bazy danych PostgreSQL w testach twożonej z użyciem biblioteki Testcontainers,
 * należy w poniższej adnotacji {@code @ActiveProfiles} zastąpić 'h2' wartością 'postgres'
 * czyli będzie: {@code @ActiveProfiles({"test", "postgres"})}.
 * Należy również odkomentować linijkę:
 * {@code @ContextConfiguration(initializers = TestcontainersInitializer.class)}.
 * To umożliwi korzystanie z inicjalizatora Testcontainers, który konfiguruje środowisko testowe
 * do korzystania z bazy danych PostgreSQL.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test, h2"})
//@ContextConfiguration(initializers = TestcontainersInitializer.class)
public @interface IntegrationTest {
}