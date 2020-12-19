package net.dreamingworld.core.chat.commands;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.chat.ChannelType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class InstantMessageCommands implements CommandExecutor, TabCompleter {

    public InstantMessageCommands() {
        Bukkit.getPluginCommand("global").setExecutor(this);
        Bukkit.getPluginCommand("global").setTabCompleter(this);

        Bukkit.getPluginCommand("local").setExecutor(this);
        Bukkit.getPluginCommand("local").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("aaaaa");
            return true;
        }

        if (args.length < 1) {
            return false;
        }

        String message = ""; // Why not I use StringBuilder? Because I don`t use StringBuilder
        for (String arg : args) {
            message += arg + " ";
        }

        DreamingWorld.getInstance().getChatManager().sendMessage(message, (Player) sender, cmd.getName().equals("local") ? ChannelType.LOCAL : ChannelType.GLOBAL);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        return new ArrayList<>();
    }
}
