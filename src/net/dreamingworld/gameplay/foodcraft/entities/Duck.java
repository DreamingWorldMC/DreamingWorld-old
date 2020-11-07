package net.dreamingworld.gameplay.foodcraft.entities;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.customentities.CustomEntity;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;


public class Duck extends CustomEntity {
    public Duck() {

        name = "Duck";
        health = 10;
        entityType = EntityType.CHICKEN;

        spawnType = "WATER_TOP";

        expDrop = 5;

        hasArmor = false;

        drops = new HashMap<>();
        effects = new ArrayList<>();

        drops.put(new ItemStack(Material.FEATHER), 60);
        drops.put(DreamingWorld.getInstance().getItemManager().get("ignium"), 10);
        drops.put(DreamingWorld.getInstance().getItemManager().get("raw_duck"), 80);

        effects.add(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 1));
        effects.add(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));

    }


}
