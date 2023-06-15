package org.example.dodalock.dodalock.utils.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.example.dodalock.dodalock.DodaLockMain;

import java.io.File;
import java.io.IOException;

public class MainConfiguration {
    private static File file;
    private FileConfiguration fileConfiguration;

    // Настройки конфига по-умолчанию
    private static final String language = "en_us";
    private static final boolean clearBunchKeysInventory = true;
    private static int verificationPeriod = 3;

    public MainConfiguration() {
        file = new File(DodaLockMain.getPlugin().getDataFolder(), "config.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Oops, error creating the configuration file.");
            }
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        getFileConfiguration().options().copyDefaults(true);
        setup();
    }

    public void setup() {
        setLanguage();
        setAllowClearBunchKeysInventory();
        setVerificationPeriod();
        setResourcePack();
        setDamage();
        setCustomItemsOptions();
    }

    public void save() {
        try {
            fileConfiguration.save(file);
        }
        catch (IOException e) {
            System.out.println("Couldn't save configuration file");
        }
    }

    public void reload() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    private void setLanguage() { getFileConfiguration().set("language", language); }

    public String getLanguage() { return getFileConfiguration().getString("language"); }

    private void setAllowClearBunchKeysInventory() {
        getFileConfiguration().set("allow_clear_bunch_of_keys_inventory", Boolean.valueOf(clearBunchKeysInventory));
    }

    public boolean getAllowClearBunchKeysInventory() {
        return getFileConfiguration().getBoolean("allow_clear_bunch_of_keys_inventory");
    }

    private void setVerificationPeriod() {
        if (verificationPeriod < 0) verificationPeriod = 0;
        getFileConfiguration().set("verification_period", Integer.valueOf(verificationPeriod));
    }

    public int getVerificationPeriod() {
        return getFileConfiguration().getInt("verification_period");
    }

    private void setCustomItemsOptions() {
        getFileConfiguration().createSection("items");

        // Кодовый замок
        getFileConfiguration().createSection("items.code_lock");
        getFileConfiguration().set("items.code_lock.shape", "$$$, &*%, $$$");
        getFileConfiguration().set("items.code_lock.shape_materials", "$: IRON_INGOT, *: REDSTONE_BLOCK, " +
                "&: STONE_BUTTON, %: GLASS_PANE");

        // Замок
        getFileConfiguration().createSection("items.lock");
        getFileConfiguration().set("items.lock.shape", "%%, %%");
        getFileConfiguration().set("items.lock.shape_materials", "%: IRON_INGOT");

        // Ключ
        getFileConfiguration().createSection("items.key");
        getFileConfiguration().set("items.key.shape", "%, $");
        getFileConfiguration().set("items.key.shape_materials", "%: IRON_INGOT, $: LEVER");

        // Связка ключей
        getFileConfiguration().createSection("items.bunch_of_keys");
        getFileConfiguration().set("items.bunch_of_keys.shape", "-$-, $%$, -$-");
        getFileConfiguration().set("items.bunch_of_keys.shape_materials", "%: IRON_INGOT, $: STRING");
    }

    private void setResourcePack() {
        getFileConfiguration().set("enable_resourcepack", true);
        getFileConfiguration().set("resourcepack_url", "http://resourcepack.host/dl/CBdqxUZ66Q7b6HOV8OoVYM7pJjclsUEF/DodaLock.zip");
    }

    private void setDamage() {
        getFileConfiguration().set("enable_damage", true);
        getFileConfiguration().set("max_attempts_to_take_damage", 3);
    }

    public boolean isEnableDamage() { return getFileConfiguration().getBoolean("enable_damage"); }

    public int getMaxAttemptsDamage() {
        int attempts = getFileConfiguration().getInt("max_attempts_to_take_damage");
        if (attempts < 1) attempts = 1;
        return attempts;
    }

    public boolean isEnableResourcePack() { return getFileConfiguration().getBoolean("enable_resourcepack"); }

    public String getResourcePackUrl() {
        return getFileConfiguration().getString("resourcepack_url");
    }

    public String getItemShape(String item_key) {
        return getFileConfiguration().getString("items." + item_key + ".shape");
    }

    public String getItemShapeMaterials(String item_key) {
        return getFileConfiguration().getString("items." + item_key + ".shape_materials");
    }

    public boolean isConfigItems(String item_key) {
        return getFileConfiguration().contains("items." + item_key);
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }
}
