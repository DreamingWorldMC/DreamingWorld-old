package net.dreamingworld.core.customentities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
                if (p.getWorld().getBlockAt(p.getWorld().getHighestBlockAt(x, z).getLocation().add(0, -1, 0)).getType() == Material.STATIONARY_WATER)
                    return p.getWorld().getHighestBlockAt(x, z).getLocation();
                break;
        }

        return null;
    }

}
