package net.dreamingworld.gameplay.manacraft.researches.manaarmour;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.crafting.CustomRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class ManaArmour {

    public ManaArmour() {
        ItemStack item = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Mana helmet");

        List<String> lore = new ArrayList<>();
        lore.add(Util.formatString("&b[&f0&b/&f2000lmml&b]"));

        meta.setLore(lore);

        item.setItemMeta(meta);

        TagWizard.addItemTag(item, "mana", "0");
        TagWizard.addItemTag(item, "max_mana", "2000");
        TagWizard.addItemTag(item, "mana_line", "0");

        DreamingWorld.getInstance().getItemManager().registerItem("mana_helmet", item);
        DreamingWorld.getInstance().getCustomArmor().addPiece("mana_helmet", 5);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "IMI", "I I", "   " });
        recipe.setCustomIngredient('I', "green_iron_ingot");
        recipe.setCustomIngredient('M', "mana_battey");


        
        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);

        item = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta lmeta = (LeatherArmorMeta)item.getItemMeta();


        lmeta.setDisplayName(ChatColor.AQUA + "Mana chestplate");
        lmeta.setColor(Color.AQUA);

        lmeta.setLore(lore);

        item.setItemMeta(lmeta);

        TagWizard.addItemTag(item, "mana", "0");
        TagWizard.addItemTag(item, "max_mana", "2000");
        TagWizard.addItemTag(item, "mana_line", "0");

        DreamingWorld.getInstance().getItemManager().registerItem("mana_chestplate", item);
        DreamingWorld.getInstance().getCustomArmor().addPiece("mana_chestplate", 6);

        recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "M M", "IMI", "IMI" });
        recipe.setCustomIngredient('I', "mana_ingot");
        recipe.setCustomIngredient('M', "mana_battey");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);


        item = new ItemStack(Material.LEATHER_LEGGINGS);
        lmeta = (LeatherArmorMeta)item.getItemMeta();

        lmeta.setDisplayName(ChatColor.AQUA + "Mana leggings");
        lmeta.setColor(Color.AQUA);

        lmeta.setLore(lore);

        item.setItemMeta(lmeta);

        TagWizard.addItemTag(item, "mana", "0");
        TagWizard.addItemTag(item, "max_mana", "2000");
        TagWizard.addItemTag(item, "mana_line", "0");

        DreamingWorld.getInstance().getItemManager().registerItem("mana_leggings", item);
        DreamingWorld.getInstance().getCustomArmor().addPiece("mana_leggings", 5);

        recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "IMI", "I I", "I I" });
        recipe.setCustomIngredient('I', "mana_ingot");
        recipe.setCustomIngredient('M', "mana_battey");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);


        item = new ItemStack(Material.LEATHER_BOOTS);
        lmeta = (LeatherArmorMeta)item.getItemMeta();

        lmeta.setDisplayName(ChatColor.AQUA + "Mana boots");
        lmeta.setColor(Color.AQUA);

        lmeta.setLore(lore);

        item.setItemMeta(lmeta);

        TagWizard.addItemTag(item, "mana", "0");
        TagWizard.addItemTag(item, "max_mana", "2000");
        TagWizard.addItemTag(item, "mana_line", "0");

        DreamingWorld.getInstance().getItemManager().registerItem("mana_boots", item);
        DreamingWorld.getInstance().getCustomArmor().addPiece("mana_boots", 4);

        recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "   ", "I I", "M M" });
        recipe.setCustomIngredient('I', "green_iron_ingot");
        recipe.setCustomIngredient('M', "mana_battery");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }
}
