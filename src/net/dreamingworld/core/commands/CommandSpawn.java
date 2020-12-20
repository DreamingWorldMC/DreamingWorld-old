package net.dreamingworld.core.commands;

import net.dreamingworld.DreamingWorld;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class CommandSpawn implements CommandExecutor {

    Location location;

    public CommandSpawn() {
//        ConfigurationSection config = YamlConfiguration.loadConfiguration(new File(DreamingWorld.dataDirectory + "spawn.yml"));
//
//        if (Bukkit.getWorld("spawn") == null) {
//            Bukkit.createWorld(new WorldCreator("spawn").environment(World.Environment.THE_END));
//        }
//
//        if (config.getString("Location") != null ) {
//            String[] locationStr = config.getString("location").split("_");
//            location = new Location(Bukkit.getWorld("spawn"), Integer.parseInt(locationStr[0]) + 0.5, Integer.parseInt(locationStr[1]), Integer.parseInt(locationStr[2]) + 0.5);
//        } else {
//            location = new Location(Bukkit.getWorld("spawn"), 0, 0, 0);
//            config.set("location", "0_0_0");
//        }


        location = new Location(Bukkit.getWorld("spawn"), 6.5, 114.5, 8.5);

        Bukkit.getPluginCommand("spawn").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        ((Player) sender).teleport(location);

        return true;
    }
}
