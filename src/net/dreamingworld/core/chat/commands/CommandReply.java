package net.dreamingworld.core.chat.commands;

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

public class CommandReply implements CommandExecutor, TabCompleter {

    public CommandReply() {
        Bukkit.getPluginCommand("reply").setExecutor(this);
        Bukkit.getPluginCommand("reply").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (args.length < 1) {
            return false;
        }

        CommandSender s = DreamingWorld.lastSenders.get(sender);

        if (s == null || ((sender instanceof Player) && !((Player) sender).isOnline())) {
            sender.sendMessage(Util.formatString("$(PC)Sorry, but you did not received messages from currently online players in last session"));
            return true;
        }

        String message = "";
        for (String arg : args) {
            message += arg + " ";
        }

        CommandTell.sendMessage(sender, s, message);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        return new ArrayList<>();
    }
}
