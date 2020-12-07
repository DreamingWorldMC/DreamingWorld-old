package net.dreamingworld.core.chat.commands;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandTell implements CommandExecutor, TabCompleter, Listener {

    public CommandTell() {
        Bukkit.getPluginCommand("tell").setExecutor(this);
        Bukkit.getPluginCommand("tell").setTabCompleter(this);

        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
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

        sendMessage(sender, receiver, message);

        return true;
    }

    protected static void sendMessage(CommandSender sender, CommandSender receiver, String message) {
        String senderInfo = Util.formatString("$(SC)You $(PC)to " + (receiver instanceof Player ? ((Player) receiver).getDisplayName() : "$(SC)" + receiver.getName()) + "&r: " + message);
        String receiverInfo = Util.formatString((sender instanceof Player ? ((Player) sender).getDisplayName() : "$(SC)" + sender.getName()) + " $(PC)to $(SC)you&r: " + message);

        DreamingWorld.lastSenders.put(receiver, sender);

        sender.sendMessage(senderInfo);
        receiver.sendMessage(receiverInfo);
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

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        DreamingWorld.lastSenders.remove(e.getPlayer());
    }
}
