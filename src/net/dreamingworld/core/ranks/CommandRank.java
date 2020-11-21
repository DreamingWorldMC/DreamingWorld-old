package net.dreamingworld.core.ranks;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandRank implements CommandExecutor, TabCompleter {

    public CommandRank() {
        Bukkit.getPluginCommand("rank").setExecutor(this);
        Bukkit.getPluginCommand("rank").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (args.length != 2) {
            return false;
        }

        DreamingWorld.getInstance().getRankManager().setPlayerRank(args[0], args[1]);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (args.length == 1) {
            List<String> names = new ArrayList<>();

            for (Player player : Bukkit.getOnlinePlayers()) {
                names.add(player.getName());
            }

            return Util.smartAutocomplete(names, args);
        } else if (args.length == 2) {
            return Util.smartAutocomplete(DreamingWorld.getInstance().getRankManager().getRankHierarchy(), args);
        }

        return new ArrayList<>();
    }
}
