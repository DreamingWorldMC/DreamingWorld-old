package net.dreamingworld.gameplay.fishing;

import net.dreamingworld.core.fishing.CustomFishingLoot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EntityCatchTest extends CustomFishingLoot {

    public EntityCatchTest() {
        chance = 100;
    }

    @Override
    public void catched(Player player, Location location, Item item) {
        item.setItemStack(new ItemStack(Material.STONE, 0));
        location.getWorld().spawnEntity(location, EntityType.SQUID);
    }
}
