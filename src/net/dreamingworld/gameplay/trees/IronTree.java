package net.dreamingworld.gameplay.trees;

import net.dreamingworld.core.structures.EasyBuilder;
import net.dreamingworld.core.structures.Structure;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldType;
import org.bukkit.block.Biome;

import java.util.concurrent.ThreadLocalRandom;

public class IronTree extends Structure {

    public IronTree() {
        chance = 1;
        worlds.add(WorldType.NORMAL);
        biomes.add(Biome.PLAINS);

        blocks.add(Material.GRASS);
    }

    @Override
    public void generate(EasyBuilder builder) {
        Bukkit.getLogger().info("hello!");

        int height = ThreadLocalRandom.current().nextInt(2, 4 + 1);
        int radiusModifier = height == 4 ? 1 : 0;

        builder.buildCircle(0, height + 1, 0, 2 + radiusModifier, "iron_leaf");
        builder.buildCircle(0, height + 2, 0, 1 + radiusModifier, "iron_leaf");

        for (int i = 0; i <= height; i++) {
            builder.buildCustomBlock(0, i + 1, 0, "iron_wood");
        }
    }
}
