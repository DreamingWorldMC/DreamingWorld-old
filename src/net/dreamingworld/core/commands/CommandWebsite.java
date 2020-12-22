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

public class CommandWebsite implements CommandExecutor {

    public CommandWebsite() {
        Bukkit.getPluginCommand("website").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        sender.sendMessage(Util.formatString("&3Website: &bhttps://dreamingworld.net"));
        return true;
    }
}
