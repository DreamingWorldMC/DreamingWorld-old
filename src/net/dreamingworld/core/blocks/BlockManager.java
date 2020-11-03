package net.dreamingworld.core.blocks;

import net.dreamingworld.DreamingWorld;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.io.File;
import java.io.IOException;
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

    public String getCustomBlockAt(Location location) {
        File chunkFile = new File(DreamingWorld.dataDirectory + "blocks/" + location.getWorld().getName() + "/", location.getChunk().getX() + "_" + location.getChunk().getZ());
        if (!chunkFile.exists())
            return null;

        YamlConfiguration data = YamlConfiguration.loadConfiguration(chunkFile);

        String s = location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ();

        if (data.getConfigurationSection("blocks") != null && data.getConfigurationSection("blocks").getKeys(false).contains(s))
            return data.getConfigurationSection("blocks").getConfigurationSection(s).getString("id");

        return null;
    }


    public void placeBlock(Location location, String id) throws IOException {
        File chunkFile = new File(DreamingWorld.dataDirectory + "blocks/" + location.getWorld().getName() + "/", location.getChunk().getX() + "_" + location.getChunk().getZ());
        YamlConfiguration data = YamlConfiguration.loadConfiguration(chunkFile);

        String s = location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ();
        data.set("blocks." + s + ".id", id);
        data.save(chunkFile);

        location.getBlock().setType(blocks.get(id).getItem().getType());
    }

    public boolean removeBlock(Location location, String id) throws IOException {
        File chunkFile = new File(DreamingWorld.dataDirectory + "blocks/" + location.getWorld().getName() + "/", location.getChunk().getX() + "_" + location.getChunk().getZ());
        YamlConfiguration data = YamlConfiguration.loadConfiguration(chunkFile);

        String s = location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ();
        if (data.getConfigurationSection("blocks") != null && data.getConfigurationSection("blocks").getKeys(false).contains(s) && data.getConfigurationSection("blocks").getConfigurationSection(s).getString("id").equals(id)) {
            data.getConfigurationSection("blocks").set(s, null);
            location.getBlock().setType(Material.AIR);

            data.save(chunkFile);

            return true;
        }

        return false;
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
