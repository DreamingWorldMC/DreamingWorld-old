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

        foodPoints = 1; // FOOD
        effects = new ArrayList<>();
        effects.add(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 10, 1));

        Potion itemPot = new Potion(PotionType.INSTANT_DAMAGE, 0);

        ItemStack item = itemPot.toItemStack(1); // ITEM
        PotionMeta meta = (PotionMeta)item.getItemMeta();

        meta.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, 0, 0), true);
        meta.setMainEffect(PotionEffectType.CONFUSION);
        meta.clearCustomEffects();
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.setDisplayName(ChatColor.YELLOW + "Ignium hot sauce");

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem(name, item);


        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "PIC", " B ", "   " });
        recipe.setVanillaIngredient('P', Material.POTATO_ITEM);
        recipe.setVanillaIngredient('C', Material.CARROT);
        recipe.setVanillaIngredient('B', Material.GLASS_BOTTLE);
        recipe.setCustomIngredient('I', "ignium");

        DreamingWorld.getInstance().getCraftingManager().registerCraft(recipe);


    }
}
