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

public class TurkeyWithHotSauce extends FoodItem {

    public TurkeyWithHotSauce() {
        name = "turkey_with_hot_sauce";

        foodPoints = 2; // FOOD
        effects = new ArrayList<>();
        effects.add(new PotionEffect(PotionEffectType.SPEED, 100, 1));


        ItemStack item = new ItemStack(Material.COOKED_CHICKEN); // ITEM
        ItemMeta meta = item.getItemMeta();


        meta.setDisplayName(ChatColor.YELLOW + "Turkey with hot sauce");

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem(name, item);


        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { " S ", " D ", "   " });
        recipe.setCustomIngredient('D', "cooked_turkey");
        recipe.setCustomIngredient('S', "ignium_hot_sauce");


        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }
}
