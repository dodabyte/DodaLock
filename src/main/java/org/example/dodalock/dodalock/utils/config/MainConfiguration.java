package org.example.dodalock.dodalock.utils.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.example.dodalock.dodalock.DodaLockMain;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
            } catch (Exception ignored) {}
            fileConfiguration = YamlConfiguration.loadConfiguration(file);
            setup();
        }
        else fileConfiguration = YamlConfiguration.loadConfiguration(file);
        getFileConfiguration().options().copyDefaults(true);

        save();
        reload;
    }

    public void setup() {
        setLanguage();
        setClearBunchKeysInventory();
        setLore();
        setDamage();
        setMasterKey();
        setResourcePack();
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

    private void setLanguage() {
        getFileConfiguration().set("language", language);

        getFileConfiguration().setComments("language",
                List.of("The language of the plugin interface. Default: en_us (English)"));
    }

    private void setClearBunchKeysInventory() {
        getFileConfiguration().set("allow_clear_bunch_of_keys_inventory", Boolean.valueOf(clearBunchKeysInventory));
        if (verificationPeriod < 0) verificationPeriod = 0;
        getFileConfiguration().set("verification_period", Integer.valueOf(verificationPeriod));

        getFileConfiguration().setComments("allow_clear_bunch_of_keys_inventory",
                List.of("", "", "Setting that includes clearing the bunch of keys inventory data once in a while. Default: true"));
        getFileConfiguration().setComments("verification_period",
                List.of("", "Setting the frequency (in hours) of checking the inventory of the bunch of keys for cleaning." +
                        " Default: 3"));
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
        getFileConfiguration().set("items.lock.shape", "-$-, %%%, %%%");
        getFileConfiguration().set("items.lock.shape_materials", "$: STRING, %: IRON_INGOT");

        // Ключ
        getFileConfiguration().createSection("items.key");
        getFileConfiguration().set("items.key.shape", "%, $, #");
        getFileConfiguration().set("items.key.shape_materials", "%: IRON_NUGGET, $: STICK, #: IRON_INGOT");

        // Мастер ключ
        getFileConfiguration().createSection("items.master_key");
        getFileConfiguration().set("items.master_key.shape", "%, $, #");
        getFileConfiguration().set("items.master_key.shape_materials", "%: IRON_NUGGET, $: STICK, #: COMMAND_BLOCK");

        // Связка ключей
        getFileConfiguration().createSection("items.bunch_of_keys");
        getFileConfiguration().set("items.bunch_of_keys.shape", "-$-, $%$, -$-");
        getFileConfiguration().set("items.bunch_of_keys.shape_materials", "%: IRON_INGOT, $: STRING");

        getFileConfiguration().setComments("items",
                List.of("", "", "Setting up recipes for crafting custom items.",
                        "shape - The items crafts shape. Max. three lines and three characters by line, where " +
                                "symbol '-' is none. Example: #$#, -#-, #$#.",
                        "shape_materials - Materials intended for crafting. Example: #: IRON_INGOT, $: STICK"));
        getFileConfiguration().setComments("items.code_lock.shape", List.of("Default: $$$, &*%, $$$"));
        getFileConfiguration().setComments("items.code_lock.shape_materials",
                List.of("Default: $: IRON_INGOT, *: REDSTONE_BLOCK, &: STONE_BUTTON, %: GLASS_PANE"));
        getFileConfiguration().setComments("items.lock.shape", List.of("Default: %%, %%"));
        getFileConfiguration().setComments("items.lock.shape_materials", List.of("Default: %: IRON_INGOT"));
        getFileConfiguration().setComments("items.key.shape", List.of("Default: %, $, #"));
        getFileConfiguration().setComments("items.key.shape_materials", List.of("Default: %: IRON_NUGGET, $: STICK, #: IRON_INGOT"));
        getFileConfiguration().setComments("items.master_key.shape", List.of("Default: %, $, #"));
        getFileConfiguration().setComments("items.master_key.shape_materials", List.of("Default: %: IRON_NUGGET, $: STICK, #: COMMAND_BLOCK"));
        getFileConfiguration().setComments("items.bunch_of_keys.shape", List.of("Default: -$-, $%$, -$-"));
        getFileConfiguration().setComments("items.bunch_of_keys.shape_materials", List.of("Default: %: IRON_INGOT, $: STRING"));
    }

    private void setResourcePack() {
        getFileConfiguration().set("enable_resourcepack", true);
        getFileConfiguration().set("resourcepack_url", "http://resourcepack.host/dl/7ZA2pXlbKr7XdcomfcCnXirXU4PGDNcf/DodaLock.zip");

        getFileConfiguration().setComments("enable_resourcepack",
                List.of("", "", "Setting enable a custom resource pack for the plugin. Enter 'false' if you want to play without it or embed the resource pack into your server locally. Default: true"));
        getFileConfiguration().setComments("resourcepack_url",
                List.of("", "Setting responsible for the link to the resource pack. " +
                        "Default: http://resourcepack.host/dl/7ZA2pXlbKr7XdcomfcCnXirXU4PGDNcf/DodaLock.zip"));
    }

    private void setDamage() {
        getFileConfiguration().set("enable_damage", true);
        getFileConfiguration().set("max_attempts_to_take_damage", 3);

        getFileConfiguration().setComments("enable_damage",
                List.of("", "", "Setting enable damage from the code lock during incorrect input password entry attempts. " +
                        "Default: true"));
        getFileConfiguration().setComments("max_attempts_to_take_damage",
                List.of("", "Setting the maximum number of attempts in which damage from incorrect password entry is not inflicted. " +
                        "Default: 3"));
    }

    private void setLore() {
        getFileConfiguration().set("enable_key_lore", true);

        getFileConfiguration().setComments("enable_key_lore",
                List.of("", "", "Setting enable a lore for keys and a bunch of keys, displaying the coordinates of the lock " +
                        "that the key or keys are associated with. Default: true"));
    }

    private void setMasterKey() {
        getFileConfiguration().set("enable_master_key", true);

        getFileConfiguration().setComments("enable_master_key",
                List.of("", "", "The setting allows you to use a universal key for administrators. Default: true"));
    }

    public String getLanguage() { return getFileConfiguration().getString("language"); }

    public boolean getAllowClearBunchKeysInventory() {
        return getFileConfiguration().getBoolean("allow_clear_bunch_of_keys_inventory");
    }

    public int getVerificationPeriod() {
        return getFileConfiguration().getInt("verification_period");
    }

    public int getMaxAttemptsDamage() {
        int attempts = getFileConfiguration().getInt("max_attempts_to_take_damage");
        if (attempts < 1) attempts = 1;
        return attempts;
    }

    public String getResourcePackUrl() {
        if (getFileConfiguration().getString("resourcepack_url") != null)
            return getFileConfiguration().getString("resourcepack_url").replace("'", "");
        return "";
    }

    public String getItemShape(String item_key) {
        return getFileConfiguration().getString("items." + item_key + ".shape");
    }

    public String getItemShapeMaterials(String item_key) {
        return getFileConfiguration().getString("items." + item_key + ".shape_materials");
    }

    public boolean isEnableDamage() { return getFileConfiguration().getBoolean("enable_damage"); }

    public boolean isEnableResourcePack() { return getFileConfiguration().getBoolean("enable_resourcepack"); }

    public boolean isConfigItems(String item_key) {
        return getFileConfiguration().contains("items." + item_key);
    }

    public boolean isEnableMasterKey() {
        return getFileConfiguration().getBoolean("enable_master_key");
    }

    public boolean isEnableLore() { return getFileConfiguration().getBoolean("enable_key_lore"); }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }
}
