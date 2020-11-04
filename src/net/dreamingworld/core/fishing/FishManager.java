package net.dreamingworld.core.fishing;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FishManager {

    public List<ItemStack> seaLoot = new ArrayList<>();

    public FishManager(){

    }

    public ItemStack randomizeFish() {
        return seaLoot.get(ThreadLocalRandom.current().nextInt(0, seaLoot.size()));
    }

}
