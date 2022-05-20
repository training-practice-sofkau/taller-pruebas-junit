package org.sofka;

import org.sofka.models.Character;
import org.sofka.repositories.MarvelComicsRepository;
import org.sofka.services.MarvelComicsService;
import org.sofka.utilities.MyLogger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final String MARVEL_PRIVATE_KEY = "marvel.privateKey";
    private static final String MARVEL_PUBLIC_KEY = "marvel.publicKey";
    private static final String MARVEL_TS = "marvel.ts";
    private static final String MARVEL_URL_BASE = "marvel.urlBase";

    /**
     * @param args {String[]} Arreglo de argumentos
     */
    public static void main(String[] args) {
        try (InputStream propFile = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            Logger logger = MyLogger.getInstance();
            Properties properties = new Properties();
            MarvelComicsRepository repository = new MarvelComicsRepository();

            if (propFile == null) {
                logger.severe("\033[0;31m");
                logger.severe("Sorry, unable to find config.properties" + "\033[0m");
                return;
            }

            properties.load(propFile);
            MarvelComicsService service = new MarvelComicsService(
                    repository,
                    properties.getProperty(MARVEL_URL_BASE),
                    properties.getProperty(MARVEL_TS),
                    properties.getProperty(MARVEL_PUBLIC_KEY),
                    properties.getProperty(MARVEL_PRIVATE_KEY));

            Character character = service.getCharacter();
            logger.log(Level.INFO, "Objeto: {0}", character);

        } catch (IOException | RuntimeException exception) {
            exception.printStackTrace();
        }
    }
}