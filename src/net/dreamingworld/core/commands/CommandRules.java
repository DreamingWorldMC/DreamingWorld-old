package net.dreamingworld.core.commands;

import net.dreamingworld.core.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandRules implements CommandExecutor {

    public CommandRules() {
        Bukkit.getPluginCommand("rules").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        sender.sendMessage(Util.formatString("&3Rules: &bhttps://dreamingworld.net/legal.html?t=rules"));
        return true;
    }
}
