package net.dreamingworld.core.blocks;

import net.dreamingworld.DreamingWorld;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.*;

public class BlockManager implements Listener {

    private Map<String, CustomBlock> blocks;
    private Set<Chunk> loadedChunks;

    public BlockManager() {
        blocks = new HashMap<>();
        loadedChunks = new HashSet<>();

        for (World world : Bukkit.getWorlds()) // /rl protection
            Collections.addAll(loadedChunks, world.getLoadedChunks());
    }

    public void registerBlock(CustomBlock block) {
        Bukkit.getScheduler().runTaskTimer(DreamingWorld.getInstance(), block, 0, 10);
        Bukkit.getPluginManager().registerEvents(block, DreamingWorld.getInstance());

        blocks.put(block.getId(), block);
    }


    public Set<Chunk> getLoadedChunks() {
        return loadedChunks;
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
