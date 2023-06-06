package org.example.dodalock.dodalock.utils.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainConfiguration {
    private final FileConfiguration fileConfiguration;

    public MainConfiguration(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
        new InventoryConfiguration();
    }

    public void setLanguage(String language) { getFileConfiguration().set("language", language); }

    public String getLanguage() { return getFileConfiguration().get("language").toString(); }

    public void setAllowClearBunchKeysInventory(boolean flag) {
        getFileConfiguration().set("allow_clear_bunch_of_keys_inventory", flag);
    }

    public boolean getAllowClearBunchKeysInventory() {
        return getFileConfiguration().getBoolean("allow_clear_bunch_of_keys_inventory");
    }

    public void setVerificationPeriod(int period) {
        getFileConfiguration().set("verification_period", period);
    }

    public int getVerificationPeriod() {
        return getFileConfiguration().getInt("verification_period");
    }

    public void setup(String language) {
        setLanguage(language);
        setAllowClearBunchKeysInventory(true);
        setVerificationPeriod(3);
        getFileConfiguration().createSection("data");
        getFileConfiguration().createSection("data.configuration");
        getFileConfiguration().createSection("data.configuration.code_locks");
        getFileConfiguration().createSection("data.configuration.locks");
    }

    public void addCodeLock(String password, Player player, String location) {
        if (!getFileConfiguration().contains("data.configuration.code_locks." + location))
            getFileConfiguration().createSection("data.configuration.code_locks." + location);
        getFileConfiguration().set("data.configuration.code_locks." + location + ".password", password);
        getFileConfiguration().set("data.configuration.code_locks." + location + ".players", Arrays.asList(player.getName()));

        List<String> codeLocksList = getFileConfiguration().getStringList("data.code_locks_list");
        codeLocksList.add(location);
        getFileConfiguration().set("data.code_locks_list", null);
        getFileConfiguration().set("data.code_locks_list", codeLocksList);
    }

    public void addLock(Player owner, String location) {
        if (!getFileConfiguration().contains("data.configuration.locks." + location))
            getFileConfiguration().createSection("data.configuration.locks." + location);
        getFileConfiguration().set("data.configuration.locks."+ location + ".id_key", "");
        getFileConfiguration().set("data.configuration.locks." + location + ".owner", owner.getName());

        List<String> locksList = getFileConfiguration().getStringList("data.locks_list");
        locksList.add(location);
        getFileConfiguration().set("data.locks_list", null);
        getFileConfiguration().set("data.locks_list", locksList);
    }

    public void addBunchKeys(String bunchKeys) {
        InventoryConfiguration.getFileInventoryConfiguration().createSection("inventory." + bunchKeys);
    }

    public void addPlayerInCodeLockData(String location, Player player) {
        List<String> playersList = getPlayersFromCodeLock(location);
        playersList.add(player.getName());
        getFileConfiguration().set("data.configuration.code_locks." + location + ".players", null);
        getFileConfiguration().set("data.configuration.code_locks." + location + ".players", playersList);
    }

    public void removePlayerFromCodeLockData(String location, Player player) {
        if (isPlayerInCodeLock(location, player)) {
            List<String> playersList = getPlayersFromCodeLock(location);
            getFileConfiguration().set("data.configuration.code_locks." + location + ".players", null);
            playersList.remove(player.getName());
            getFileConfiguration().set("data.configuration.code_locks." + location + ".players", playersList);
        }
    }

    public void changePassword(String location, String newPassword) {
        getFileConfiguration().set("data.configuration.code_locks." + location + ".password", newPassword);
    }

    public void changeKey(String location, String newKey) {
        getFileConfiguration().set("data.configuration.locks." + location + ".id_key", newKey);
    }

    public void removeCodeLock(String location) {
        getFileConfiguration().set("data.configuration.code_locks." + location, null);
    }

    public void removeLock(String location) {
        getFileConfiguration().set("data.configuration.locks." + location, null);
    }

    public void removeKey(String location) {
        getFileConfiguration().set("data.configuration.locks." + location + ".id_key", null);
    }

    public void removeCodeLockFromList(String location) {
        List<String> codeLocksList = getFileConfiguration().getStringList("data.code_locks_list");
        codeLocksList.remove(location);
        getFileConfiguration().set("data.code_locks_list", null);
        getFileConfiguration().set("data.code_locks_list", codeLocksList);
    }

    public void removeLockFromList(String location) {
        List<String> locksList = getFileConfiguration().getStringList("data.locks_list");
        locksList.remove(location);
        getFileConfiguration().set("data.locks_list", null);
        getFileConfiguration().set("data.locks_list", locksList);
    }

    public void removeBunchOfKeys(String bunchKeys) {
        InventoryConfiguration.getFileInventoryConfiguration().set("inventory." + bunchKeys, null);
    }

    public boolean isCodeLock(String location) {
        return getFileConfiguration().contains("data.configuration.code_locks." + location);
    }

    public boolean isLock(String location) {
        return getFileConfiguration().contains("data.configuration.locks." + location);
    }

    public boolean isPlayerInCodeLock(String location, Player player) {
        return isCodeLock(location) && getPlayersFromCodeLock(location) != null &&
                getPlayersFromCodeLock(location).contains(player.getName());
    }

    public boolean isOwnerInLock(String location, Player owner) {
        return isLock(location) && getOwnerFromLock(location) != null
                && getOwnerFromLock(location).equals(owner.getName());
    }

    public boolean isTruePassword(String location, String password) {
        return isCodeLock(location) && getPassword(location) != null && getPassword(location).equals(password);
    }

    public boolean isTrueKey(String location, String key) {
        return isLock(location) && getKey(location) != null && getKey(location).equals(key);
    }

    public boolean isKey(String location) {
        return isLock(location) && getKey(location) != null && !getKey(location).equals("");
    }

    public boolean isBunchKeys(String bunchKeys) {
        return bunchKeys != null && !bunchKeys.equals("") &&
            InventoryConfiguration.getFileInventoryConfiguration().contains("inventory." + bunchKeys);
    }

    public List<ItemStack> getKeysInBunchKeys(String bunchKeys) {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (InventoryConfiguration.getFileInventoryConfiguration().contains("inventory." + bunchKeys + ".content." + i)) {
                itemStackList.add(InventoryConfiguration.getFileInventoryConfiguration().getItemStack("inventory." +
                        bunchKeys + ".content." + i));
            }
        }
        return itemStackList;
    }

    public List<String> getBunchKeys() {
        return InventoryConfiguration.getFileInventoryConfiguration().getStringList("inventory");
    }

    public LocalDateTime getLastDateSerialize(String bunchKeys) {
        String stringDate = InventoryConfiguration.getFileInventoryConfiguration().getString("inventory." +
                bunchKeys + ".date_last_serialize");
        if (stringDate != null)
            return LocalDateTime.parse(stringDate);
        return null;
    }

    public List<String> getCodeLockData() {
        return getFileConfiguration().getStringList("data.code_locks_list");
    }

    public List<String> getLockData() {
        return getFileConfiguration().getStringList("data.locks_list");
    }

    public List<String> getPlayersFromCodeLock(String location) {
        return getFileConfiguration().getStringList("data.configuration.code_locks." + location + ".players");
    }

    public String getOwnerFromLock(String location) {
        return getFileConfiguration().getString("data.configuration.locks." + location + ".owner");
    }

    public String getPassword(String location) {
        return getFileConfiguration().getString("data.configuration.code_locks." + location + ".password");
    }

    public String getKey(String location) {
        return getFileConfiguration().getString("data.configuration.locks." + location + ".id_key");
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }
}
