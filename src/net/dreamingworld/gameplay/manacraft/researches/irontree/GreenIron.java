package net.dreamingworld.gameplay.manacraft.researches.irontree;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.crafting.CustomRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GreenIron {

    public GreenIron() {
        ItemStack item = new ItemStack(Material.QUARTZ);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GREEN + "Green iron nugget");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("green_iron_nugget", item);


        item = new ItemStack(Material.IRON_INGOT);
        meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GREEN + "Green iron ingot");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("green_iron_ingot", item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "III", "III", "III" });
        recipe.setCustomIngredient('I', "green_iron_nugget");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }
}
