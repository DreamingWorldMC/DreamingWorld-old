package net.dreamingworld.core.commands;

import net.dreamingworld.DreamingWorld;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class CommandSpawn implements CommandExecutor, Listener {

    Location location;

    public CommandSpawn() {

        if (Bukkit.getWorld("spawn") == null) {
            Bukkit.createWorld(new WorldCreator("spawn").environment(World.Environment.THE_END));
        }

        location = new Location(Bukkit.getWorld("spawn"), 6.5, 114.5, 8.5);

        Bukkit.getPluginCommand("spawn").setExecutor(this);

        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        ((Player) sender).teleport(location);

        return true;
    }

    @EventHandler
    public void onNewJoin(PlayerJoinEvent e) {
        if (!Bukkit.getOfflinePlayer(e.getPlayer().getName()).hasPlayedBefore()) {
            e.getPlayer().teleport(location);
        }
    }
}
