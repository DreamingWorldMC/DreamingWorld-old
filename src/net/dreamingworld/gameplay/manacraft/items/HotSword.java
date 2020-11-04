package net.dreamingworld.gameplay.manacraft.items;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.crafting.CustomRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HotSword {

    public HotSword() {

        ItemStack item = new ItemStack(Material.GOLD_SWORD);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GOLD + "Hot sword");

        meta.addEnchant(Enchantment.DAMAGE_ALL, 6, true);
        meta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
        meta.addEnchant(Enchantment.DURABILITY, 3, true);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("hot_sword", item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { " I ", " I ", " B " });
        recipe.setCustomIngredient('I', "hot_peddle");
        recipe.setVanillaIngredient('B', Material.STICK);

        DreamingWorld.getInstance().getCraftingManager().registerCraft(recipe);
    }
}