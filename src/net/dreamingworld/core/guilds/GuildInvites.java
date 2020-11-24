package net.dreamingworld.core.guilds;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.MojangAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
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
    }


    public static boolean isInvited(String player, String guild) {
        ConfigurationSection s = config.getConfigurationSection("invites");

        UUID uuid = MojangAPI.getPlayerUUID(player);

        if (uuid == null) {
            return false;
        }

        return s.getStringList(guild).contains(uuid.toString());
    }

    public static int addInvite(String player, String guild) {
        ConfigurationSection s = config.getConfigurationSection("invites");

        UUID uuid = MojangAPI.getPlayerUUID(player);

        if (uuid == null) {
            return -1;
        }

        if (!isInvited(player, guild)) {
            List<String> l = s.getStringList(guild);
            l.add(uuid.toString());
            s.set(guild, l);

            return 0;
        }

        return -2;
    }

    public static int cancelInvite(String player, String guild) {
        ConfigurationSection s = config.getConfigurationSection("invites");

        UUID uuid = MojangAPI.getPlayerUUID(player);

        if (uuid == null) {
            return -1;
        }

        if (isInvited(player, guild)) {
            List<String> l = s.getStringList(guild);
            l.remove(uuid.toString());
            s.set(guild, l);

            return 0;
        }

        return -2;
    }
}
