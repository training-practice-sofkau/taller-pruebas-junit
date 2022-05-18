package com.sofka.repositories.interfaces;

import java.io.IOException;

import okhttp3.Response;

public interface InterfaceMarvelComicsRepository {
    Response findAll(String url)  throws IOException;
}
