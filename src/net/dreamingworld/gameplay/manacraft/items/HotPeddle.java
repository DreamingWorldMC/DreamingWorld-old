package net.dreamingworld.gameplay.manacraft.items;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.alloys.OreAlloy;
import net.dreamingworld.core.crafting.CustomRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HotPeddle {

    public HotPeddle() {
        ItemStack item = new ItemStack(Material.FLINT);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GOLD + "Hot Peddle");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("hot_peddle", item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "III", "IBI", "III" });
        recipe.setCustomIngredient('I', "mystic_peddle");
        recipe.setCustomIngredient('B', "ignium");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }
}
