package net.dreamingworld.core.guilds;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.MojangAPI;
import net.dreamingworld.core.Util;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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

        if (config.getConfigurationSection("players") == null) {
            config.createSection("players");
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

    public static List<String> getPlayerInvites(UUID uuid) {
        ConfigurationSection p = config.getConfigurationSection("players");
        return p.getStringList(uuid.toString());
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
        ConfigurationSection p = config.getConfigurationSection("players");
        ConfigurationSection n = config.getConfigurationSection("nicks");

        UUID uuid = MojangAPI.getPlayerUUID(player);

        if (uuid == null) {
            return -1;
        }

        if (!isInvited(uuid, guild)) {
            if (getPlayerInvites(uuid).size() > 15) {
                return -3;
            }

            if (getInvited(guild).size() > 20) {
                return -4;
            }

            List<String> l = s.getStringList(guild);
            l.add(uuid.toString());
            s.set(guild, l);

            l = p.getStringList(uuid.toString());
            l.add(guild);
            p.set(uuid.toString(), l);

            n.set(uuid.toString(), player);

            Player pl = Bukkit.getPlayer(uuid);

            if (pl != null && pl.isOnline()) {
                sendInviteMessage(pl, guild);
            }

            return 0;
        }

        return -2;
    }

    public static int cancelInvite(String player, String guild) {
        ConfigurationSection s = config.getConfigurationSection("invites");
        ConfigurationSection p = config.getConfigurationSection("players");
        ConfigurationSection n = config.getConfigurationSection("nicks");

        UUID uuid = MojangAPI.getPlayerUUID(player);

        if (uuid == null) {
            return -1;
        }

        if (isInvited(uuid, guild)) {
            List<String> l = s.getStringList(guild);
            l.remove(uuid.toString());
            s.set(guild, l);

            l = p.getStringList(uuid.toString());
            l.remove(guild);
            p.set(uuid.toString(), l);

            n.set(uuid.toString(), null);

            return 0;
        }

        return -2;
    }


    protected static void sendInviteMessage(Player player, String guild) {
        player.sendMessage(Util.formatString("$(PC)You have been invited to $(SC)" + guild + "$(PC)! Run $(SC)/guild join " + guild + " $(PC)to join this guild or run $(SC)/guild reject " + guild + " $(PC)to reject an invitation"));
    }

    protected static void sendInviteMessages(Player player) {
        List<String> invites = getPlayerInvites(player.getUniqueId());

        if (invites == null) {
            return;
        }

        for (String guild : invites) {
            sendInviteMessage(player, guild);
        }
    }
}
