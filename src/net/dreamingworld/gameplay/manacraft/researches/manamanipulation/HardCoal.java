package net.dreamingworld.gameplay.manacraft.researches.manamanipulation;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.crafting.CustomRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HardCoal {
    public HardCoal() {
        ItemStack item = new ItemStack(Material.COAL);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GRAY + "Hard coal");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("hard_coal", item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "GGG", "GCG", "GGG" });
        recipe.setVanillaIngredient('G', Material.COAL);
        recipe.setCustomIngredient('C', "manium");

        recipe.setResearch("mana_manipulation");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }
}
