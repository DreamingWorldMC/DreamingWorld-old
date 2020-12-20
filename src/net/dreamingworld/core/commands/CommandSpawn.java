package net.dreamingworld.core.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpawn implements CommandExecutor {

    Location location;

    public CommandSpawn() {

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
