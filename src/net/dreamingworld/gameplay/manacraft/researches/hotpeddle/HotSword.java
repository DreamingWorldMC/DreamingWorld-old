package net.dreamingworld.gameplay.manacraft.researches.hotpeddle;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.crafting.CustomRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class HotSword {

    public HotSword() {
        ItemStack item = new ItemStack(Material.GOLD_SWORD);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GOLD + "Hot sword");

        meta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);

        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.BLUE + "[Deals 8 damage]");
        meta.setLore(lore);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("hot_sword", item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { " I ", " I ", " B " });
        recipe.setCustomIngredient('I', "hot_peddle");
        recipe.setVanillaIngredient('B', Material.STICK);

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
        DreamingWorld.getInstance().getCustomWeaponManager().addWeapon("hot_sword", 8);
    }
}
