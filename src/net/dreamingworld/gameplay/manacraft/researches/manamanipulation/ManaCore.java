package net.dreamingworld.gameplay.manacraft.researches.manamanipulation;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.crafting.CustomRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ManaCore {

    public ManaCore() {
        ItemStack item = new ItemStack(Material.NETHER_BRICK_ITEM);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Mana core");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("mana_core", item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "IBI", "MBM", "IBI" });
        recipe.setCustomIngredient('M', "energium");
        recipe.setCustomIngredient('B', "manium");
        recipe.setCustomIngredient('I', "ignium");

        recipe.setResearch("mana_manipulation");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }
}
