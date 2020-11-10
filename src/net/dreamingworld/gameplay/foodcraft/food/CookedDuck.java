package net.dreamingworld.gameplay.foodcraft.food;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.customfood.FoodItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CookedDuck extends FoodItem {

    public CookedDuck() {
        name = "cooked_duck";

        foodPoints = 1; // FOOD

        ItemStack item = new ItemStack(Material.COOKED_CHICKEN); // Item
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.YELLOW + "Cooked duck");

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem(name, item);
        DreamingWorld.getInstance().getSmeltingManager().addSmelt("raw_duck", "cooked_duck");
    }
}
