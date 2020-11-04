package net.dreamingworld.gameplay.trees;

import net.dreamingworld.core.structures.EasyBuilder;
import org.bukkit.Location;

import java.util.concurrent.ThreadLocalRandom;

public class Trees {

    public static void growIronTree(Location location) {
        EasyBuilder builder = new EasyBuilder(location);

        int height = ThreadLocalRandom.current().nextInt(2, 4 + 1);
        int radiusModifier = height == 4 ? 1 : 0;

        builder.buildCircle(0, height, 0, 2 + radiusModifier, "iron_leaf");
        builder.buildCircle(0, height + 1, 0, 1 + radiusModifier, "iron_leaf");

        for (int i = 0; i <= height; i++)
            builder.buildCustomBlock(0, i,0,"iron_wood");
    }
}
