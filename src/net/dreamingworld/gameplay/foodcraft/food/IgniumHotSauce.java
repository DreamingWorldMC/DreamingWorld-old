package net.dreamingworld.gameplay.foodcraft.food;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.dreamingworld.core.customfood.FoodItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

public class IgniumHotSauce extends FoodItem {

    public IgniumHotSauce() {
        name = "ignium_hot_sauce";

        foodPoints = 2; // FOOD
        effects = new ArrayList<>();
        effects.add(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 10, 1));

        Potion itemPot = new Potion(PotionType.INSTANT_DAMAGE, 1);

        ItemStack item = itemPot.toItemStack(1); // ITEM
        PotionMeta meta = (PotionMeta)item.getItemMeta();

        meta.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, 1, 1), true);
        meta.setMainEffect(PotionEffectType.CONFUSION);
        meta.removeCustomEffect(PotionEffectType.HARM);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.setDisplayName(ChatColor.YELLOW + "Ignium hot sauce");

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem(name, item);


        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "PIC", " B ", "   " });
        recipe.setVanillaIngredient('P', Material.POTATO_ITEM);
        recipe.setVanillaIngredient('C', Material.CARROT_ITEM);
        recipe.setVanillaIngredient('B', Material.GLASS_BOTTLE);
        recipe.setCustomIngredient('I', "ignium");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }
}
