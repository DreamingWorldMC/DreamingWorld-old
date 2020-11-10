package net.dreamingworld.gameplay.foodcraft.food;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.customfood.FoodItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class RawBoar extends FoodItem {

    public RawBoar() {
        name = "raw_boar";

        foodPoints = 0; // FOOD
        effects = new ArrayList<>();
        effects.add(new PotionEffect(PotionEffectType.HUNGER, 2, 1));

        ItemStack item = new ItemStack(Material.RAW_BEEF); // Item
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.YELLOW + "Raw boar");

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem(name, item);
    }
}
