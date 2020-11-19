package net.dreamingworld.core.structures;

import net.dreamingworld.DreamingWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StructureManager {

    private Map<String, Structure> structures;

    public StructureManager() {
        for (World world : Bukkit.getWorlds()) {
            world.getPopulators().clear();
        }

        structures = new HashMap<>();
    }


    public void registerStructure(String id, Structure structure) {
        Bukkit.getPluginManager().registerEvents(structure, DreamingWorld.getInstance());

        for (World world : Bukkit.getWorlds()) {
            if (structure.worlds.contains(world.getWorldType())) {
                world.getPopulators().add(structure);
            }
        }

        structures.put(id, structure);
    }

    public Structure getStructure(String id) {
        return structures.get(id);
    }

    public List<String> getIds() {
        return new ArrayList<>(structures.keySet());
    }
}
