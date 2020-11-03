package net.dreamingworld.gameplay.trees;

import net.dreamingworld.core.structures.EasyBuilder;
import org.bukkit.Location;

import java.util.concurrent.ThreadLocalRandom;

public class Trees {

    public static void growIronTree(Location location) {
        EasyBuilder builder = new EasyBuilder(location);

        int height = ThreadLocalRandom.current().nextInt(4, 8 + 1);
        int radius = height > 5 ? 4 : 3;

        builder.buildSphere(0, height, 0, radius, false, "iron_leaf");

        for (int i = 0; i <= height; i++)
            builder.buildCustomBlock(0, i,0,"iron_wood");
    }
}
