package net.dreamingworld.core.chat.commands;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class CommandTeleport implements CommandExecutor, TabCompleter, Listener {
    Map<String, String> tpRequests;


    public CommandTeleport() {
        Bukkit.getPluginCommand("teleport").setExecutor(this);
        Bukkit.getPluginCommand("teleport").setTabCompleter(this);

        tpRequests = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (args.length < 1) {
            return false;
        }

        if (!(sender instanceof Player)) {
            return true;
        }

        Player receiver = Bukkit.getPlayer(args[0]);

        if (receiver == null || !receiver.isOnline()) {
            if (args[0].toLowerCase().charAt(0) == 's') {
                receiver = Bukkit.getPlayer(args[1]);
                if (receiver != null || receiver.isOnline()) {
                    tpRequests.put(sender.getName(), receiver.getName());
                    receiver.sendMessage(((Player) sender).getDisplayName() + " Wants to teleport to you.");
                    sender.sendMessage("You want to teleport to " + receiver.getDisplayName());
                }
                else {
                    receiver.sendMessage("You can not teleport to that player");
                }
            } else if (args[0].toLowerCase().charAt(0) == 'a') {
                if (args.length < 2) {
                    if (tpRequests.containsValue(sender.getName())) {
                        for (Map.Entry<String, String> x : tpRequests.entrySet()) {
                            if (x.getValue().equals(sender.getName())) {
                                Bukkit.getPlayer(x.getKey()).teleport(((Player) sender).getLocation());
                                tpRequests.remove(x.getKey());
                                sender.sendMessage(Bukkit.getPlayer(x.getKey()).getDisplayName() + " has been teleported to you");
                                Bukkit.getPlayer(x.getKey()).sendMessage("You have been teleported to " + ((Player) sender).getDisplayName());
                            }
                        }
                    }
                    else {
                        sender.sendMessage(ChatColor.AQUA + "Wow such empty (:");
                    }
                }
                else {
                    if (tpRequests.containsValue(sender.getName())) {
                        for (Map.Entry<String, String> x : tpRequests.entrySet()) {
                            if (x.getValue().equals(sender.getName()) && x.getKey().equals(args[1])) {
                                Bukkit.getPlayer(x.getKey()).teleport(((Player) sender).getLocation());
                                tpRequests.remove(x.getKey());
                                sender.sendMessage(Bukkit.getPlayer(x.getKey()).getDisplayName() + " has been teleported to you");
                            }
                        }
                    }
                }
            } else if (args[0].toLowerCase().charAt(0) == 'd') {

            } else if (args[0].toLowerCase().charAt(0) == 'c') {

            }
        }
        else {
            if (receiver != null || receiver.isOnline()) {
                tpRequests.put(sender.getName(), receiver.getName());
                receiver.sendMessage(((Player) sender).getDisplayName() + " Wants to teleport to you.");
                sender.sendMessage("You want to teleport to " + receiver.getDisplayName());
            }
            else {
                receiver.sendMessage("Can not tp to that player");
            }
        }



        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (args.length == 1) {
            List<String> names = new ArrayList<>();

            for (Player player : Bukkit.getOnlinePlayers()) {
                names.add(player.getName());
            }

            names.add("accept");
            names.add("send");
            names.add("deny");
            names.add("cancel");

            return Util.smartAutocomplete(names, args);
        } else if (args.length == 2) {
            List<String> names = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                names.add(player.getName());
            }
            return Util.smartAutocomplete(names, args);
        }

        return new ArrayList<>();
    }
}
