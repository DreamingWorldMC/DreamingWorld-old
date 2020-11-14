package net.dreamingworld.gameplay.manacraft.mobs;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.customentities.CustomEntity;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;

public class AstralCreature extends CustomEntity {

    public AstralCreature() {
        name = "Astral creature";
        health = 50;
        entityType = EntityType.SKELETON;
        damage = 20;

        spawnType = "NIGHT_TOP";

        expDrop = 5;

        hasArmor = true;
        itemInHand = new ItemStack(Material.DIAMOND_SWORD);
        itemOnHead = new ItemStack(Material.SUGAR);
        itemOnChest = new ItemStack(Material.IRON_CHESTPLATE);

        drops = new HashMap<>();
        effects = new ArrayList<>();

        drops.put(DreamingWorld.getInstance().getItemManager().get("star_dust"), 80);

        effects.add(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
        effects.add(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));

    }
}
