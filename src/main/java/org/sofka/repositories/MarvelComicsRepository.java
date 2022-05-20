package org.sofka.repositories;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.sofka.repositories.interfaces.InterfaceMarvelComicsRepository;

import java.io.IOException;

public class MarvelComicsRepository implements InterfaceMarvelComicsRepository {

    @Override
    public String findAll(String url) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response answer = client.newCall(request).execute();
            return answer.body().string();
        } catch (RuntimeException | IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }

    }

}
