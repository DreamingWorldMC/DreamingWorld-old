package net.dreamingworld.core.fishing;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public abstract class CustomFishingLoot {

    protected int chance;

    public CustomFishingLoot() {
        chance = 10;
    }

    public int getChance() {
        return chance;
    }


    public abstract void catched(Player player, Location location, Item item);
}
