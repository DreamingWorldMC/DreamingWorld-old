package net.dreamingworld.gameplay.trees;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.structures.EasyBuilder;
import net.dreamingworld.core.structures.Structure;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldType;
import org.bukkit.block.Biome;

import java.util.concurrent.ThreadLocalRandom;

public class WhitePyramidTree extends Structure {
    public WhitePyramidTree() {
        chance = 2;
        worlds.add(WorldType.NORMAL);
        biomes.add(Biome.PLAINS);

        blocks.add(Material.GRASS);
    }

    @Override
    public void generate(EasyBuilder builder) {
        Bukkit.getScheduler().runTaskAsynchronously(DreamingWorld.getInstance(), () -> {
            int height = ThreadLocalRandom.current().nextInt(4, 20 + 1);
            int finalHeight = 0;

            for (int i = 0; i <= height / 2 ; i++) {
                builder.buildCustomBlock(0, i + 1, 0, "white_log");
            }

            for (int i = 0 ; i <= height * 4; i+=4) {
                builder.buildCustomBlock(0, i - 1, 0, "white_log");
                builder.buildCustomBlock(0, i - 2, 0, "white_log");
                builder.buildCustomBlock(0, i - 3, 0, "white_log");
                builder.buildCustomBlock(0, i - 4, 0, "white_log");
                for (int i2 = 1; i2 < height - (i/4); i2++) {
                    builder.buildCustomBlock(i2, i2 + (height / 4) + i, 0, "white_log");
                    builder.buildCustomBlock(i2, i2 + 1 + (height / 4) + i, 0, "holy_leaf");

                    builder.buildCustomBlock(-i2, i2 + (height / 4) + i, 0, "white_log");
                    builder.buildCustomBlock(-i2, i2 + 1 + (height / 4) + i, 0, "holy_leaf");

                    builder.buildCustomBlock(0, i2 + (height / 4) + i, i2, "white_log");
                    builder.buildCustomBlock(0, i2 + 1 + (height / 4) + i, i2, "holy_leaf");

                    builder.buildCustomBlock(0, i2 + (height / 4) + i, -i2, "white_log");
                    builder.buildCustomBlock(0, i2 + 1 + (height / 4) + i, -i2, "holy_leaf");
                }
                finalHeight = i-1;
            }
            builder.buildCustomBlock(0, finalHeight, 0, "holy_leaf");
        });
    }
}
