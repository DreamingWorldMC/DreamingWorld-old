package net.dreamingworld.core.guilds.commands;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.Util;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
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
            add(Util.formatString("$(PC)/guild help &r- $(SC)displays this message"));
            add(Util.formatString("$(PC)/guild create <name> &r- $(SC)creates new guild"));
            add(Util.formatString("$(PC)/guild join <name> &r- $(SC)adds you to guild as member (if you invited)"));
        }};

        memberUsage = new ArrayList<String>() {{
            add(Util.formatString("$(PC)/guild help &r- $(SC)displays this message"));
            add(Util.formatString("$(PC)/guild leave &r- $(SC)leave guild"));
            add(Util.formatString("$(PC)/guild pole &r- $(SC)privatizes chunk where player are standing"));
        }};


        subcommands = new ArrayList<String>() {{
            add("help");
            add("create");
            add("join");
        }};

        memberSubcommands = new ArrayList<String>() {{
            add("help");
            add("leave");
            add("pole");
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

        switch (args[0]) {

            ///////////////////////////////////////////////////////// 'HOMELESS' commands

            case ("create"):
                if (args.length != 2) {
                    sender.sendMessage(Util.formatString("&4Wrong command usage! (Guild name may not contain spaces)"));
                    sender.sendMessage(Util.formatString("&4/guild create <name>"));
                    return true;
                }

                if (args[1].length() > 32) {
                    sender.sendMessage(Util.formatString("&4Name may not be longer than 32 characters"));
                    return true;
                }

                int res = DreamingWorld.getInstance().getGuildManager().createGuild(args[1], player);

                switch (res) {
                    case (-1):
                        sender.sendMessage(Util.formatString("$(PC)Guild with name $(SC)" + args[1] + " $(PC) already exists."));
                        return true;
                    case (-2):
                        sender.sendMessage(Util.formatString("$(PC)You are already in guild. Run $(SC)/guild leave $(PC)to leave from your current guild"));
                        return true;
                    default:
                        sender.sendMessage(Util.formatString("$(PC)Created guild $(SC)" + args[1] + " $(PC)successfully."));
                        return true;
                }

            ///////////////////////////////////////////////////////// Guild members commands

            case ("leave"):
                if (args.length != 1) {
                    sender.sendMessage(Util.formatString("&4Wrong command usage!"));
                    sender.sendMessage(Util.formatString("&4/guild leave"));
                    return true;
                }

                if (!member) {
                    sender.sendMessage(Util.formatString("$(PC)You are not in guild"));
                    return true;
                }

                String name = DreamingWorld.getInstance().getGuildManager().getPlayerGuild(player)[0];

                boolean removeGuild = false;

                if (DreamingWorld.getInstance().getGuildManager().getGuildMembers(name).size() <= 1) {
                    DreamingWorld.getInstance().getGuildManager().removeGuild(name);
                    removeGuild = true;
                }

                DreamingWorld.getInstance().getGuildManager().removePlayerFromGuild(player, name);

                sender.sendMessage(Util.formatString("$(PC)You successfully left guild $(SC)" + name + "$(PC). " + (removeGuild ? "You were only member of guild, so it was removed." : "")));
                break;

            case ("pole"):
                if (args.length != 1) {
                    sender.sendMessage(Util.formatString("&4Wrong command usage!"));
                    sender.sendMessage(Util.formatString("&4/guild pole"));
                    return true;
                }

                if (!member) {
                    sender.sendMessage(Util.formatString("$(PC)You are not in guild"));
                    return true;
                }

                Chunk chunk = player.getLocation().getChunk();
                name = DreamingWorld.getInstance().getGuildManager().getPlayerGuild(player)[0];

                boolean canPole = false;

                List<Chunk> cl = DreamingWorld.getInstance().getGuildManager().getGuildChunkList(name);

                for (Chunk ch : cl) {
                    if (chunk.getBlock(0, 0, 0).getLocation().distance(ch.getBlock(0, 0, 0).getLocation()) <= 16) {
                        canPole = true;
                        break;
                    }
                }

                if (cl.size() < 1) {
                    canPole = true;
                }

                if (!canPole) {
                    sender.sendMessage(Util.formatString("$(PC)Sorry, but this chunk is too far from your territory"));
                    return true;
                }

                res = DreamingWorld.getInstance().getGuildManager().giveChunk(chunk, name);

                switch (res) {
                    case (-1):
                        sender.sendMessage(Util.formatString("$(PC)You are member of guild that does not exist..."));
                        sender.sendMessage(Util.formatString("$(PC)WELCOME TO DREAMINGWORLD ALPHA"));
                        sender.sendMessage(Util.formatString("$(PC)Write down current time and tell admin about this incident"));
                        break;
                    case (-2):
                        sender.sendMessage(Util.formatString("$(PC)This chunk already belongs to your guild"));
                        break;
                    case (-3):
                        sender.sendMessage(Util.formatString("$(PC)This chunk already belongs to $(SC)" + DreamingWorld.getInstance().getGuildManager().getChunkOwner(chunk)));
                        break;
                    default:
                        sender.sendMessage(Util.formatString("$(PC)Successfully claimed chunk at X: $(SC)" + chunk.getX() + "$(PC), Z: $(SC)" + chunk.getZ()));
                        break;
                }

                break;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("uh");
            return new ArrayList<>();
        }

        if (args.length == 1) {
            Player player = (Player) sender;

            if (isPlayerMember(player)) {
                return Util.smartAutocomplete(memberSubcommands, args);
            } else {
                return Util.smartAutocomplete(subcommands, args);
            }
        }

        return new ArrayList<>();
    }
}
