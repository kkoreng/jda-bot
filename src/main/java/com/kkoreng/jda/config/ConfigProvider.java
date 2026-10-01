package com.kkoreng.jda.config;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class ConfigProvider {

    private final Map<String, Object> configData;

    public ConfigProvider() {
        this.configData = YamlConfiguration.loadFromResources("/config.yml");
    }

    private Object get(String key) {
        return configData.get(key);
    }

    public String getString(String key) {
        Object value = get(key);
        if (value != null) {
            return String.valueOf(value);
        }
        return null;
    }

    public boolean getBoolean(String key) {
        Object value = get(key);
        if (value instanceof Boolean bool) {
            return bool;
        }
        return false;
    }

    public int getInt(String key) {
        Object value = get(key);
        if (value instanceof Integer num) {
            return num;
        }
        return 0;
    }

    public long getLong(String key) {
        Object value = get(key);
        if (value instanceof Long num) {
            return num;
        }
        return 0L;
    }

    public double getDouble(String key) {
        Object value = get(key);
        if (value instanceof Double num) {
            return num;
        }
        return 0.0;
    }

    public float getFloat(String key) {
        Object value = get(key);
        if (value instanceof Float num) {
            return num;
        }
        return 0.0f;
    }

    public List<String> getStringList(String key) {
        Object value = get(key);
        if (value instanceof List<?>) {
            return ((List<?>) value).stream()
                    .map(String::valueOf)
                    .toList();
        }
        return Collections.emptyList();
    }
}