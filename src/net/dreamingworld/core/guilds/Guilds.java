package net.dreamingworld.core.guilds;

import net.dreamingworld.DreamingWorld;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.Set;

public class Guilds implements Listener {

    private File file;
    private YamlConfiguration config;

    public Guilds() {
        file = new File(DreamingWorld.dataDirectory + "guilds/", "guilds.yml");
        config = YamlConfiguration.loadConfiguration(file);

        if (config.getConfigurationSection("guilds") == null) {
            config.createSection("guilds");
        }
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

        ConfigurationSection players = guild.createSection("players");
        players.set(ownerUuid, "owner");

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


    public int addPlayerToGuild(Player player, String guild, String role) {
        ConfigurationSection g = config.getConfigurationSection("guilds").getConfigurationSection(guild);

        if (g == null) {
            return -1;
        }

        ConfigurationSection pl = g.getConfigurationSection("players");

        if (pl == null) {
            return -1;
        }

        

        return 0;
    }

    public void removePlayerFromGuild() {

    }
}
