package net.dreamingworld.core.guilds;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.MojangAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class GuildInvites {

    private static File file;
    private static YamlConfiguration config;

    public static void initializeInvites() {
        file = new File(DreamingWorld.dataDirectory + "guilds/", "invites.yml");
        config = YamlConfiguration.loadConfiguration(file);

        if (config.getConfigurationSection("invites") == null) {
            config.createSection("invites");
        }

        if (config.getConfigurationSection("nicks") == null) {
            config.createSection("nicks");
        }
    }

    public static void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<String> getInvited(String guild) {
        ConfigurationSection s = config.getConfigurationSection("invites");
        return s.getStringList(guild);
    }


    public static boolean isInvited(String player, String guild) {
        ConfigurationSection s = config.getConfigurationSection("invites");

        UUID uuid = MojangAPI.getPlayerUUID(player);

        if (uuid == null) {
            return false;
        }

        return s.getStringList(guild).contains(uuid.toString());
    }

    public static boolean isInvited(UUID uuid, String guild) {
        ConfigurationSection s = config.getConfigurationSection("invites");
        return s.getStringList(guild).contains(uuid.toString());
    }


    public static String getNick(String uuid) {
        ConfigurationSection n = config.getConfigurationSection("nicks");
        return n.getString(uuid);
    }


    public static int addInvite(String player, String guild) {
        ConfigurationSection s = config.getConfigurationSection("invites");
        ConfigurationSection n = config.getConfigurationSection("nicks");

        UUID uuid = MojangAPI.getPlayerUUID(player);

        if (uuid == null) {
            return -1;
        }

        if (!isInvited(uuid, guild)) {
            List<String> l = s.getStringList(guild);
            l.add(uuid.toString());
            s.set(guild, l);

            n.set(uuid.toString(), player);

            return 0;
        }

        return -2;
    }

    public static int cancelInvite(String player, String guild) {
        ConfigurationSection s = config.getConfigurationSection("invites");
        ConfigurationSection n = config.getConfigurationSection("nicks");

        UUID uuid = MojangAPI.getPlayerUUID(player);

        if (uuid == null) {
            return -1;
        }

        if (isInvited(uuid, guild)) {
            List<String> l = s.getStringList(guild);
            l.remove(uuid.toString());
            s.set(guild, l);

            n.set(uuid.toString(), null);

            return 0;
        }

        return -2;
    }
}
