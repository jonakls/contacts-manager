package io.github.jonakls.contactsmanager.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;

@Getter
public final class ConfigManager {

    private final Dotenv dotenv;

    private ConfigManager() {
        this.dotenv = Dotenv
                .configure().directory(System.getProperty("user.dir"))
                .load();
    }

    private static class ConfigManagerHolder {
        private static final ConfigManager INSTANCE = new ConfigManager();
    }

    public static ConfigManager get() {
        return ConfigManagerHolder.INSTANCE;
    }


}
