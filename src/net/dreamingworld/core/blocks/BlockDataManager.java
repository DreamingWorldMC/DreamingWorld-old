package net.dreamingworld.core.blocks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.dreamingworld.DreamingWorld;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BlockDataManager {

    public BlockDataManager() {

    }


    public void setBlockTag(Location location, String name, String value) {
        Map<String, String> data = getBlockData(location);

        if (data == null)
            data = new HashMap<>();

        data.put(name, value);

        setBlockData(location, data);
    }

    public String getBlockTag(Location location, String name) {
        Map<String, String> data = getBlockData(location);

        if (data.containsKey(name))
            return data.get(name);

        return null;
    }


    public void setBlockData(Location location, Map<String, String> data) {
        String json = new Gson().toJson(data);
        setBlockInfo(location, "data", json);
    }

    public Map<String, String> getBlockData(Location location) {
        String json = getBlockInfo(location, "data");

        if (json != null)
            return new Gson().fromJson(json, new TypeToken<HashMap<String, String>>() {}.getType());

        return null;
    }


    protected void setBlockInfo(Location location, String id, String value) {
        File chunkFile = new File(DreamingWorld.dataDirectory + "blocks/" + location.getWorld().getName() + "/", location.getChunk().getX() + "_" + location.getChunk().getZ());
        YamlConfiguration data = YamlConfiguration.loadConfiguration(chunkFile);

        String s = location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ();

        if (data.getConfigurationSection("blocks") != null)
            data.getConfigurationSection("blocks").set(s + "." + id, value);

        try {
            data.save(chunkFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String getBlockInfo(Location location, String id) {
        File chunkFile = new File(DreamingWorld.dataDirectory + "blocks/" + location.getWorld().getName() + "/", location.getChunk().getX() + "_" + location.getChunk().getZ());
        if (!chunkFile.exists())
            return null;

        YamlConfiguration data = YamlConfiguration.loadConfiguration(chunkFile);

        String s = location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ();

        if (data.getConfigurationSection("blocks") != null && data.getConfigurationSection("blocks").getKeys(false).contains(s))
            return data.getConfigurationSection("blocks").getConfigurationSection(s).getString(id);

        return null;
    }

    protected void removeBlockInfoSection(Location location) {
        File chunkFile = new File(DreamingWorld.dataDirectory + "blocks/" + location.getWorld().getName() + "/", location.getChunk().getX() + "_" + location.getChunk().getZ());
        YamlConfiguration data = YamlConfiguration.loadConfiguration(chunkFile);

        String s = location.getBlockX() + "_" + location.getBlockY() + "_" + location.getBlockZ();
        if (data.getConfigurationSection("blocks") != null && data.getConfigurationSection("blocks").getKeys(false).contains(s))
            data.getConfigurationSection("blocks").set(s, null);

        try {
            data.save(chunkFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
