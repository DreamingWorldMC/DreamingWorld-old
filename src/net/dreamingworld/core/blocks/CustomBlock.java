package net.dreamingworld.core.blocks;

import net.dreamingworld.DreamingWorld;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Set;

public abstract class CustomBlock implements Runnable, Listener {

    protected String id;
    protected ItemStack item;

    public CustomBlock() {

    }

    public ItemStack getItem() {
        return item;
    }

    public String getId() {
        return id;
    }


    public abstract void tick(Location location);

    public void place(Block block) { }


    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (!DreamingWorld.getInstance().getItemManager().checkItemAuthenticity(e.getItemInHand(), id))
            return;

        Location loc = e.getBlock().getLocation();
        DreamingWorld.getInstance().getBlockManager().placeBlock(loc, id);

        place(e.getBlock());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Location loc = e.getBlock().getLocation();

        if (DreamingWorld.getInstance().getBlockManager().removeBlock(loc, id)) {
            e.setCancelled(true);
            loc.getBlock().getLocation().getWorld().dropItem(loc.getBlock().getLocation(), item);
        }
    }

    @Override
    public void run() {
        for (Chunk chunk : DreamingWorld.getInstance().getBlockManager().getLoadedChunks()) {
            File chunkFile = new File(DreamingWorld.dataDirectory + "blocks/" + chunk.getWorld().getName() + "/", chunk.getX() + "_" + chunk.getZ());
            if (!chunkFile.exists())
                continue;

            YamlConfiguration data = YamlConfiguration.loadConfiguration(chunkFile);

            ConfigurationSection blocksSection = data.getConfigurationSection("blocks");

            if (blocksSection == null)
                return;

            Set<String> blocks = blocksSection.getKeys(false);

            for (String block : blocks) {
                if (id.equals(data.getConfigurationSection("blocks").getConfigurationSection(block).getString("id"))) {
                    String[] coords = block.split("_");
                    tick(new Location(chunk.getWorld(), Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2])));
                }
            }
        }
    }
}
