package net.dreamingworld.core.structures;

import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.generator.BlockPopulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Structure extends BlockPopulator implements Listener {

    protected int chance = 2;
    protected List<WorldType> worlds;
    protected List<Biome> biomes;
    protected List<Material> blocks;

    public Structure() {
        worlds = new ArrayList<>();
        biomes = new ArrayList<>();
        blocks = new ArrayList<>();
    }

    public abstract void generate(EasyBuilder builder);

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        int rnd = random.nextInt(100);

        if (rnd > chance) {
            return;
        }

        int x = random.nextInt(15);
        int z = random.nextInt(15);

        Location location = chunk.getBlock(x, 0, z).getLocation();

        int y = 0;
        for (int i = 255; i > 0; i--) { // World#getHighestBlockAt not working because chunk is not populated
            Location loc = new Location(world, location.getX(), i, location.getZ());

            if (loc.getBlock().getType() != Material.AIR) {
                y = i;
                break;
            }
        }

        location.setY(y);

        Block block = location.getBlock();

        if (!blocks.contains(block.getType()) || !biomes.contains(block.getBiome())) {
            return;
        }

        EasyBuilder builder = new EasyBuilder(location);
        generate(builder);
    }
}
