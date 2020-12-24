package net.dreamingworld.core.customentities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class EntityGeneration {

    public static Location genEntity(Player p, String type) {
        int x = ThreadLocalRandom.current().nextInt(40, 100);
        int z = ThreadLocalRandom.current().nextInt(40, 100);

        if (ThreadLocalRandom.current().nextBoolean())
            x = -x;

        if (ThreadLocalRandom.current().nextBoolean())
            z = -z;

        x += p.getLocation().getX();
        z += p.getLocation().getZ();

        int y;

        switch (type) {
            case ("WATER_TOP"):
                if (p.getWorld().getBlockAt(p.getWorld().getHighestBlockAt(x, z).getLocation().add(0, -1, 0)).getType() == Material.STATIONARY_WATER) {
                    return p.getWorld().getHighestBlockAt(x, z).getLocation();
                }
                break;
            case ("FOREST_TOP"):
                if (p.getWorld().getBiome(x, z) == Biome.FOREST || p.getWorld().getBiome(x, z) == Biome.FOREST_HILLS || p.getWorld().getBiome(x, z) == Biome.MESA_PLATEAU_FOREST || p.getWorld().getBiome(x, z) == Biome.ROOFED_FOREST || p.getWorld().getBiome(x, z) == Biome.ROOFED_FOREST_MOUNTAINS || p.getWorld().getBiome(x, z) == Biome.COLD_TAIGA || p.getWorld().getBiome(x, z) == Biome.COLD_TAIGA_HILLS || p.getWorld().getBiome(x, z) == Biome.COLD_TAIGA_MOUNTAINS) {
                    if (p.getWorld().getBlockAt(p.getWorld().getHighestBlockAt(x, z).getLocation().add(0, -1, 0)).getType() == Material.LEAVES || p.getWorld().getBlockAt(p.getWorld().getHighestBlockAt(x, z).getLocation().add(0, -1, 0)).getType() == Material.LEAVES_2) {
                        Location startPos = p.getWorld().getHighestBlockAt(x, z).getLocation().add(0, -1, 0);

                        for (int i = 0; i < 10; i++){
                            if (startPos.add(0,-2,0).getBlock().getType() == Material.AIR && startPos.add(0,1,0).getBlock().getType() == Material.AIR) {
                                return startPos;
                            }
                        }
                    } else if (p.getWorld().getBlockAt(p.getWorld().getHighestBlockAt(x, z).getLocation().add(0, -1, 0)).getType() != Material.WATER) {
                        return p.getWorld().getHighestBlockAt(x, z).getLocation();
                    }
                }
                break;
            case ("NIGHT_TOP"):
                if (p.getWorld().getHighestBlockAt(x, z).getLightLevel() < 6) {
                    return p.getWorld().getHighestBlockAt(x, z).getLocation();
                }
                break;
            case ("DAY_TOP"):
                if (p.getWorld().getHighestBlockAt(x, z).getLightLevel() > 6) {
                    return p.getWorld().getHighestBlockAt(x, z).getLocation();
                }
                break;
        }

        return null;
    }

}
