package net.dreamingworld.gameplay.foodcraft.entities;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.customentities.CustomEntity;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;


public class Duck extends CustomEntity {
    public Duck() {

        name = "Duck";
        Health = 10;
        entityType = EntityType.CHICKEN;

        spawnType = "WATER_TOP";

        expDrop = 5;

        hasArmor = false;

        drops = new HashMap<>();

        drops.put(new ItemStack(Material.FEATHER), 60);
        drops.put(DreamingWorld.getInstance().getItemManager().get("ignium"), 10);
        drops.put(DreamingWorld.getInstance().getItemManager().get("raw_duck"), 80);
    }


}
