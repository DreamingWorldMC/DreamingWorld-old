package net.dreamingworld.core.commands;

import net.dreamingworld.DreamingWorld;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandRecipes implements CommandExecutor, TabCompleter {

    public CommandRecipes() {
        Bukkit.getPluginCommand("recipes").setExecutor(this);
        Bukkit.getPluginCommand("recipes").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (args.length != 0) {
            return false;
        }

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Sorry, but you are stupid");
            return true;
        }

        DreamingWorld.getInstance().getRecipeBook().showRecipeBook((Player) sender);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        return new ArrayList<>();
    }
}
