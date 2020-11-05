package net.dreamingworld.gameplay.manacraft.Armor;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.crafting.CustomRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class GreenIronArmor {
    public GreenIronArmor() {
        ItemStack item = new ItemStack(Material.SKULL_ITEM);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GREEN + "Green iron helmet");
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("green_iron_helmet", item);
        DreamingWorld.getInstance().getCustomArmor().addPiece("green_iron_helmet", 5);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "MIM", "IMI", "D D" });
        recipe.setCustomIngredient('I', "green_iron_ingot");
        recipe.setVanillaIngredient('D', Material.DIAMOND);
        recipe.setCustomIngredient('M', "advanced_rod");

        DreamingWorld.getInstance().getCraftingManager().registerCraft(recipe);

        item = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta lmeta = (LeatherArmorMeta)item.getItemMeta();


        lmeta.setDisplayName(ChatColor.GREEN + "Green iron chestplate");
        lmeta.setColor(Color.GREEN);

        item.setItemMeta(lmeta);
        DreamingWorld.getInstance().getItemManager().registerItem("green_iron_chestplate", item);
        DreamingWorld.getInstance().getCustomArmor().addPiece("green_iron_chestplate", 6);

        recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "M M", "IMI", "IDI" });
        recipe.setCustomIngredient('I', "green_iron_ingot");
        recipe.setVanillaIngredient('D', Material.DIAMOND);
        recipe.setCustomIngredient('M', "mana_core");

        DreamingWorld.getInstance().getCraftingManager().registerCraft(recipe);


        item = new ItemStack(Material.LEATHER_LEGGINGS);
        lmeta = (LeatherArmorMeta)item.getItemMeta();

        lmeta.setDisplayName(ChatColor.GREEN + "Green iron leggings");
        lmeta.setColor(Color.LIME);

        item.setItemMeta(lmeta);
        DreamingWorld.getInstance().getItemManager().registerItem("green_iron_leggings", item);
        DreamingWorld.getInstance().getCustomArmor().addPiece("green_iron_leggings", 5);

        recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "IDI", "I I", "D D" });
        recipe.setCustomIngredient('I', "green_iron_ingot");
        recipe.setVanillaIngredient('D', Material.DIAMOND);

        DreamingWorld.getInstance().getCraftingManager().registerCraft(recipe);


        item = new ItemStack(Material.LEATHER_BOOTS);
        lmeta = (LeatherArmorMeta)item.getItemMeta();

        lmeta.setDisplayName(ChatColor.GREEN + "Green iron boots");
        lmeta.setColor(Color.GREEN);

        item.setItemMeta(lmeta);
        DreamingWorld.getInstance().getItemManager().registerItem("green_iron_boots", item);
        DreamingWorld.getInstance().getCustomArmor().addPiece("green_iron_boots", 4);

        recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "M M", "I I", "D D" });
        recipe.setCustomIngredient('I', "green_iron_ingot");
        recipe.setVanillaIngredient('D', Material.DIAMOND);
        recipe.setCustomIngredient('M', "mana_core");

        DreamingWorld.getInstance().getCraftingManager().registerCraft(recipe);
    }




}
