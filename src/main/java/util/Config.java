package util;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Config {
    public static final String API_TOKEN = "apiToken";

    private final JsonObject properties;

    public Config(String configFilePath) throws IOException {
        this.properties = new Gson().fromJson(Files.readString(Paths.get(configFilePath)), JsonObject.class);
    }

    public String getStringRequired(String key) {
        return this.properties.get(key).getAsString();
    }
}
