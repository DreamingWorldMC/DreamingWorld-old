package net.dreamingworld.gameplay.manacraft.researches.managem;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.crafting.CustomRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BlankGem {
    public BlankGem() {
        ItemStack item = new ItemStack(Material.QUARTZ);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GREEN + "Blank gem");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("blank_gem", item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "III", "IMI", "III" });
        recipe.setVanillaIngredient('I', Material.GLASS);
        recipe.setCustomIngredient('M', "manium");


        recipe.setResearch("mana_gem");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }
}
