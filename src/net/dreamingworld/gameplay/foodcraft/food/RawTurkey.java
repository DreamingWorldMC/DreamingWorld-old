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

public class RawTurkey extends FoodItem {

    public RawTurkey() {
        name = "raw_turkey";

        foodPoints = 1; // FOOD
        effects = new ArrayList<>();
        effects.add(new PotionEffect(PotionEffectType.HUNGER, 20, 1));

        ItemStack item = new ItemStack(Material.RAW_CHICKEN); // Item
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.YELLOW + "Raw turkey");

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem(name, item);
    }
}
