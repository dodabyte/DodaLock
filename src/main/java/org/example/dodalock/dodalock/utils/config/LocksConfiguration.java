package org.example.dodalock.dodalock.utils.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.example.dodalock.dodalock.DodaLockMain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocksConfiguration {
    private static File file;
    private FileConfiguration fileLocksConfiguration;

    public LocksConfiguration() {
        file = new File(DodaLockMain.getPlugin().getDataFolder(), "locks/data.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Oops, error creating the configuration file.");
            }
            fileLocksConfiguration = YamlConfiguration.loadConfiguration(file);
            setup();
        }
        else fileLocksConfiguration = YamlConfiguration.loadConfiguration(file);

        getFileLocksConfiguration().options().copyDefaults(true);
        save();
        reload();
    }

    public void setup() {
        getFileLocksConfiguration().createSection("data");
        getFileLocksConfiguration().createSection("data.configuration");
        getFileLocksConfiguration().createSection("data.configuration.code_locks");
        getFileLocksConfiguration().createSection("data.configuration.locks");
    }

    public void save() {
        try {
            fileLocksConfiguration.save(file);
        }
        catch (IOException e) {
            System.out.println("Couldn't save configuration file");
        }
    }

    public void reload() {
        fileLocksConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void addCodeLock(String password, Player player, String location) {
        if (!getFileLocksConfiguration().contains("data.configuration.code_locks." + location))
            getFileLocksConfiguration().createSection("data.configuration.code_locks." + location);
        getFileLocksConfiguration().set("data.configuration.code_locks." + location + ".password", password);
        getFileLocksConfiguration().set("data.configuration.code_locks." + location + ".players", Arrays.asList(player.getName()));

        List<String> codeLocksList = getFileLocksConfiguration().getStringList("data.code_locks_list");
        codeLocksList.add(location);
        getFileLocksConfiguration().set("data.code_locks_list", null);
        getFileLocksConfiguration().set("data.code_locks_list", codeLocksList);
    }

    public void addLock(Player owner, String location) {
        if (!getFileLocksConfiguration().contains("data.configuration.locks." + location))
            getFileLocksConfiguration().createSection("data.configuration.locks." + location);
        getFileLocksConfiguration().set("data.configuration.locks."+ location + ".id_key", "");
        getFileLocksConfiguration().set("data.configuration.locks." + location + ".owner", owner.getName());

        List<String> locksList = getFileLocksConfiguration().getStringList("data.locks_list");
        locksList.add(location);
        getFileLocksConfiguration().set("data.locks_list", null);
        getFileLocksConfiguration().set("data.locks_list", locksList);
    }

    public void addPlayerInCodeLockData(String location, Player player) {
        List<String> playersList = getPlayersFromCodeLock(location);
        playersList.add(player.getName());
        getFileLocksConfiguration().set("data.configuration.code_locks." + location + ".players", null);
        getFileLocksConfiguration().set("data.configuration.code_locks." + location + ".players", playersList);
    }

    public void removePlayerFromCodeLockData(String location, Player player) {
        if (isPlayerInCodeLock(location, player)) {
            List<String> playersList = getPlayersFromCodeLock(location);
            getFileLocksConfiguration().set("data.configuration.code_locks." + location + ".players", null);
            playersList.remove(player.getName());
            getFileLocksConfiguration().set("data.configuration.code_locks." + location + ".players", playersList);
        }
    }

    public void changePassword(String location, String newPassword) {
        getFileLocksConfiguration().set("data.configuration.code_locks." + location + ".password", newPassword);
    }

    public void changeKey(String location, String newKey) {
        getFileLocksConfiguration().set("data.configuration.locks." + location + ".id_key", newKey);
    }

    public void removeCodeLock(String location) {
        getFileLocksConfiguration().set("data.configuration.code_locks." + location, null);

        List<String> codeLocksList = getFileLocksConfiguration().getStringList("data.code_locks_list");
        codeLocksList.remove(location);
        getFileLocksConfiguration().set("data.code_locks_list", null);
        getFileLocksConfiguration().set("data.code_locks_list", codeLocksList);
    }

    public void removeLock(String location) {
        getFileLocksConfiguration().set("data.configuration.locks." + location, null);

        List<String> locksList = getFileLocksConfiguration().getStringList("data.locks_list");
        locksList.remove(location);
        getFileLocksConfiguration().set("data.locks_list", null);
        getFileLocksConfiguration().set("data.locks_list", locksList);
    }

    public void removeKey(String location) {
        getFileLocksConfiguration().set("data.configuration.locks." + location + ".id_key", null);
    }

    public boolean isCodeLock(String location) {
        return getFileLocksConfiguration().contains("data.configuration.code_locks." + location);
    }

    public boolean isLock(String location) {
        return getFileLocksConfiguration().contains("data.configuration.locks." + location);
    }

    public boolean isPlayerInCodeLock(String location, Player player) {
        return isCodeLock(location) && getPlayersFromCodeLock(location) != null &&
                getPlayersFromCodeLock(location).contains(player.getName());
    }

    public boolean isOwnerInLock(String location, Player owner) {
        return isLock(location) && getOwnerFromLock(location) != null
                && getOwnerFromLock(location).equals(owner.getName());
    }

    public List<String> getLocksListWithOwner(Player owner) {
        List<String> locksList = new ArrayList<>();
        for (String lock : Configurations.getLocks().getLockData()) {
            if (isOwnerInLock(lock, owner)) {
                locksList.add(lock);
            }
        }
        return locksList;
    }

    public List<String> getCodeLocksListWithPlayer(Player player) {
        List<String> codeLocksList = new ArrayList<>();
        for (String codeLock : Configurations.getLocks().getCodeLockData()) {
            if (isPlayerInCodeLock(codeLock, player)) {
                codeLocksList.add(codeLock);
            }
        }
        return codeLocksList;
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

    public boolean isUniqueUuidKey(String key) {
        boolean isUniqueUuid = true;
        List<String> locationsList = getLockData();
        for (String location : locationsList) {
            if (getKey(location).equals(key)) {
                isUniqueUuid = false;
                break;
            }
        }
        return isUniqueUuid;
    }

    public List<String> getCodeLockData() {
        return getFileLocksConfiguration().getStringList("data.code_locks_list");
    }

    public List<String> getLockData() {
        return getFileLocksConfiguration().getStringList("data.locks_list");
    }

    public List<String> getPlayersFromCodeLock(String location) {
        return getFileLocksConfiguration().getStringList("data.configuration.code_locks." + location + ".players");
    }

    public String getOwnerFromLock(String location) {
        return getFileLocksConfiguration().getString("data.configuration.locks." + location + ".owner");
    }

    public String getPassword(String location) {
        return getFileLocksConfiguration().getString("data.configuration.code_locks." + location + ".password");
    }

    public String getKey(String location) {
        return getFileLocksConfiguration().getString("data.configuration.locks." + location + ".id_key");
    }

    public List<String> getKeyIdList() {
        List<String> keyIdList = new ArrayList<>();
        for (String keyLocation : getLockData()) {
            keyIdList.add(getKey(keyLocation));
        }
        return keyIdList;
    }

    public FileConfiguration getFileLocksConfiguration() { return fileLocksConfiguration; }
}
