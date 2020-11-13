package net.dreamingworld.gameplay.trees;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.structures.EasyBuilder;
import org.bukkit.Location;

import java.util.concurrent.ThreadLocalRandom;

public class Trees {

    public static void growIronTree(Location location) {
        EasyBuilder builder = new EasyBuilder(location.subtract(0, 1, 0));
        DreamingWorld.getInstance().getStructureManager().getStructure("iron_tree").generate(builder);
    }

    public static void growWhitePyramidTree(Location location) {
        EasyBuilder builder = new EasyBuilder(location.subtract(0, 1, 0));
        DreamingWorld.getInstance().getStructureManager().getStructure("white_pyramid_tree").generate(builder);
    }
}
