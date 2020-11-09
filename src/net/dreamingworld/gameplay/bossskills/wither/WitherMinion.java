package net.dreamingworld.gameplay.bossskills.wither;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.customentities.CustomEntity;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;

public class WitherMinion extends CustomEntity {

    public WitherMinion() {
        name = "Wither Minion";
        health = 25;
        entityType = EntityType.SKELETON;

        expDrop = 100;

        hasArmor = true;
        itemInHand = new ItemStack(Material.STONE_AXE);
        itemOnLegs = new ItemStack(Material.IRON_LEGGINGS);
        itemOnChest = new ItemStack(Material.GOLD_CHESTPLATE);
        itemOnBoots = new ItemStack(Material.GOLD_BOOTS);
        itemOnHead = new ItemStack(Material.SKULL_ITEM, 1, (byte) 1);

        drops = new HashMap<>();
        effects = new ArrayList<>();

        drops.put(new ItemStack(Material.DIAMOND), 50);
        drops.put(new ItemStack(Material.SKULL_ITEM, 1, (byte) 1), 10);
        drops.put(DreamingWorld.getInstance().getItemManager().get("ignium"), 25);
        drops.put(DreamingWorld.getInstance().getItemManager().get("wither_heart"), 25);

        effects.add(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3));
        effects.add(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
    }

}
