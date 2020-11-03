package net.dreamingworld.core.structures;

import net.dreamingworld.DreamingWorld;
import org.bukkit.Location;
import org.bukkit.Material;

import java.io.IOException;

public class EasyBuilder {

    public Location startLocation;

    public EasyBuilder(Location start) {
        startLocation = start;
    }

    public void buildCustomBlock(int x, int y, int z, String customBlock, Material material) {
        try {
            DreamingWorld.getInstance().getBlockManager().placeBlock(startLocation.add(x, y, z), customBlock);
        } catch (IOException e) {
            e.printStackTrace();
        }

        startLocation.subtract(x, y, z);
    }
}
