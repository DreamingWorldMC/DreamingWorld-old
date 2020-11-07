package net.dreamingworld.core.customEntities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class EntityGeneration {

    public Location genEntity(Player p, String type) {
        int x = (int) (ThreadLocalRandom.current().nextInt(-100, 100) + p.getLocation().getX());
        int z = (int) (ThreadLocalRandom.current().nextInt(-100, 100) + p.getLocation().getZ());
        int y;

        switch (type) {
            case ("WATER_TOP"):
                if (p.getWorld().getHighestBlockAt(x, z).getType() == Material.WATER) {
                    return p.getWorld().getHighestBlockAt(x, z).getLocation().add(0,1,0);
                }
                break;
        }


        return null;
    }

}
