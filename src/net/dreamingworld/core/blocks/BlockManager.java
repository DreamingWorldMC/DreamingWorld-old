package net.dreamingworld.core.blocks;

import net.dreamingworld.DreamingWorld;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.*;

public class BlockManager implements Listener {

    private final Map<String, CustomBlock> blocks;
    private final Set<Chunk> loadedChunks;

    private final BlockDataManager bdm; // Bondage Domination Masochism

    public BlockManager() {
        blocks = new HashMap<>();
        loadedChunks = new HashSet<>();

        bdm = new BlockDataManager();

        for (World world : Bukkit.getWorlds()) { // /rl protection
            Collections.addAll(loadedChunks, world.getLoadedChunks());
        }
    }


    public void registerBlock(CustomBlock block) {
        Bukkit.getScheduler().runTaskTimer(DreamingWorld.getInstance(), block, 0, 10);
        Bukkit.getPluginManager().registerEvents(block, DreamingWorld.getInstance());

        blocks.put(block.getId(), block);
    }

    public String getCustomBlockAt(Location location) {
        return bdm.getBlockInfo(location, "id");
    }


    public void placeBlock(Location location, String id) {
        bdm.setBlockInfo(location, "id", id);
    }

    public boolean removeBlock(Location location, String id) {
        if (!id.equals(bdm.getBlockInfo(location, "id"))) {
            return false;
        }

        bdm.removeBlockInfoSection(location);

        boolean dropItem = location.getBlock().getType() == DreamingWorld.getInstance().getItemManager().get(id).getType();
        location.getBlock().setType(Material.AIR);

        return dropItem;
    }


    public Set<Chunk> getLoadedChunks() {
        return loadedChunks;
    }

    public BlockDataManager getBlockDataManager() {
        return bdm;
    }


    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e) {
        loadedChunks.add(e.getChunk());
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e) {
        loadedChunks.remove(e.getChunk());
    }
}
