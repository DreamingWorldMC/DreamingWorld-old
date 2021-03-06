package net.dreamingworld.core.commands;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.MojangAPI;
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

public class CommandDwgive implements CommandExecutor, TabCompleter {

    public CommandDwgive() {
        Bukkit.getPluginCommand("dwgive").setExecutor(this);
        Bukkit.getPluginCommand("dwgive").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (args.length > 3 || args.length < 2) {
            return false;
        }

        String nick = args[0];
        String id = args[1];
        int amount = args.length == 2 ? 1 : Integer.parseInt(args[2]);

        Player player = Bukkit.getPlayer(nick);
        if (player == null || !player.isOnline()) {
            sender.sendMessage(Util.formatString("$(PC)Sorry, but $(SC)" + nick + " $(PC)not found or not online"));
            return true;
        }

        ItemStack item = DreamingWorld.getInstance().getItemManager().get(id);
        if (item == null) {
            sender.sendMessage(Util.formatString("$(PC)Sorry, but $(SC)" + id + " $(PC)is not a correct DreamingWorld item id"));
            return true;
        }

        item.setAmount(amount);
        player.getInventory().addItem(item);
        sender.sendMessage(Util.formatString("$(PC)Given $(SC)" + amount + " $(PC)pcs of $(SC)" + id + "$(PC) to $(SC)" + nick));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (args.length == 1) {
            return Util.smartAutocomplete(DreamingWorld.playerNames, args);
        } else if (args.length == 2) {
            return Util.smartAutocomplete(DreamingWorld.getInstance().getItemManager().getIds(), args);
        }

        return new ArrayList<>();
    }
}
