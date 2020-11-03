package net.dreamingworld.core.structures;

import net.dreamingworld.DreamingWorld;
import org.bukkit.Location;

import java.io.IOException;

public class EasyBuilder {

    public Location startLocation;

    public EasyBuilder(Location start) {
        startLocation = start;
    }

    public void buildCustomBlock(int x, int y, int z, String customBlock) {
        try {
            DreamingWorld.getInstance().getBlockManager().placeBlock(startLocation.add(x, y, z), customBlock);
        } catch (IOException e) {
            e.printStackTrace();
        }

        startLocation.subtract(x, y, z);
    }

    public void buildSphere(int x, int y, int z, int radius, boolean hollow, String customBlock) {
        for (int xx = x - radius; xx <= x + radius; xx++) {
            for (int yy = y - radius; yy <= y + radius; yy++) {
                for (int zz = z - radius; zz <= z + radius; zz++) {
                    double distance = Math.pow(x - xx, 2) + Math.pow(z - zz, 2) + Math.pow(y - yy, 2);

                    if (distance < Math.pow(radius, 2) && !(hollow && distance < Math.pow(radius - 1, 2)))
                        buildCustomBlock(xx, yy, zz, customBlock);
                }
            }
        }
    }
}
