package com.kkoreng.jda.config;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public final class YamlConfiguration {

    private final Map<String, Object> data;

    private YamlConfiguration(Map<String, Object> data) {
        this.data = data;
    }

    public static Map<String, Object> loadFromResources(String resourcePath) {
        try (InputStream inputStream =
                     YamlConfiguration.class.getResourceAsStream(resourcePath)) {

            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }

            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);

            return data;

        } catch (IOException e) {
            throw new RuntimeException("Failed to load config from resource: " + resourcePath, e);
        }
    }
}