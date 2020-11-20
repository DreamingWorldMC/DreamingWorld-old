package net.dreamingworld.core.chat.commands;

import net.dreamingworld.core.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandTell implements CommandExecutor, TabCompleter {

    public CommandTell() {
        Bukkit.getPluginCommand("tell").setExecutor(this);
        Bukkit.getPluginCommand("tell").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (args.length < 2) {
            return false;
        }

        Player receiver = Bukkit.getPlayer(args[0]);

        if (receiver == null || !receiver.isOnline()) {
            sender.sendMessage(Util.formatString("$(PC)Sorry, but player $(SC)" + args[0] + " $(PC)not online or not found"));
            return true;
        }

        String message = "";
        for (int i = 1; i < args.length; i++) {
            message += args[i] + " ";
        }

        String senderInfo = Util.formatString("$(SC)You $(PC)to " + receiver.getDisplayName() + "&r: " + message);
        String receiverInfo = Util.formatString(sender instanceof Player ? ((Player) sender).getDisplayName() : sender.getName() + " $(PC)to $(SC)you&r: " + message);

        sender.sendMessage(senderInfo);
        receiver.sendMessage(receiverInfo);

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
        }

        return new ArrayList<>();
    }
}
