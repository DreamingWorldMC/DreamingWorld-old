package net.dreamingworld.core.structures;

import net.dreamingworld.DreamingWorld;
import org.bukkit.Location;

public class EasyBuilder {

    public Location startLocation;

    public EasyBuilder(Location start) {
        startLocation = start;
    }

    public void buildCustomBlock(int x, int y, int z, String customBlock) {
        DreamingWorld.getInstance().getBlockManager().placeBlock(startLocation.add(x, y, z), customBlock);

        startLocation.getBlock().setType(DreamingWorld.getInstance().getItemManager().get(customBlock).getType());
        startLocation.getBlock().setData((byte) DreamingWorld.getInstance().getItemManager().get(customBlock).getDurability());
        startLocation.subtract(x, y, z);
    }

    public void buildCircle(int cx, int cy, int cz, int radius, String material) {
        int r2 = radius * radius;
        for (int x = cx - radius; x <= cx + radius; x++) {
            for (int z = cz - radius; z <= cz + radius; z++) {
                if (Math.pow((cx - x), 2) + Math.pow((cz - z), 2) <= r2) {
                    buildCustomBlock(x, cy, z, material);
                }
            }
        }
    }
}
