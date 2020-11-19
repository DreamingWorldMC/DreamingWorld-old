package net.dreamingworld.gameplay.manacraft.armor;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.crafting.CustomRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class AstrumArmor {

    public AstrumArmor() {
        ItemStack item = new ItemStack(Material.QUARTZ_STAIRS);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_PURPLE + "Astrum helmet");
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("astrum_helmet", item);
        DreamingWorld.getInstance().getCustomArmor().addPiece("astrum_helmet", 6);
        DreamingWorld.getInstance().getCustomArmor().addHeadPiece("astrum_helmet");

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "MIM", "I I", "D D" });
        recipe.setCustomIngredient('I', "astrum_ingot");
        recipe.setVanillaIngredient('D', Material.DIAMOND);
        recipe.setCustomIngredient('M', "advanced_rod");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);

        item = new ItemStack(Material.DIAMOND_CHESTPLATE);
        meta = item.getItemMeta();


        meta.setDisplayName(ChatColor.DARK_PURPLE + "Astrum chestplate");

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("astrum_chestplate", item);
        DreamingWorld.getInstance().getCustomArmor().addPiece("astrum_chestplate", 7);

        recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "M M", "IMI", "IDI" });
        recipe.setCustomIngredient('I', "astrum_ingot");
        recipe.setVanillaIngredient('D', Material.DIAMOND);
        recipe.setCustomIngredient('M', "mana_core");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);


        item = new ItemStack(Material.DIAMOND_LEGGINGS);
        meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_PURPLE + "Astrum leggings");

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("astrum_leggings", item);
        DreamingWorld.getInstance().getCustomArmor().addPiece("astrum_leggings", 6);

        recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "IDI", "I I", "D D" });
        recipe.setCustomIngredient('I', "astrum_ingot");
        recipe.setVanillaIngredient('D', Material.DIAMOND);

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);


        item = new ItemStack(Material.DIAMOND_BOOTS);
        meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_PURPLE + "Astrum boots");

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("astrum_boots", item);
        DreamingWorld.getInstance().getCustomArmor().addPiece("astrum_boots", 5);

        recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "M M", "I I", "D D" });
        recipe.setCustomIngredient('I', "astrum_ingot");
        recipe.setVanillaIngredient('D', Material.DIAMOND);
        recipe.setCustomIngredient('M', "mana_core");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }
}
