package net.dreamingworld.core.commands;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.MojangAPI;
import net.dreamingworld.core.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CommandDwsummon implements CommandExecutor, TabCompleter {

    public CommandDwsummon() {
        Bukkit.getPluginCommand("dwsummon").setExecutor(this);
        Bukkit.getPluginCommand("dwsummon").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (args.length != 2) {
            return false;
        }

        String nick = args[0];
        String id = args[1];

        Player player = Bukkit.getPlayer(nick);
        if (player == null || !player.isOnline()) {
            sender.sendMessage(Util.formatString("$(PC)Sorry, but $(SC)" + nick + " $(PC)not found or not online"));
            return true;
        }

        Entity mob = DreamingWorld.getInstance().getEntityManager().summonEntity(player.getLocation(), id);
        if (mob == null) {
            sender.sendMessage(Util.formatString("$(PC)Sorry, but $(SC)" + id + " $(PC)is not a correct DreamingWorld mob id"));
            return true;
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

            return Util.smartAutocomplete(names, args);
        } else if (args.length == 2) {
            return Util.smartAutocomplete(DreamingWorld.getInstance().getEntityManager().getIds(), args);
        }

        return null;
    }
}

