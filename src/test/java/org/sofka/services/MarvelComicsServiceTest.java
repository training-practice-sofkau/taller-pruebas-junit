package org.sofka.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.sofka.Main;
import org.sofka.models.Character;
import org.sofka.repositories.MarvelComicsRepository;
import org.sofka.utilities.MyLogger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


class MarvelComicsServiceTest {
    private static final String MARVEL_PRIVATE_KEY = "marvel.privateKey";
    private static final String MARVEL_PUBLIC_KEY = "marvel.publicKey";
    private static final String MARVEL_TS = "marvel.ts";
    private static final String MARVEL_URL_BASE = "marvel.urlBase";
    private String privateKey;
    private String publicKey;
    private String ts;
    private String urlBase;

    public MarvelComicsServiceTest() {
        try (InputStream propFile = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            Logger logger = MyLogger.getInstance();
            Properties properties = new Properties();
            if (propFile == null) {
                logger.severe("\033[0;31m");
                logger.severe("Sorry, unable to find config.properties" + "\033[0m");
                return;
            }
            properties.load(propFile);
            urlBase = properties.getProperty(MARVEL_URL_BASE);
            ts = properties.getProperty(MARVEL_TS);
            privateKey = properties.getProperty(MARVEL_PRIVATE_KEY);
            publicKey = properties.getProperty(MARVEL_PUBLIC_KEY);
        } catch (IOException | RuntimeException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    @DisplayName("Personajes del universo Marvel Comics")
    void getCharacterMain() {
        MarvelComicsRepository repository = new MarvelComicsRepository();
        MarvelComicsService service = new MarvelComicsService(repository, urlBase, ts, publicKey, privateKey);

        Character character = service.getCharacter();

        assertNotNull(character);
        assertEquals("200", character.getCode());
    }

    @Test
    @DisplayName("Personajes del universo Marvel Comics con Mockito")
    void getCharacterMainMockito() {
        MarvelComicsRepository repository = Mockito.mock(MarvelComicsRepository.class);
        MarvelComicsService service = new MarvelComicsService(repository, urlBase, ts, publicKey, privateKey);

        String characterSimulado = "{\"code\":200,\"status\":\"Ok\",\"copyright\":\"© 2022 MARVEL\"," +
                "\"attributionText\":\"Data provided by Marvel. © 2022 MARVEL\",\"attributionHTML\":" +
                "\"<a href=\\\"http://marvel.com\\\">Data provided by Marvel. © 2022 MARVEL</a>\"," +
                "\"etag\":\"02ead2609565d7bbb61c742dea6b7c3e1cd0f7a4\"}";

        when(repository.findAll(service.getUrl())).thenReturn(characterSimulado);
        Character character = service.getCharacter();

        assertEquals("200", character.getCode());
    }

    @Test
    @DisplayName("Timeout solicitud a dominio incorrecto o caido")
    void getCharacterTimeout() {
        String urlBase = "https://gateway.marvel.co";
        MarvelComicsRepository repository = new MarvelComicsRepository();
        MarvelComicsService service = new MarvelComicsService(repository, urlBase, ts, publicKey, privateKey);

        Exception exception = assertThrows(
                RuntimeException.class,
                () -> {
                    Character character = service.getCharacter();
                }
        );

        assertEquals("Read timed out", exception.getMessage());
    }
}