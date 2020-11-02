package net.dreamingworld.core.blocks;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.TagWizard;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
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

    @EventHandler
    public void onPlace(BlockPlaceEvent e) throws IOException {
        if (!DreamingWorld.getInstance().getItemManager().checkItemAuthenticity(e.getItemInHand(), id))
            return;

        Location loc = e.getBlock().getLocation();
        DreamingWorld.getInstance().getBlockManager().placeBlock(loc, id);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) throws IOException {
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
            Set<String> blocks = data.getConfigurationSection("blocks").getKeys(false);

            for (String block : blocks) {
                if (data.getConfigurationSection("blocks").getConfigurationSection(block).getString("id").equals(id)) {
                    String[] coords = block.split("_");
                    tick(new Location(chunk.getWorld(), Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2])));
                }
            }
        }
    }
}
