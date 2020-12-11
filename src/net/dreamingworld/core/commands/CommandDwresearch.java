package net.dreamingworld.core.commands;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CommandDwresearch implements CommandExecutor, TabCompleter {

    public CommandDwresearch() {
        Bukkit.getPluginCommand("dwresearch").setExecutor(this);
        Bukkit.getPluginCommand("dwresearch").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (args.length != 2) {
            return false;
        }

        Player player = Bukkit.getPlayer(args[0]);

        if (player == null) {
            sender.sendMessage(Util.formatString("Sorry, but specified player not found or not online"));
            return true;
        }

        ItemStack item = DreamingWorld.getInstance().getResearchManager().getFinalResearchItem(args[1]);

        if (item == null) {
            sender.sendMessage(Util.formatString("Sorry, but specified research not found"));
            return true;
        }

        player.getInventory().addItem(item);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (args.length == 1) {
            return Util.smartAutocomplete(DreamingWorld.playerNames, args);
        } else if (args.length == 2) {
            return Util.smartAutocomplete(new ArrayList<>(DreamingWorld.getInstance().getResearchManager().getResearches().keySet()), args);
        }

        return new ArrayList<>();
    }
}
