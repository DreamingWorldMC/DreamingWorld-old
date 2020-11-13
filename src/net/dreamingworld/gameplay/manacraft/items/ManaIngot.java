package net.dreamingworld.gameplay.manacraft.items;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.dreamingworld.core.manainfusion.ManaInfusionRecipe;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ManaIngot {

    public ManaIngot() {
        ItemStack item = new ItemStack(Material.IRON_INGOT);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Mana ingot");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("mana_ingot", item);

        ManaInfusionRecipe recipe = new ManaInfusionRecipe();

        recipe.result = item;
        recipe.fromCustomItem = false;
        recipe.vanillaItem = Material.IRON_INGOT;
        recipe.manaTakes = 100;

        DreamingWorld.getInstance().getManaInfusionManager().addRecipe(recipe);
    }
}
