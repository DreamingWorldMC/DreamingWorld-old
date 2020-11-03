package net.dreamingworld.core.stractures;

import net.dreamingworld.DreamingWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;

import java.io.IOException;

public class EasyBuilder {

    public Location startLoc;

    public EasyBuilder(Location startLoc_) {
        startLoc = startLoc_;
    }

    public void buildCustomBlock(double x, double y, double z, String customBlock, Material material) {

        try {
            DreamingWorld.getInstance().getBlockManager().placeBlock(startLoc.add(x,y,z), customBlock);
        } catch (IOException e) {
            e.printStackTrace();
        }

        startLoc.getWorld().getBlockAt(startLoc).setType(material);
        startLoc.add(-x,-y,-z);

    }
}
