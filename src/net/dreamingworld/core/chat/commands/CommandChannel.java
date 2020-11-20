package net.dreamingworld.core.chat.commands;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.chat.ChannelType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandChannel implements CommandExecutor, TabCompleter {

    public CommandChannel() {
        Bukkit.getPluginCommand("channel").setExecutor(this);
        Bukkit.getPluginCommand("channel").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("wut");
            return true;
        }

        if (args.length != 1) {
            return false;
        }

        String channel = args[0];
        Player player = (Player) sender;

        ChannelType ct;

        switch (channel.toLowerCase().toCharArray()[0]) {
            case 'g':
                ct = ChannelType.GLOBAL;
                break;
            case 'l':
                ct = ChannelType.LOCAL;
                break;

            default:
                return false;
        }

        DreamingWorld.getInstance().getChatManager().setPlayerChannel(player, ct);
        player.sendMessage(Util.formatString("$(PC)You are now in $(SC)" + ct.toString() + " $(PC)channel"));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (args.length == 1) {
            return new ArrayList<String>() {{
                add("global");
                add("local");
            }};
        }

        return new ArrayList<>();
    }
}
