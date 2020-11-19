package net.dreamingworld.core.research;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.crafting.CustomRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ResearchRecipes {
    public ResearchRecipes() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GRAY + "Empty research paper");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("empty_research_paper", item);
        item.setAmount(2);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "PPP", "PMP", "PPP" });
        recipe.setCustomIngredient('M', "manium");
        recipe.setVanillaIngredient('P', Material.PAPER);

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);

        recipe = new CustomRecipe(DreamingWorld.getInstance().getResearchManager().getResearchItem("basics"));
        recipe.shape(new String[] { "PPP", "MMM", "PPP" });
        recipe.setCustomIngredient('P', "empty_research_paper");
        recipe.setCustomIngredient('M', "manium");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }
}
