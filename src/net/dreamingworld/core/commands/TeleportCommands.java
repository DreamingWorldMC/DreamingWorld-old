package net.dreamingworld.core.commands;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.*;

public class TeleportCommands implements CommandExecutor, TabCompleter, Listener {

    private Map<Player, Set<Player>> requests;

    public TeleportCommands() {
        Bukkit.getPluginCommand("teleport").setExecutor(this);
        Bukkit.getPluginCommand("teleport").setTabCompleter(this);

        Bukkit.getPluginCommand("teleportaccept").setExecutor(this);
        Bukkit.getPluginCommand("teleportaccept").setTabCompleter(this);

        Bukkit.getPluginCommand("teleportdeny").setExecutor(this);
        Bukkit.getPluginCommand("teleportdeny").setTabCompleter(this);

        requests = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        switch (cmd.getName()) {
            case "teleport":
                if (args.length != 1) {
                    return false;
                }

                Player receiver = Bukkit.getPlayer(args[0]);

                if (receiver == null || !receiver.isOnline()) {
                    sender.sendMessage(Util.formatString("$(PC)Sorry, but $(SC)" + args[0] + " $(PC)is not found or not online"));
                    return true;
                }

                requests.putIfAbsent(receiver, new HashSet<>());

                Set<Player> s = requests.get(receiver);

                if (s.contains(player)) {
                    player.sendMessage(Util.formatString("$(PC)Sorry, but you have already sent teleport request to this player"));
                    return true;
                }

                s.add(player);
                requests.put(receiver, s);

                sender.sendMessage(Util.formatString("$(PC)Sent teleport request to " + receiver.getDisplayName() + " $(PC)and they have 60 seconds to accept you request"));

                receiver.sendMessage(Util.formatString("$(SC)" + player.getDisplayName() + " $(PC)wants to teleport to you."));
                PacketWizard.tellraw(receiver, "[\"\",{\"text\":\"You can \",\"color\":\"green\"},{\"text\":\"accept\",\"color\":\"aqua\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/tpaccept " + player.getName() + "\"}},{\"text\":\", \",\"color\":\"green\"},{\"text\":\"deny\",\"color\":\"aqua\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/tpdeny " + player.getName() + "\"}},{\"text\":\", \",\"color\":\"green\"},{\"text\":\"accept all requests\",\"color\":\"aqua\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/tpaccept\"}},{\"text\":\" or \",\"color\":\"green\"},{\"text\":\"deny all requests\",\"color\":\"aqua\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/tpdeny\"}}]");

                Bukkit.getScheduler().runTaskLaterAsynchronously(DreamingWorld.getInstance(), () -> {
                    Set<Player> set = requests.get(receiver);

                    if (set.contains(player)) {
                        set.remove(player);
                        requests.put(receiver, set);
                    }

                    if (player.isOnline()) {
                        player.sendMessage(Util.formatString("$(PC)Your teleport request to " + receiver.getDisplayName() + " $(PC)was expired"));
                    }
                }, 1200);

                return true;

            case "teleportaccept":
                if (args.length > 1) {
                    return false;
                }

                s = requests.get(player);

                if (args.length == 1) {
                    Player p = Bukkit.getPlayer(args[0]);

                    if (p == null) {
                        player.sendMessage(Util.formatString("$(PC)Player not found"));
                        return true;
                    }

                    if (!s.contains(p)) {
                        player.sendMessage(Util.formatString("$(PC)This player did not send you teleport request"));
                        return true;
                    }

                    s.remove(p);
                    requests.put(player, s);

                    if (!p.isOnline()) {
                        player.sendMessage(Util.formatString("$(PC)This player is not online"));
                        return true;
                    }

                    player.sendMessage(Util.formatString("$(PC)Teleporting " + p.getDisplayName() + " $(PC)to you"));
                    p.sendMessage(Util.formatString("$(PC)Teleporting to " + player.getDisplayName()));

                    p.teleport(player);
                } else {
                    for (Player p : s) {
                        if (!p.isOnline()) {
                            return true;
                        }

                        p.sendMessage(Util.formatString("$(PC)Teleporting to " + player.getDisplayName()));

                        p.teleport(player);
                    }

                    player.sendMessage(Util.formatString("$(PC)Accepted all teleport requests"));

                    s.clear();
                    requests.put(player, s);
                }

                return true;

            case "teleportdeny":
                if (args.length > 1) {
                    return false;
                }

                s = requests.get(player);

                if (args.length == 1) {
                    Player p = Bukkit.getPlayer(args[0]);

                    if (p == null) {
                        player.sendMessage(Util.formatString("$(PC)Player not found"));
                        return true;
                    }

                    if (!s.contains(p)) {
                        player.sendMessage(Util.formatString("$(PC)This player did not send you teleport request"));
                        return true;
                    }

                    s.remove(p);
                    requests.put(player, s);

                    if (!p.isOnline()) {
                        return true;
                    }

                    player.sendMessage(Util.formatString("$(PC)You have been rejected " + p.getDisplayName() + "$(SC)`s $(PC)teleport request"));
                    p.sendMessage(Util.formatString("$(PC)Your teleportation request to " + player.getDisplayName() + " $(PC)was rejected"));
                } else {
                    for (Player p : s) {
                        if (!p.isOnline()) {
                            return true;
                        }

                        p.sendMessage(Util.formatString("$(PC)Your teleportation request to " + player.getDisplayName() + " $(PC)was rejected"));
                    }

                    player.sendMessage(Util.formatString("$(PC)Rejected all teleport requests"));

                    s.clear();
                    requests.put(player, s);
                }

                return true;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (!(sender instanceof Player)) {
            return new ArrayList<>();
        }

        if (args.length == 1) {
            switch (cmd.getName()) {
                case "teleport":
                    List<String> names = new ArrayList<>();

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        names.add(player.getName());
                    }

                    return names;

                default:
                    names = new ArrayList<>();

                    for (Player player : requests.get(sender)) {
                        names.add(player.getName());
                    }

                    return names;
            }
        }

        return new ArrayList<>();
    }
}
