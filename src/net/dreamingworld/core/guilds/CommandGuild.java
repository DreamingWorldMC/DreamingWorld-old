package net.dreamingworld.core.guilds;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.MojangAPI;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.guilds.GuildInvites;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandGuild implements CommandExecutor, TabCompleter {

    private final List<String> usage;
    private final List<String> memberUsage;
    private final List<String> ownerUsage;

    private final List<String> subcommands;
    private final List<String> memberSubcommands;
    private final List<String> ownerSubcommands;

    public CommandGuild() {
        Bukkit.getPluginCommand("guild").setExecutor(this);
        Bukkit.getPluginCommand("guild").setTabCompleter(this);

        usage = new ArrayList<String>() {{
            add(Util.formatString("$(PC)/guild help &r- $(SC)displays this message"));
            add(Util.formatString("$(PC)/guild create <name> &r- $(SC)creates new guild"));
            add(Util.formatString("$(PC)/guild join <name> $(SC)- adds you to guild as member (if you invited)"));
            add(Util.formatString("$(PC)/guild reject <name> &r- $(SC)rejects invite to guild"));
        }};

        memberUsage = new ArrayList<String>() {{
            add(Util.formatString("$(PC)/guild help $(SC)- displays this message"));
            add(Util.formatString("$(PC)/guild leave $(SC)- leave guild"));
            add(Util.formatString("$(PC)/guild pole $(SC)- privatizes chunk where player are standing"));
            add(Util.formatString("$(PC)/guild invite <send|cancel> <player> $(SC)- invites (cancels invitation) player to guild"));
            add(Util.formatString("$(PC)/guild info $(SC)- prints guild info"));
            add(Util.formatString("$(PC)/guild home $(SC)- Moves you to your guild's home"));
        }};

        ownerUsage = new ArrayList<String>() {{
            addAll(memberUsage);
            add(Util.formatString("$(PC)/guild abandon $(SC)- chunk where you are standing will not belong to your guild..."));
            add(Util.formatString("$(PC)/guild kick <player> $(SC)- kicks specified player from guild"));
            add(Util.formatString("$(PC)/guild transfer <player> $(SC)- transfers guild ownership to player"));
            add(Util.formatString("$(PC)/guild sethome $(SC)- Sets home of your guild"));
        }};


        subcommands = new ArrayList<String>() {{
            add("help");
            add("create");
            add("join");
            add("reject");
        }};

        memberSubcommands = new ArrayList<String>() {{
            add("help");
            add("leave");
            add("pole");
            add("invite");
            add("info");
            add("home");
        }};

        ownerSubcommands = new ArrayList<String>() {{
            addAll(memberSubcommands);
            add("abandon");
            add("kick");
            add("transfer");
            add("sethome");
        }};
    }


    private boolean isPlayerMember(Player player) {
        return DreamingWorld.getInstance().getGuildManager().getPlayerGuild(player)[0] != null;
    }

    private boolean isPlayerOwner(Player player) {
        return DreamingWorld.getInstance().getGuildManager().getPlayerGuild(player)[1].equals("owner");
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

        String[] d = DreamingWorld.getInstance().getGuildManager().getPlayerGuild(player);

        boolean member = d[0] != null;
        boolean isOwner = false;

        if (member) {
            if ("owner".equals(d[1])) {
                isOwner = true;
            }
        }

        if (args.length < 1 || args[0].equals("help")) {
            sendUsage(player, !member ? usage : ("owner".equals(DreamingWorld.getInstance().getGuildManager().getPlayerGuild(player)[1]) ? ownerUsage : memberUsage));
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

            case ("join"):
                if (args.length != 2) {
                    sender.sendMessage(Util.formatString("&4Wrong command usage!"));
                    sender.sendMessage(Util.formatString("&4/guild join <name>"));
                    return true;
                }

                if (member) {
                    sender.sendMessage(Util.formatString("$(PC)Sorry, but you are already in a guild"));
                    return true;
                }

                if (!GuildInvites.isInvited(player.getUniqueId(), args[1])) {
                    sender.sendMessage(Util.formatString("$(PC)Sorry, but you were not invited to this guild!"));
                    return true;
                }

                res = DreamingWorld.getInstance().getGuildManager().addPlayerToGuild(player, args[1], "member");

                if (res != 0) {
                    sender.sendMessage(Util.formatString("$(PC)Sorry, but specified guild not found!"));
                    return true;
                }

                sender.sendMessage(Util.formatString("$(PC)You have successfully joined $(SC)" + args[1]));

                for (String pl : DreamingWorld.getInstance().getGuildManager().getGuildMembers(args[1])) {
                    Player p = Bukkit.getPlayer(UUID.fromString(pl));

                    if (p == null) {
                        continue;
                    }

                    if (player.getName().equals(p.getName())) {
                        continue;
                    }

                    p.sendMessage(Util.formatString(player.getDisplayName() + " $(PC)has joined the guild"));
                }

                GuildInvites.cancelInvite(player.getUniqueId(), args[1]);
                return true;

            case ("reject"):
                if (args.length != 2) {
                    sender.sendMessage(Util.formatString("&4Wrong command usage!"));
                    sender.sendMessage(Util.formatString("&4/guild reject <name>"));
                    return true;
                }

                if (!GuildInvites.isInvited(player.getUniqueId(), args[1])) {
                    sender.sendMessage(Util.formatString("$(PC)Sorry, but you were not invited to this guild!"));
                    return true;
                }

                sender.sendMessage(Util.formatString("$(PC)You have successfully rejected invitation to $(SC)" + args[1]));
                GuildInvites.cancelInvite(player.getUniqueId(), args[1]);

                return true;

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

                String name = d[0];

                boolean removeGuild = false;

                if (DreamingWorld.getInstance().getGuildManager().getGuildMembers(name).size() <= 1) {
                    DreamingWorld.getInstance().getGuildManager().removeGuild(name);
                    removeGuild = true;
                }

                DreamingWorld.getInstance().getGuildManager().removePlayerFromGuild(player, name);

                sender.sendMessage(Util.formatString("$(PC)You successfully left guild $(SC)" + name + "$(PC). " + (removeGuild ? "You were only member of guild, so it was removed." : "")));
                return true;

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
                name = d[0];

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
                    case (-4):
                        sender.sendMessage(Util.formatString("$(PC)Your guild already has max count of private chunks! Invite new members to increase this limit"));
                        break;
                    default:
                        sender.sendMessage(Util.formatString("$(PC)Successfully claimed chunk at X: $(SC)" + chunk.getX() + "$(PC), Z: $(SC)" + chunk.getZ()));
                        break;
                }

                break;

            case ("invite"):
                if (args.length != 3) {
                    sender.sendMessage(Util.formatString("&4Wrong command usage!"));
                    sender.sendMessage(Util.formatString("&4/guild invite <send|cancel> <player>"));
                    return true;
                }

                if (!member) {
                    sender.sendMessage(Util.formatString("$(PC)You are not in guild"));
                    return true;
                }

                String action = args[1];

                switch (action.toLowerCase()) {
                    case ("send"):
                        UUID uuid = MojangAPI.getPlayerUUID(args[2]);

                        if (uuid == null) {
                            sender.sendMessage(Util.formatString("$(PC)Sorry, but player $(SC)" + args[2] + " $(PC)not found in Mojang and KGB databases"));
                            return true;
                        }

                        String gld = DreamingWorld.getInstance().getGuildManager().getPlayerGuild(uuid)[0];

                        if (gld != null) {
                            sender.sendMessage(Util.formatString("$(PC)Sorry, but $(SC)" + Bukkit.getOfflinePlayer(uuid).getName() + " $(PC)is already member of " + (d[0].equals(gld) ? "your" : "another") + " guild"));
                            return true;
                        }

                        res = GuildInvites.addInvite(uuid, DreamingWorld.getInstance().getGuildManager().getPlayerGuild(player)[0]);

                        switch (res) {
                            case (-1):
                                sender.sendMessage(Util.formatString("$(PC)Sorry, but this player is already invited to your guild"));
                                return true;
                            case (-2):
                                sender.sendMessage(Util.formatString("$(PC)Sorry, but specified player can`t be invited to $(SC)more than 15 guilds $(PC)at the same time"));
                                return true;
                            case (-3):
                                sender.sendMessage(Util.formatString("$(PC)Sorry, but guild can`t have more than $(SC)30 pending invites"));
                                return true;

                            default:
                                sender.sendMessage(Util.formatString("$(PC)Invite successfully sent to $(SC)" + args[2]));
                                return true;
                        }

                    case ("cancel"):
                        res = GuildInvites.cancelInvite(args[2], DreamingWorld.getInstance().getGuildManager().getPlayerGuild(player)[0]);

                        switch (res) {
                            case (-1):
                                sender.sendMessage(Util.formatString("$(PC)Sorry, but player $(SC)" + args[2] + " $(PC)not found in whole Minecraft!"));
                                return true;
                            case (-2):
                                sender.sendMessage(Util.formatString("$(PC)Sorry, but this player was not invited to your guild"));
                                return true;

                            default:
                                sender.sendMessage(Util.formatString("$(PC)Invite successfully cancelled"));
                                return true;
                        }

                    default:
                        sender.sendMessage(Util.formatString("&4Wrong command usage!"));
                        sender.sendMessage(Util.formatString("&4/guild invite <send|cancel> <player>"));
                        return true;
                }

            case ("info"):
                if (args.length != 1) {
                    sender.sendMessage(Util.formatString("&4Wrong command usage!"));
                    sender.sendMessage(Util.formatString("&4/guild info"));
                    return true;
                }

                if (!member) {
                    sender.sendMessage(Util.formatString("$(PC)You are not in guild"));
                    return true;
                }

                Guilds gm = DreamingWorld.getInstance().getGuildManager();

                String guild = gm.getPlayerGuild(player)[0];

                String owner = null;
                String members = null;

                for (String m : gm.getGuildMembers(guild)) {
                    OfflinePlayer mem = Bukkit.getOfflinePlayer(UUID.fromString(m));

                    String rank = gm.getPlayerGuild(UUID.fromString(m))[1];

                    if ("owner".equals(rank)) {
                        if (owner == null) {
                            owner = "";
                        }

                        owner += (mem.isOnline() ? ChatColor.WHITE : ChatColor.GRAY) + mem.getName() + " ";
                    } else {
                        if (members == null) {
                            members = "";
                        }

                        members += (mem.isOnline() ? ChatColor.WHITE : ChatColor.GRAY) + mem.getName() + " ";
                    }
                }

                sender.sendMessage(Util.formatString("$(SC)" + guild + " $(PC)guild info:"));
                sender.sendMessage(Util.formatString("$(PC)Owner: " + (owner == null ? "&rn/a" : owner)));
                sender.sendMessage(Util.formatString("$(PC)Members: " + (members == null ? "&rn/a" : members)));
                sender.sendMessage(Util.formatString("$(PC)Chunks: &r" + gm.getGuildChunkList(guild).size() + "/" + gm.getGuildMaxChunks(guild)));

                return true;

            case ("home"):
                if (args.length != 1) {
                    sender.sendMessage(Util.formatString("&4Wrong command usage!"));
                    sender.sendMessage(Util.formatString("&4/guild home"));
                    return true;
                }

                if (!member) {
                    sender.sendMessage(Util.formatString("$(PC)You are not in guild"));
                    return true;
                }

                Location loc = DreamingWorld.getInstance().getGuildManager().getHomeLocation(d[0]);

                if (loc == null) {
                    sender.sendMessage(Util.formatString("$(PC)Sorry, but your guild does not have a home"));
                    return true;
                }

                player.teleport(loc);
                return true;

            ///////////////////////////////////////////////////////// Guild owners commands

            case ("abandon"):
                if (args.length != 1) {
                    sender.sendMessage(Util.formatString("&4Wrong command usage!"));
                    sender.sendMessage(Util.formatString("&4/guild abandon"));
                    return true;
                }

                if (!member) {
                    sender.sendMessage(Util.formatString("$(PC)You are not in guild"));
                    return true;
                }

                chunk = player.getLocation().getChunk();

                if (!isOwner) {
                    sender.sendMessage(Util.formatString("$(PC)Sorry, but you are not owner of this guild"));
                    return true;
                }

                String o = DreamingWorld.getInstance().getGuildManager().getChunkOwner(chunk);
                String g = d[0];

                if (o != null && !o.equals(g)) {
                    sender.sendMessage(Util.formatString("$(PC)Sorry, but this chunk does not belong to your guild"));
                    return true;
                }

                res = DreamingWorld.getInstance().getGuildManager().removeChunk(chunk);

                switch (res) {
                    case (-1):
                        sender.sendMessage(Util.formatString("$(PC)Sorry, but this chunk is already..."));
                        sender.sendMessage(Util.formatString(" &5&oLost in a Roman &lwilderness &r&5&oof pain"));
                        break;
                    case (-2):
                        sender.sendMessage(Util.formatString("$(PC)Sorry, but this chunk does not belong to your guild"));
                        break;
                    case (0):
                        sender.sendMessage(Util.formatString("$(PC)Successfully abandoned chunk at X: $(SC)" + chunk.getX() + "$(PC), Z: $(SC)" + chunk.getZ()));
                        break;
                }

                return true;

            case ("kick"):
                if (args.length != 2) {
                    sender.sendMessage(Util.formatString("&4Wrong command usage!"));
                    sender.sendMessage(Util.formatString("&4/guild kick <player>"));
                    return true;
                }

                if (!member) {
                    sender.sendMessage(Util.formatString("$(PC)You are not in guild"));
                    return true;
                }

                if (!isOwner) {
                    sender.sendMessage(Util.formatString("$(PC)Sorry, but you are not owner of this guild"));
                    return true;
                }

                OfflinePlayer kick = Bukkit.getOfflinePlayer(args[1]);

                if (kick == null) {
                    sender.sendMessage(Util.formatString("$(PC)Sorry, but specified player not found"));
                    return true;
                }

                g = DreamingWorld.getInstance().getGuildManager().getPlayerGuild(kick.getUniqueId())[0];

                if (!d[0].equals(g)) {
                    sender.sendMessage(Util.formatString("$(PC)Sorry, but this player is not in your guild"));
                    return true;
                }

                DreamingWorld.getInstance().getGuildManager().addPlayerToGuild(kick.getUniqueId(), d[0], null);

                sender.sendMessage(Util.formatString("$(PC)Kicked $(SC)" + kick.getName() + " $(PC)from guild"));
                kick.getPlayer().sendMessage(Util.formatString("$(PC)You have been kicked from your guild"));

                for (String pl : DreamingWorld.getInstance().getGuildManager().getGuildMembers(d[0])) {
                    Player p = Bukkit.getPlayer(UUID.fromString(pl));

                    if (p == null) {
                        continue;
                    }

                    p.sendMessage(Util.formatString(kick.getPlayer().getDisplayName() + " $(PC)is kicked from guild"));
                }

                break;

            case ("transfer"):
                if (args.length != 2) {
                    sender.sendMessage(Util.formatString("&4Wrong command usage!"));
                    sender.sendMessage(Util.formatString("&4/guild transfer <player>"));
                    return true;
                }

                if (!member) {
                    sender.sendMessage(Util.formatString("$(PC)You are not in guild"));
                    return true;
                }

                if (!isOwner) {
                    sender.sendMessage(Util.formatString("$(PC)Sorry, but you are not owner of this guild"));
                    return true;
                }

                OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);

                if (p == null) {
                    sender.sendMessage(Util.formatString("$(PC)Sorry, but specified player not found"));
                    return true;
                }

                String gld = DreamingWorld.getInstance().getGuildManager().getPlayerGuild(p.getUniqueId())[0];

                if (!gld.equals(d[0])) {
                    sender.sendMessage(Util.formatString("$(PC)Sorry, but specified player is not a member of your guild"));
                    return true;
                }

                if (p.getUniqueId().equals(player.getUniqueId())) {
                    sender.sendMessage(Util.formatString("$(PC)Sorry, but specified player is $(SC)YOU"));
                    return true;
                }

                sender.sendMessage(Util.formatString("$(PC)Transferred guild ownership to $(SC)" + p.getName()));
                p.getPlayer().sendMessage(Util.formatString("$(PC)You are a new guild owner! Congrats!!!!"));

                DreamingWorld.getInstance().getGuildManager().addPlayerToGuild(player.getUniqueId(), d[0], "member");
                DreamingWorld.getInstance().getGuildManager().addPlayerToGuild(p.getUniqueId(), d[0], "owner");

                return true;

            case ("sethome"):
                if (args.length != 1) {
                    sender.sendMessage(Util.formatString("&4Wrong command usage!"));
                    sender.sendMessage(Util.formatString("&4/guild sethome"));
                    return true;
                }

                if (!member) {
                    sender.sendMessage(Util.formatString("$(PC)You are not in guild"));
                    return true;
                }

                if (!isOwner) {
                    sender.sendMessage(Util.formatString("$(PC)Sorry, but you are not owner of this guild"));
                    return true;
                }

                res = DreamingWorld.getInstance().getGuildManager().setHome(player.getLocation(), d[0]);

                if (res == -1) {
                    sender.sendMessage(Util.formatString("$(PC)Sorry, but this chunk does not belong to your guild"));
                } else {
                    sender.sendMessage(Util.formatString("$(PC)Guild home successfully set"));
                }

                return true;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLine, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("uh");
            return new ArrayList<>();
        }

        Player player = (Player) sender;

        if (args.length == 1) {
            if (isPlayerMember(player)) {
                String rank = DreamingWorld.getInstance().getGuildManager().getPlayerGuild(player)[1];

                if ("owner".equals(rank)) {
                    return Util.smartAutocomplete(ownerSubcommands, args);
                } else {
                    return Util.smartAutocomplete(memberSubcommands, args);
                }
            } else {
                return Util.smartAutocomplete(subcommands, args);
            }
        } else if (args.length == 2) {
            if (args[0].equals("invite")) {
                if (isPlayerMember(player)) {
                    return Util.smartAutocomplete(new ArrayList<String>() {{
                        add("send");
                        add("cancel");
                    }}, args);
                }
            } else if (args[0].equals("join") || args[0].equals("reject")) {
                if (!isPlayerMember(player)) {
                    return Util.smartAutocomplete(GuildInvites.getPlayerInvites(player.getUniqueId()), args);
                }
            } else if (args[0].equals("kick") || args[0].equals("transfer")) {
                if (isPlayerMember(player) && isPlayerOwner(player)) {
                    List<String> a = new ArrayList<>();

                    for (String uuid : DreamingWorld.getInstance().getGuildManager().getGuildMembers(DreamingWorld.getInstance().getGuildManager().getPlayerGuild(player)[0])) {
                        OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);

                        if (p == null) {
                            continue;
                        }

                        if (p.getUniqueId().equals(player.getUniqueId())) {
                            continue;
                        }

                        a.add(p.getName());
                    }

                    return a;
                }
            }
        } else if (args.length == 3) {
            if (args[0].equals("invite")) {
                if (args[1].equals("send")) {
                    List<String> a = new ArrayList<>();

                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (!isPlayerMember(p) && !GuildInvites.isInvited(p.getName(), DreamingWorld.getInstance().getGuildManager().getPlayerGuild(player)[0])) {
                            a.add(p.getName());
                        }
                    }

                    return a;
                } else if (args[1].equals("cancel")) {
                    List<String> a = new ArrayList<>();

                    for (String uuid : GuildInvites.getInvited(DreamingWorld.getInstance().getGuildManager().getPlayerGuild(player)[0])) {
                        String n = GuildInvites.getNick(uuid);

                        if (n != null) {
                            a.add(n);
                        }
                    }

                    return a;
                }
            }
        }

        return new ArrayList<>();
    }
}
