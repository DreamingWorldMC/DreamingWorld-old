package net.dreamingworld.gameplay.foodcraft.food;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.dreamingworld.core.customfood.FoodItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class DuckWithHotSauce extends FoodItem {

    public DuckWithHotSauce() {
        name = "duck_with_hot_sauce";

        foodPoints = 3; // FOOD
        effects = new ArrayList<>();
        effects.add(new PotionEffect(PotionEffectType.SPEED, 100, 1));


        ItemStack item = new ItemStack(Material.COOKED_CHICKEN); // ITEM
        ItemMeta meta = item.getItemMeta();


        meta.setDisplayName(ChatColor.YELLOW + "Duck with hot sauce");

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem(name, item);


        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { " S ", " D ", "   " });
        recipe.setCustomIngredient('D', "cooked_duck");
        recipe.setCustomIngredient('S', "ignium_hot_sauce");


        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }
}
