package net.dreamingworld.gameplay.manacraft.researches.managem;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.crafting.CustomRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EarthGem {
    public EarthGem() {
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_GREEN + "Earth gem");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("earth_gem", item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "FFF", "FGF", "FFF" });
        recipe.setCustomIngredient('G', "blank_gem");
        recipe.setVanillaIngredient('F', Material.STONE);

        recipe.setResearch("mana_gem");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);

        recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "FFF", "FGF", "FFF" });
        recipe.setCustomIngredient('G', "blank_gem");
        recipe.setVanillaIngredient('F', Material.COBBLESTONE);

        recipe.setResearch("mana_gem");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);

        recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "FFF", "FGF", "FFF" });
        recipe.setCustomIngredient('G', "blank_gem");
        recipe.setVanillaIngredient('F', Material.SAND);

        recipe.setResearch("mana_gem");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);

        recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "FFF", "FGF", "FFF" });
        recipe.setCustomIngredient('G', "blank_gem");
        recipe.setVanillaIngredient('F', Material.SANDSTONE);

        recipe.setResearch("mana_gem");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);

        recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "FFF", "FGF", "FFF" });
        recipe.setCustomIngredient('G', "blank_gem");
        recipe.setVanillaIngredient('F', Material.GRASS);

        recipe.setResearch("mana_gem");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);

        recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "FFF", "FGF", "FFF" });
        recipe.setCustomIngredient('G', "blank_gem");
        recipe.setVanillaIngredient('F', Material.GRAVEL);

        recipe.setResearch("mana_gem");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);

        recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "FFF", "FGF", "FFF" });
        recipe.setCustomIngredient('G', "blank_gem");
        recipe.setVanillaIngredient('F', Material.DIRT);

        recipe.setResearch("mana_gem");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }
}
