package net.dreamingworld.gameplay.manacraft.researches.advancedtools;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.dreamingworld.gameplay.manacraft.researches.irontree.GreenIron;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AdvancedStick {

    public AdvancedStick() {
        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GRAY + "Advanced stick");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("advanced_rod", item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { " S ", " M ", " S " });
        recipe.setCustomIngredient('M', "mana_core");
        recipe.setVanillaIngredient('S', Material.STICK);

        recipe.setResearch("advanced_tools");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }
}
