package net.dreamingworld.gameplay.manacraft.researches.managem;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.dreamingworld.core.manainfusion.ManaInfusionRecipe;
import net.dreamingworld.gameplay.manacraft.researches.manainfusion.ManaInfusion;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ManaGemItem {
    public ManaGemItem() {
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Mana gem");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("mana_gem", item);

        ManaInfusionRecipe recipe = new ManaInfusionRecipe();

        recipe.result = item;
        recipe.fromCustomItem = true;
        recipe.customItem = "blank_gem";
        recipe.manaTakes = 500;

        DreamingWorld.getInstance().getManaInfusionManager().addRecipe(recipe);
    }
}
