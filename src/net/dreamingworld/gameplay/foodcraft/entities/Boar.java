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


public class Boar extends CustomEntity {
    public Boar() {

        name = "Boar";
        health = 20;
        entityType = EntityType.PIG;

        spawnType = "FOREST_TOP";

        expDrop = 5;

        hasArmor = false;

        drops = new HashMap<>();
        effects = new ArrayList<>();


        drops.put(DreamingWorld.getInstance().getItemManager().get("raw_boar"), 90);

        effects.add(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 1));
        effects.add(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3));

    }


}
