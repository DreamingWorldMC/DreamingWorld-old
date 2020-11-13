package net.dreamingworld.gameplay.trees;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.structures.EasyBuilder;
import net.dreamingworld.core.structures.Structure;
import net.minecraft.server.v1_8_R3.MathHelper;
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
            int height = ThreadLocalRandom.current().nextInt(4, 12 + 1);
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

                    int i2_ = MathHelper.clamp(i2, 0, height/2);

                    builder.buildCustomBlock(i2_, i2_ + (height / 4) + i, 0, "white_log");
                    builder.buildCustomBlock(i2_, i2_ + 1 + (height / 4) + i, 0, "holy_leaf");

                    builder.buildCustomBlock(-i2_, i2_ + (height / 4) + i, 0, "white_log");
                    builder.buildCustomBlock(-i2_, i2_ + 1 + (height / 4) + i, 0, "holy_leaf");

                    builder.buildCustomBlock(0, i2_ + (height / 4) + i, i2_, "white_log");
                    builder.buildCustomBlock(0, i2_ + 1 + (height / 4) + i, i2_, "holy_leaf");

                    builder.buildCustomBlock(0, i2_ + (height / 4) + i, -i2_, "white_log");
                    builder.buildCustomBlock(0, i2_ + 1 + (height / 4) + i, -i2_, "holy_leaf");
                }
                finalHeight = i-1;
            }
            builder.buildCustomBlock(0, finalHeight, 0, "holy_leaf");
        });
    }
}
