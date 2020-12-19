package net.dreamingworld.core.blocks;

import net.dreamingworld.DreamingWorld;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

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

    public boolean blockBreak(Block block) {
        return true;
    }


    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (!DreamingWorld.getInstance().getItemManager().checkItemAuthenticity(e.getItemInHand(), id)) {
            return;
        }

        Location loc = e.getBlock().getLocation();
        DreamingWorld.getInstance().getBlockManager().placeBlock(loc, id);

        place(e.getBlock());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Location loc = e.getBlock().getLocation();

        if (DreamingWorld.getInstance().getBlockManager().removeBlock(loc, id)) {
            e.setCancelled(true);
            if (blockBreak(e.getBlock())) {
                item.setAmount(1);
                loc.getWorld().dropItem(loc, item);
            }
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        for (Block explodedBlock : e.blockList()) {
            Location location = explodedBlock.getLocation();

            if (DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(location) != null) {
                if (DreamingWorld.getInstance().getBlockManager().removeBlock(location, id)) {
                    if (ThreadLocalRandom.current().nextBoolean()) {
                        location.getWorld().dropItem(location, item);
                    }
                }
            }
        }
    }

    private void handlePiston(List<Block> blockList, BlockPistonEvent e) {
        if (e.getBlock() == null) {
            return;
        }

        for (Block block : blockList) {
            if (DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(block.getLocation()) != null) {
                e.setCancelled(true);
                return;
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
