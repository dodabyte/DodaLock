package org.example.dodalock.dodalock.utils.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.example.dodalock.dodalock.DodaLockMain;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LanguageConfiguration {
    private FileConfiguration fileLanguageConfiguration;

    public LanguageConfiguration(String locale){
        if (locale.contains(File.separator)) throw new RuntimeException("Locale name inputStream not valid");
        if (locale.equals("system")) locale = System.getProperty("user.language");

        InputStream inputStream = null;
        inputStream = DodaLockMain.getPlugin().getResource("localization/" + locale + ".yml");
        if (inputStream == null) {
            inputStream = DodaLockMain.getPlugin().getResource("localization/en_us.yml");
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            fileLanguageConfiguration = YamlConfiguration.loadConfiguration(reader);
        }
        catch (NullPointerException e) { System.out.println("Error in InputStreamReader: " + e); }
    }

    public String translate(String key) {
        return fileLanguageConfiguration.getString(key);
    }
}
