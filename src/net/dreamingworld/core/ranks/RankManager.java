package net.dreamingworld.core.ranks;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.MojangAPI;
import net.dreamingworld.core.Util;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RankManager implements Listener {

    private final YamlConfiguration config;

    private final File playersFile;
    private final YamlConfiguration players;

    private String defaultRank = "player";

    public RankManager() {
        File file = new File(DreamingWorld.dataDirectory, "ranks.yml");
        config = YamlConfiguration.loadConfiguration(file);

        playersFile = new File(DreamingWorld.dataDirectory, "players.yml");
        players = YamlConfiguration.loadConfiguration(playersFile);

        if (config.getConfigurationSection("hierarchy") == null) {
            config.createSection("hierarchy");
        }

        if (players.getConfigurationSection("players") == null) {
            players.createSection("players");
        }

        new CommandRank();

        for (String k : config.getConfigurationSection("hierarchy").getKeys(false)) {
            if (config.getConfigurationSection("hierarchy").getConfigurationSection(k).getBoolean("default")) {
                defaultRank = k;
                break;
            }
        }
    }


    public List<String> getRankHierarchy() {
        return new ArrayList<>(config.getConfigurationSection("hierarchy").getKeys(false));
    }


    public void setPlayerRank(String name, String rank) {
        UUID uuid = MojangAPI.getPlayerUUID(name);

        if (uuid != null) {
            players.getConfigurationSection("players").set(uuid.toString(), rank);
        }

        try {
            players.save(playersFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPlayerRank(UUID uuid) {
        if (uuid != null && players.getConfigurationSection("players").contains(uuid.toString())) {
            return players.getConfigurationSection("players").getString(uuid.toString());
        }

        return null;
    }

    public String getPlayerRank(String name) {
        UUID uuid = MojangAPI.getPlayerUUID(name);
        return getPlayerRank(uuid);
    }


    public List<String> getRankPermissions(String rank) {
        ConfigurationSection s = config.getConfigurationSection("hierarchy").getConfigurationSection(rank);

        if (s == null) {
            return new ArrayList<>();
        }

        List<String> perms = new ArrayList<>();

        List<String> keys = new ArrayList<>(config.getConfigurationSection("hierarchy").getKeys(false));
        int idx = keys.indexOf(rank);

        if (idx > 0) {
            for (String r : keys.subList(0, idx)) {
                perms.addAll(config.getConfigurationSection("hierarchy").getConfigurationSection(r).getStringList("permissions"));
            }
        }

        perms.addAll(s.getStringList("permissions"));

        return perms;
    }

    public String getRankPrefix(String rank) {
        ConfigurationSection s = config.getConfigurationSection("hierarchy").getConfigurationSection(rank);

        if (s == null) {
            return "";
        }

        String p = s.getString("prefix");
        return Util.formatString(p);
    }

    public String getRankPostfix(String rank) {
        ConfigurationSection s = config.getConfigurationSection("hierarchy").getConfigurationSection(rank);

        if (s == null) {
            return " ";
        }

        String p = s.getString("postfix");
        return Util.formatString(p);
    }

    public int getRankChunks(String rank) {
        ConfigurationSection s = config.getConfigurationSection("hierarchy").getConfigurationSection(rank);

        if (s == null) {
            return 3;
        }

        int i = s.getInt("chunks");

        if (i < 3) {
            i = 3;
        }

        return i;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        String rank = getPlayerRank(e.getPlayer().getName());
        e.getPlayer().setOp(false);

        if (rank == null) {
            rank = defaultRank;
            setPlayerRank(e.getPlayer().getName(), rank);
        }

        PermissionAttachment attachment = e.getPlayer().addAttachment(DreamingWorld.getInstance());

        for (String k : attachment.getPermissions().keySet()) {
            attachment.unsetPermission(k);
        }

        for (String permission : getRankPermissions(rank)) {
            if (permission.equals("*")) {
                for (Permission p : Bukkit.getPluginManager().getPermissions()) {
                    attachment.setPermission(p, true);
                }

                e.getPlayer().setOp(true);

                break;
            }

            attachment.setPermission(permission, true);
        }

        e.getPlayer().setDisplayName(getRankPrefix(rank) + e.getPlayer().getName() + getRankPostfix(rank));
        e.getPlayer().setPlayerListName(getRankPrefix(rank) + e.getPlayer().getName());
    }
}
