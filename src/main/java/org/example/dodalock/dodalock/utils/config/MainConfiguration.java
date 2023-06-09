package org.example.dodalock.dodalock.utils.config;

import org.bukkit.configuration.file.FileConfiguration;

public class MainConfiguration {
    private final FileConfiguration fileConfiguration;

    public MainConfiguration(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }

    public void setup(String language) {
        setLanguage(language);
        setAllowClearBunchKeysInventory(false);
        setVerificationPeriod(0);
    }

    public void setup(String language, int verificationPeriod) {
        int period = verificationPeriod;
        setLanguage(language);
        if (verificationPeriod > 0)
            setAllowClearBunchKeysInventory(true);
        else setAllowClearBunchKeysInventory(false);
        if (period < 0) period = 0;
        setVerificationPeriod(period);
    }

    public void setup(String language, boolean clearBunchKeysInventory, int verificationPeriod) {
        int period = verificationPeriod;
        setLanguage(language);
        setAllowClearBunchKeysInventory(clearBunchKeysInventory);
        if (period < 0) period = 0;
        setVerificationPeriod(period);
    }

    public void setLanguage(String language) { getFileConfiguration().set("language", language); }

    public String getLanguage() { return getFileConfiguration().getString("language"); }

    public void setAllowClearBunchKeysInventory(boolean flag) {
        getFileConfiguration().set("allow_clear_bunch_of_keys_inventory", Boolean.valueOf(flag));
    }

    public boolean getAllowClearBunchKeysInventory() {
        return getFileConfiguration().getBoolean("allow_clear_bunch_of_keys_inventory");
    }

    public void setVerificationPeriod(int period) {
        getFileConfiguration().set("verification_period", Integer.valueOf(period));
    }

    public int getVerificationPeriod() {
        return getFileConfiguration().getInt("verification_period");
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }
}
