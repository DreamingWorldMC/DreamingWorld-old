package net.dreamingworld.core.guilds;

import net.dreamingworld.DreamingWorld;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Guilds implements Listener, Runnable {

    private File file;
    private YamlConfiguration config;

    public Guilds() {
        file = new File(DreamingWorld.dataDirectory + "guilds/", "guilds.yml");
        config = YamlConfiguration.loadConfiguration(file);

        if (config.getConfigurationSection("guilds") == null) {
            config.createSection("guilds");
        }

        Bukkit.getScheduler().runTaskTimerAsynchronously(DreamingWorld.getInstance(), this, 0, 100);
    }


    public int createGuild(String name, Player owner) {
        Set<String> guilds = config.getConfigurationSection("guilds").getKeys(false);

        if (guilds.contains(name)) {
            return -1;
        }

        if (getPlayerGuild(owner)[0] != null) {
            return -2;
        }

        String ownerUuid = owner.getUniqueId().toString();

        ConfigurationSection guild = config.getConfigurationSection("guilds").createSection(name);
        guild.set("owner", ownerUuid);

        guild.createSection("chunks");

        addPlayerToGuild(owner, name, "owner");

        return 0;
    }

    public int removeGuild(String name) {
        ConfigurationSection g = config.getConfigurationSection("guilds");
        Set<String> guilds = g.getKeys(false);

        if (!guilds.contains(name)) {
            return -1;
        }

        g.set(name, null);

        return 0;
    }


    public List<Chunk> getGuildChunkList(String name) {
        ConfigurationSection guild = config.getConfigurationSection("guilds").getConfigurationSection(name);

        if (guild == null) {
            Bukkit.broadcastMessage("a");
            return new ArrayList<>();
        }

        ConfigurationSection chunks = guild.getConfigurationSection("chunks");

        if (chunks == null) {
            return new ArrayList<>();
        }

        Set<String> worlds = chunks.getKeys(false);

        List<Chunk> chnks = new ArrayList<>();

        for (String w : worlds) {
            List<String> c = chunks.getStringList(w);

            for (String ch : c) {
                String[] coords = ch.split("_");
                chnks.add(Bukkit.getWorld(w).getChunkAt(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])));
            }
        }

        return chnks;
    }


    public String getChunkOwner(Chunk chunk) {
        ConfigurationSection chunks = config.getConfigurationSection("chunks");

        if (chunks == null) {
            return null;
        }

        return chunks.getString(chunk.getX() + "_" + chunk.getZ());
    }

    public void setChunkOwner(Chunk chunk, String guild) {
        ConfigurationSection chunks = config.getConfigurationSection("chunks");

        if (chunks == null) {
            chunks = config.createSection("chunks");
        }

        chunks.set(chunk.getX() + "_" + chunk.getZ(), guild);
    }

    public int giveChunk(Chunk chunk, String guild) {
        Set<String> guilds = config.getConfigurationSection("guilds").getKeys(false);

        if (!guilds.contains(guild)) {
            return -1;
        }

        ConfigurationSection chunks = config.getConfigurationSection("chunks");

        if (chunks == null) {
            config.createSection("chunks");
        }

        String o = getChunkOwner(chunk);

        if (o != null) {
            return guild.equals(o) ? -2 : -3;
        }

        setChunkOwner(chunk, guild);

        List<String> ch = config.getConfigurationSection("guilds").getConfigurationSection(guild).getConfigurationSection("chunks").getStringList(chunk.getWorld().getName());

        if (ch == null) {
            ch = new ArrayList<>();
        }

        ch.add(chunk.getX() + "_" + chunk.getZ());
        config.getConfigurationSection("guilds").getConfigurationSection(guild).getConfigurationSection("chunks").set(chunk.getWorld().getName(), ch);

        return 0;
    }


    public String[] getPlayerGuild(Player player) {
        Set<String> guilds = config.getConfigurationSection("guilds").getKeys(false);
        String uuid = player.getUniqueId().toString();

        String[] output = new String[2];

        for (String guild : guilds) {
            ConfigurationSection sect = config.getConfigurationSection("guilds").getConfigurationSection(guild);

            String plr = sect.getConfigurationSection("players").getString(uuid);

            if (plr != null) {
                output[0] = guild;
                output[1] = plr;
                return output;
            }
        }

        return output;
    }


    public Set<String> getGuildMembers(String guild) {
        ConfigurationSection g = config.getConfigurationSection("guilds").getConfigurationSection(guild);

        if (g == null) {
            return null;
        }

        ConfigurationSection pl = g.getConfigurationSection("players");

        if (pl == null) {
            return new HashSet<>();
        }

        return pl.getKeys(false);
    }

    public int addPlayerToGuild(Player player, String guild, String role) {
        ConfigurationSection g = config.getConfigurationSection("guilds").getConfigurationSection(guild);

        if (g == null) {
            return -1;
        }

        ConfigurationSection pl = g.getConfigurationSection("players");

        if (pl == null) {
            pl = g.createSection("players");
        }

        pl.set(player.getUniqueId().toString(), role);

        return 0;
    }

    public int removePlayerFromGuild(Player player, String guild) {
        return addPlayerToGuild(player, guild, null);
    }


    @Override
    public void run() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
