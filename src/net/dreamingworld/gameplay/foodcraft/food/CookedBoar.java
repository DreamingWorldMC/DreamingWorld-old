package net.dreamingworld.gameplay.foodcraft.food;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.customfood.FoodItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CookedBoar extends FoodItem {

    public CookedBoar() {
        name = "cooked_boar";

        foodPoints = 2; // FOOD

        ItemStack item = new ItemStack(Material.COOKED_BEEF); // Item
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.WHITE + "Cooked Boar");

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem(name, item);
        DreamingWorld.getInstance().getSmeltingManager().addSmelt("raw_boar", "cooked_boar");
    }
}
