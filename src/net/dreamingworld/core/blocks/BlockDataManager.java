package net.dreamingworld.core.blocks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.dreamingworld.DreamingWorld;
import net.minecraft.server.v1_8_R3.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BlockDataManager {

    private final Map<Chunk, Tuple<File, YamlConfiguration>> chunkFiles;

    public BlockDataManager() {
        chunkFiles = new HashMap<>();

        Bukkit.getScheduler().runTaskTimerAsynchronously(DreamingWorld.getInstance(), this::saveAll, 0, 40);
    }

    public void setBlockTag(Location location, String name, String value) {
        Map<String, String> data = getBlockData(location);

        if (data == null) {
            data = new HashMap<>();
        }

        data.put(name, value);

        setBlockData(location, data);
    }

    public String getBlockTag(Location location, String name) {
        Map<String, String> data = getBlockData(location);

        if (data == null) {
            return null;
        }

        if (data.containsKey(name)) {
            return data.get(name);
        }

        return null;
    }


    public void setBlockData(Location location, Map<String, String> data) {
        String json = new Gson().toJson(data);
        setBlockInfo(location, "data", json);
    }

    public Map<String, String> getBlockData(Location location) {
        String json = getBlockInfo(location, "data");

        if (json != null) {
            return new Gson().fromJson(json, new TypeToken<HashMap<String, String>>() {}.getType());
        }

        return null;
    }


    protected void setBlockInfo(Location location, String id, String value) {
        YamlConfiguration data = getChunkConfig(location.getChunk()).b();

        String s = location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ();

        if (data.getConfigurationSection("blocks") == null) {
            data.createSection("blocks");
        }

        data.getConfigurationSection("blocks").set(s + "." + id, value);
    }

    protected String getBlockInfo(Location location, String id) {
        YamlConfiguration data = getChunkConfig(location.getChunk()).b();

        String s = location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ();

        if (data.getConfigurationSection("blocks") != null && data.getConfigurationSection("blocks").getKeys(false).contains(s)) {
            return data.getConfigurationSection("blocks").getConfigurationSection(s).getString(id);
        }

        return null;
    }

    protected void removeBlockInfoSection(Location location) {
        YamlConfiguration data = getChunkConfig(location.getChunk()).b();

        String s = location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ();

        if (data.getConfigurationSection("blocks") != null && data.getConfigurationSection("blocks").getKeys(false).contains(s)) {
            data.getConfigurationSection("blocks").set(s, null);
        }
    }


    public Tuple<File, YamlConfiguration> getChunkConfig(Chunk chunk) {
        if (chunkFiles.containsKey(chunk)) {
            return chunkFiles.get(chunk);
        }

        File chunkFile = new File(DreamingWorld.dataDirectory + "blocks/" + chunk.getWorld().getName() + "/", chunk.getX() + "_" + chunk.getZ());
        YamlConfiguration data = YamlConfiguration.loadConfiguration(chunkFile);

        Tuple<File, YamlConfiguration> t = new Tuple<>(chunkFile, data);
        chunkFiles.put(chunk, t);

        return t;
    }

    public void saveAll() {
        for (Map.Entry<Chunk, Tuple<File, YamlConfiguration>> e : chunkFiles.entrySet()) {
            try {
                e.getValue().b().save(e.getValue().a());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
