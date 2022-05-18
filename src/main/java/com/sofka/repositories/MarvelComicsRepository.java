package com.sofka.repositories;

import java.io.IOException;

import com.sofka.repositories.interfaces.InterfaceMarvelComicsRepository;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MarvelComicsRepository implements InterfaceMarvelComicsRepository {

    @Override
    public Response findAll(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        return client.newCall(request).execute();
    }

}
