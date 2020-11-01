package net.dreamingworld.alloys;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class OreAlloy {

    protected ItemStack item;
    protected int chance;
    protected boolean dropOnBreak;
    protected boolean dropOnSmelt;

    public ItemStack randomize() {
        int a = ThreadLocalRandom.current().nextInt(0, 101);

        if (a <= chance)
            return item;

        return new ItemStack(Material.AIR);
    }
}
