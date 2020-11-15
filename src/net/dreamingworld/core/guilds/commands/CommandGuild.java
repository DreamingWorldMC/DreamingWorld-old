package net.dreamingworld.core.guilds.commands;

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

public class CommandGuild implements CommandExecutor, TabCompleter {

    private List<String> usage;
    private List<String> memberUsage;

    private List<String> subcommands;
    private List<String> memberSubcommands;

    public CommandGuild() {
        Bukkit.getPluginCommand("guild").setExecutor(this);
        Bukkit.getPluginCommand("guild").setTabCompleter(this);

        usage = new ArrayList<String>() {{
            add(Util.formatString("$(PC)/guild help <name> &r- $(SC)displays this message"));
            add(Util.formatString("$(PC)/guild create <name> &r- $(SC)creates new guild"));
            add(Util.formatString("$(PC)/guild join <name> &r- $(SC)adds you to guild as member (if you invited)"));
        }};

        memberUsage = new ArrayList<String>() {{
            add(Util.formatString("$(PC)/guild help <name> &r- $(SC)displays this message"));
            add(Util.formatString("$(PC)/guild leave &r- $(SC)leave guild"));
        }};


        subcommands = new ArrayList<String>() {{
            add("help");
            add("create");
            add("join");
        }};

        memberSubcommands = new ArrayList<String>() {{
            add("help");
            add("leave");
        }};
    }


    private boolean isPlayerMember(Player player) {
        return DreamingWorld.getInstance().getGuildManager().getPlayerGuild(player)[0] != null;
    }

    private void sendUsage(Player player, List<String> usg) {
        for (String line : usg) {
            player.sendMessage(line);
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("hvat?");
            return true;
        }

        Player player = (Player) sender;
        boolean member = isPlayerMember(player);

        if (args.length < 1 || args[0].equals("help")) {
            sendUsage(player, member ? memberUsage : usage);
            return true;
        }

        if (args[0].equals("leave")) {
            if (!member) {
                sender.sendMessage(Util.formatString("$(PC)You are not in guild"));
                return true;
            }


        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("uh");
            return null;
        }

        if (args.length == 1) {
            Player player = (Player) sender;

            if (isPlayerMember(player)) {
                return memberSubcommands;
            } else {
                return subcommands;
            }
        }

        return null;
    }
}
