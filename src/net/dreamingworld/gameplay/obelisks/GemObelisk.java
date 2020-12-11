package net.dreamingworld.gameplay.obelisks;

import net.dreamingworld.core.structures.EasyBuilder;
import net.dreamingworld.core.structures.Structure;
import org.bukkit.Material;
import org.bukkit.WorldType;
import org.bukkit.block.Biome;

import java.util.concurrent.ThreadLocalRandom;

public class GemObelisk extends Structure {

    public GemObelisk() {
        chance = 1;

        worlds.add(WorldType.NORMAL);

        biomes.add(Biome.PLAINS);
        biomes.add(Biome.ROOFED_FOREST);
        biomes.add(Biome.DESERT);

        blocks.add(Material.GRASS);
        blocks.add(Material.SAND);
    }

    @Override
    public void generate(EasyBuilder builder) {
        Biome biome = builder.getStartLocation().getBlock().getBiome();

        String id;
        Material material;
        int durability = 0;

        if (biome == Biome.PLAINS) {
            id = "obsidian_obelisk_top";
            material = Material.OBSIDIAN;
        } else if (biome == Biome.ROOFED_FOREST) {
            id = "cobblestone_obelisk_top";
            material = Material.COBBLESTONE;
        } else {
            id = "sandstone_obelisk_top";
            material = Material.SANDSTONE;
            durability = 2;
        }

        int bodyHeight = ThreadLocalRandom.current().nextInt(1, 2);

        for (int i = 1; i <= bodyHeight + 1; i++) {
            builder.buildMinecraftBlock(0, i, 0, material, durability);
        }

        builder.buildCustomBlock(0, bodyHeight + 2, 0, id);
    }
}
