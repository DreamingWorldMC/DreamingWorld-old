package net.dreamingworld.core.guilds;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.Util;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Guilds implements Listener {

    private final File file;
    private final YamlConfiguration config;

    // guild save values needed to not destroy server's cpu on every /guild info and /guild top
    private List<String> top;
    private Map<String, Integer> guildPos;

    public Guilds() {
            file = new File(DreamingWorld.dataDirectory + "guilds/", "guilds.yml");
            config = YamlConfiguration.loadConfiguration(file);

            top = new ArrayList<>();
            guildPos = new HashMap<>();

            if (config.getConfigurationSection("guilds") == null) {
                config.createSection("guilds");
            }

            if (config.getConfigurationSection("chunks") == null) {
                config.createSection("chunks");
            }

            GuildInvites.initializeInvites();

            Bukkit.getScheduler().runTaskTimerAsynchronously(DreamingWorld.getInstance(), () -> { // Player actionbar update
                for (Player player : Bukkit.getOnlinePlayers()) {
                    String o = getChunkOwner(player.getLocation().getChunk());
                    String pg = getPlayerGuild(player)[0];

                    String g = o == null ? Util.formatString("&4Wilderness") : ((pg != null && pg.equals(o)) ? ChatColor.GREEN : ChatColor.GOLD) + o;

                    PacketWizard.sendActionBar(player, "[" + g + ChatColor.RESET + "]");
                }
            }, 0, 20);

            Bukkit.getScheduler().runTaskTimerAsynchronously(DreamingWorld.getInstance(), () -> { // Config save
                try {
                    config.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                GuildInvites.saveConfig();
            }, 0, 100);

            Bukkit.getScheduler().runTaskTimerAsynchronously(DreamingWorld.getInstance(), () -> { // Config save
                Map<String, Integer> localGuildPos = new HashMap<>();

                for (String x : config.getConfigurationSection("guilds").getKeys(false)) {
                    localGuildPos.put(x, getGuildPoints(x));
                }

                List<String> localTop = new ArrayList<>();

                for (Map.Entry<String, Integer> x : localGuildPos.entrySet()) {

                    for (String str : localTop) {
                        if (getGuildPoints(str) < x.getValue()) {
                            localTop.add(localTop.indexOf(str), x.getKey());
                            break;
                        }
                    }
                    if (!localTop.contains(x.getKey())) {
                        localTop.add(x.getKey());
                    }
                }
                for (String x : localTop) {
                    localGuildPos.replace(x, localTop.indexOf(x));
                }

                top = localTop;
                guildPos = localGuildPos;
            }, 0, 600);
    }


    public int createGuild(String name, Player owner) {
        Set<String> guilds = config.getConfigurationSection("guilds").getKeys(false);

        if (guilds.contains(name)) {
            return -1;
        }

        if (getPlayerGuild(owner)[0] != null) {
            return -2;
        }

        String ownerUuid = owner.getUniqueId().toString();

        ConfigurationSection guild = config.getConfigurationSection("guilds").createSection(name);
        guild.set("owner", ownerUuid);

        guild.createSection("chunks");

        addPlayerToGuild(owner, name, "owner");

        return 0;
    }

    public void removeGuild(String name) {
        ConfigurationSection g = config.getConfigurationSection("guilds");
        Set<String> guilds = g.getKeys(false);

        if (!guilds.contains(name)) {
            return;
        }

        ConfigurationSection sect = g.getConfigurationSection(name).getConfigurationSection("chunks");

        for (String world : sect.getKeys(false)) {
            for (String chunk : sect.getStringList(world)) {
                config.getConfigurationSection("chunks").getConfigurationSection(world).set(chunk, null);
            }
        }

        for (String player : GuildInvites.getInvited(name)) {
            GuildInvites.cancelInvite(player, name);
        }

        g.set(name, null);
    }


    public List<Chunk> getGuildChunkList(String name) {
        ConfigurationSection guild = config.getConfigurationSection("guilds").getConfigurationSection(name);

        if (guild == null) {
            return new ArrayList<>();
        }

        ConfigurationSection chunks = guild.getConfigurationSection("chunks");

        if (chunks == null) {
            return new ArrayList<>();
        }

        Set<String> worlds = chunks.getKeys(false);

        List<Chunk> chnks = new ArrayList<>();

        for (String w : worlds) {
            List<String> c = chunks.getStringList(w);

            for (String ch : c) {
                String[] coords = ch.split("_");
                chnks.add(Bukkit.getWorld(w).getChunkAt(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])));
            }
        }

        return chnks;
    }

    public int getGuildMaxChunks(String name) {
        if (config.getConfigurationSection("guilds").getConfigurationSection(name) == null) {
            return -1;
        }

        int c = 0;

        for (String uuid : getGuildMembers(name)) {
            if (Bukkit.getOfflinePlayer(UUID.fromString(uuid)) == null) {
                continue;
            }

            c += DreamingWorld.getInstance().getRankManager().getRankChunks(DreamingWorld.getInstance().getRankManager().getPlayerRank(UUID.fromString(uuid)));
        }

        return c;
    }


    public String getChunkOwner(Chunk chunk) {
        if (chunk.getWorld().getName().equals("spawn")) {
            return "spawn";
        }

        ConfigurationSection chunks = config.getConfigurationSection("chunks").getConfigurationSection(chunk.getWorld().getName());

        if (chunks == null) {
            return null;
        }

        return chunks.getString(chunk.getX() + "_" + chunk.getZ());
    }

    public void setChunkOwner(Chunk chunk, String guild) {
        ConfigurationSection chunks = config.getConfigurationSection("chunks").getConfigurationSection(chunk.getWorld().getName());

        if (config.getConfigurationSection("chunks") == null) {
            chunks = config.createSection("chunks");
        }
        if (chunks == null) {
            chunks = config.getConfigurationSection("chunks").createSection(chunk.getWorld().getName());
        }

        chunks.set(chunk.getX() + "_" + chunk.getZ(), guild);
    }


    public int giveChunk(Chunk chunk, String guild) {
        Set<String> guilds = config.getConfigurationSection("guilds").getKeys(false);

        if (!guilds.contains(guild)) {
            return -1;
        }

        String o = getChunkOwner(chunk);

        if (o != null) {
            return guild.equals(o) ? -2 : -3;
        }

        if (getGuildChunkList(guild).size() >= getGuildMaxChunks(guild)) {
            return -4;
        }

        setChunkOwner(chunk, guild);

        List<String> ch = config.getConfigurationSection("guilds").getConfigurationSection(guild).getConfigurationSection("chunks").getStringList(chunk.getWorld().getName());

        if (ch == null) {
            ch = new ArrayList<>();
        }

        ch.add(chunk.getX() + "_" + chunk.getZ());
        config.getConfigurationSection("guilds").getConfigurationSection(guild).getConfigurationSection("chunks").set(chunk.getWorld().getName(), ch);

        return 0;
    }

    public int removeChunk(Chunk chunk) {
        String guild = getChunkOwner(chunk);

        if (guild == null) {
            return -1;
        }

        List<String> ch = config.getConfigurationSection("guilds").getConfigurationSection(guild).getConfigurationSection("chunks").getStringList(chunk.getWorld().getName());

        if (ch == null) {
            return -2;
        }

        ch.remove(chunk.getX() + "_" + chunk.getZ());
        config.getConfigurationSection("guilds").getConfigurationSection(guild).getConfigurationSection("chunks").set(chunk.getWorld().getName(), ch);

        setChunkOwner(chunk, null);

        return 0;
    }


    public String[] getPlayerGuild(Player player) {
        return getPlayerGuild(player.getUniqueId());
    }

    public String[] getPlayerGuild(UUID uuid_) {
        Set<String> guilds = config.getConfigurationSection("guilds").getKeys(false);
        String uuid = uuid_.toString();

        String[] output = new String[2];

        for (String guild : guilds) {
            ConfigurationSection sect = config.getConfigurationSection("guilds").getConfigurationSection(guild);

            String plr = sect.getConfigurationSection("players").getString(uuid);

            if (plr != null) {
                output[0] = guild;
                output[1] = plr;
                return output;
            }
        }

        return output;
    }


    public Set<String> getGuildMembers(String guild) {
        ConfigurationSection g = config.getConfigurationSection("guilds").getConfigurationSection(guild);

        if (g == null) {
            return null;
        }

        ConfigurationSection pl = g.getConfigurationSection("players");

        if (pl == null) {
            return new HashSet<>();
        }

        return pl.getKeys(false);
    }

    public int addPlayerToGuild(Player player, String guild, String role) {
        return addPlayerToGuild(player.getUniqueId(), guild, role);
    }

    public int addPlayerToGuild(UUID uuid, String guild, String role) {
        ConfigurationSection g = config.getConfigurationSection("guilds").getConfigurationSection(guild);

        if (g == null) {
            return -1;
        }

        ConfigurationSection pl = g.getConfigurationSection("players");

        if (pl == null) {
            pl = g.createSection("players");
        }

        pl.set(uuid.toString(), role);

        return 0;
    }

    public int removePlayerFromGuild(Player player, String guild) {
        return addPlayerToGuild(player, guild, null);
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        GuildInvites.sendInviteMessages(e.getPlayer());
    }


    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        cancelPrivatized(e, e.getPlayer(), e.getBlock().getChunk());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBreak(BlockBreakEvent e) {
        cancelPrivatized(e, e.getPlayer(), e.getBlock().getChunk());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Block b = e.getClickedBlock();

        if (b != null) {
            cancelPrivatized(e, e.getPlayer(), e.getClickedBlock().getChunk());
        }
    }

    @EventHandler
    public void onWaterFlow(BlockFromToEvent e) {
        String a = getChunkOwner(e.getBlock().getChunk());
        String b = getChunkOwner(e.getToBlock().getChunk());

        if (!Objects.equals(a, b) && !(a != null && b == null)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent e) {
        for (Block block : e.blockList()) {
            if (getChunkOwner(block.getChunk()) != null) {
                e.setCancelled(true);
                break;
            }
        }
    }

    @EventHandler
    public void onBlockChange(EntityChangeBlockEvent e) {
        if (getChunkOwner(e.getBlock().getChunk()) != null) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDispense(BlockDispenseEvent e) {
        if (e.getItem().getType() == Material.WATER_BUCKET || e.getItem().getType() == Material.LAVA_BUCKET || e.getItem().getType() == Material.BUCKET) {
            Vector v = e.getVelocity();
            Vector bv = e.getBlock().getLocation().toVector();

            if (v.subtract(bv).isInAABB(new Vector(-1, -1, -1), new Vector(1, 1, 1))) { // Mafs
                Block block = v.add(bv).toLocation(e.getBlock().getWorld()).getBlock();

                String o = getChunkOwner(e.getBlock().getChunk());
                String g = getChunkOwner(block.getChunk());

                if (!Objects.equals(g, o) && !(o != null && g == null)) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent e) {
        handlePiston(e.getBlocks(), e);
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent e) {
        handlePiston(e.getBlocks(), e);
    }

    private void handlePiston(List<Block> blockList, BlockPistonEvent e) {
        if (e.getBlock() == null) {
            return;
        }

        String o = getChunkOwner(e.getBlock().getChunk());

        for (Block block : blockList) {
            String g = getChunkOwner(block.getChunk());

            if (!Objects.equals(g, o) && !(o != null && g == null)) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            cancelPrivatized(e, (Player)e.getDamager(), e.getEntity().getLocation().getChunk());
        }
    }

    private void cancelPrivatized(Cancellable e, Player player, Chunk chunk) {
        String o = getChunkOwner(chunk);
        String g = getPlayerGuild(player)[0];

        if (o != null && !Objects.equals(o, g)) {
            if (!o.equals("spawn")) {
                player.sendMessage(Util.formatString("$(PC)This chunk belongs to $(SC)" + o + "$(PC). Hands off!"));
            }

            e.setCancelled(true);
        }
    }

    public int setHome(Location location, String guild) {
        String o = getChunkOwner(location.getChunk());

        if (!guild.equals(o)) {
            return -1;
        }

        config.getConfigurationSection("guilds").getConfigurationSection(guild).set("home", location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ() + "_" + location.getWorld().getName());

        return 0;
    }

    public Location getHomeLocation(String guild) {
        String s = config.getConfigurationSection("guilds").getConfigurationSection(guild).getString("home");

        if (s == null) {
            return null;
        }

        String[] str = s.split("_");
        return new Location(Bukkit.getWorld(str[3]), Integer.parseInt(str[0]), Integer.parseInt(str[1]), Integer.parseInt(str[2]));
    }


    public int getGuildPoints(String guild) {
        String s = config.getConfigurationSection("guilds").getConfigurationSection(guild).getString("points");

        if (s == null) {
            return 0;
        } else {
            return Integer.parseInt(s);
        }
    }

    public void addGuildPoints(String guild, int points) {
        String s = config.getConfigurationSection("guilds").getConfigurationSection(guild).getString("points");
        config.getConfigurationSection("guilds").getConfigurationSection(guild).set("points", s == null ? points : Integer.parseInt(s) + points);
    }

    public int getGuildPosition(String guild) {
        return guildPos.getOrDefault(guild, -1);
     }

    public String getGuildTopPlace(int position) {
        return top.get(position);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreakBlock(BlockBreakEvent e) {
        if (getPlayerGuild(e.getPlayer())[0] != null && !e.getPlayer().getItemInHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH) &&DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getBlock().getLocation()) == null) {
            switch(e.getBlock().getType()) {
                case DIAMOND_ORE:
                    addGuildPoints(getPlayerGuild(e.getPlayer())[0], 5);
                    break;
                case LAPIS_ORE:
                    addGuildPoints(getPlayerGuild(e.getPlayer())[0], 3);
                    break;
                case EMERALD_ORE:
                    addGuildPoints(getPlayerGuild(e.getPlayer())[0], 10);
                    break;
                case COAL_ORE:
                    addGuildPoints(getPlayerGuild(e.getPlayer())[0], 1);
                    break;
                case REDSTONE_ORE:
                    addGuildPoints(getPlayerGuild(e.getPlayer())[0], 2);
                    break;
            }
        }
    }
}
