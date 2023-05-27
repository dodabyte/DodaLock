package org.example.dodalock.dodalock.utils.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MainConfiguration {
    private final FileConfiguration fileConfiguration;

    public MainConfiguration(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }

    public void setLanguage(String language) { getFileConfiguration().set("language", language); }

    public String getLanguage() { return getFileConfiguration().get("language").toString(); }

    public void setup(String language) {
        setLanguage(language);
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
