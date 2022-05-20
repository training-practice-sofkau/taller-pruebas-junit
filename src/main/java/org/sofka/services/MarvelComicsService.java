package org.sofka.services;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import lombok.Data;
import org.sofka.models.Character;
import org.sofka.repositories.interfaces.InterfaceMarvelComicsRepository;
import org.sofka.services.interfaces.InterfaceMarvelComicsService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Servicio para consultar la API de Marvel Comics
 *
 * @author Julian Lasso <julian.lasso@sofka.com.co>
 * @version 1.0.0 18-05-2022
 */
@Data
public class MarvelComicsService implements InterfaceMarvelComicsService {

    private InterfaceMarvelComicsRepository marvelComicsRepository;
    private String url;

    /**
     * Método constructor de la clase MarvelComicsService
     *
     * @param marvelComicsRepository {InterfaceMarvelComicsRepository} Repositorio de Marvel Comics
     * @param urlBase                {String} URL base
     * @param ts                     {String} TimeOut para la consulta
     * @param publicKey              {String} Llave publica
     * @param privateKey             {String} Llave privada
     * @since 1.0.0
     */
    public MarvelComicsService(
            InterfaceMarvelComicsRepository marvelComicsRepository,
            String urlBase,
            String ts,
            String publicKey,
            String privateKey) {
        this.marvelComicsRepository = marvelComicsRepository;
        String find = "/v1/public/characters?ts=" + ts + "&apikey=" + publicKey + "&hash=";
        this.url = urlBase + find + getHash(ts + privateKey + publicKey);
    }

    /**
     * Consulta todos los personajes del universo Marvel Comics
     *
     * @return {Character} Objeto con la información de los personajes
     * @since 1.0.0
     */
    @Override
    public Character getCharacter() {
        try {
            String response = marvelComicsRepository.findAll(url);
            return new Gson().fromJson(response, Character.class);
        } catch (JsonSyntaxException | JsonIOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    /**
     * Crea un hash en MD5
     *
     * @param message {String} Mensaje base para crear el hash
     * @return {String} devuelve el hash creado
     * @since 1.0.0
     */
    private String getHash(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(message.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
