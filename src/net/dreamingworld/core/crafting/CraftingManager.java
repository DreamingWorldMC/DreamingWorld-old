package net.dreamingworld.core.crafting;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.TagWizard;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

public class CraftingManager implements Listener {

    private Set<CustomRecipe> recipes;

    private Map<String, String> itemsRequiringResearch;

    public CraftingManager() {
        recipes = new HashSet<>();
        itemsRequiringResearch = new HashMap<>();
    }


    public void registerRecipe(CustomRecipe recipe) {
        recipe.register();
        recipes.add(recipe);
        if (recipe.requiredResearch != null) {
            itemsRequiringResearch.put(TagWizard.getItemTag(recipe.getResult(), "id"), recipe.requiredResearch);
        }
    }

    public Set<CustomRecipe> getRecipes() {
        return recipes;
    }


    @EventHandler
    public void onCraftPrepare(PrepareItemCraftEvent e) {
        CraftingInventory inventory = e.getInventory();
        for (CustomRecipe recipe : recipes) {
            if (!recipe.isValid(inventory.getMatrix())) {
                inventory.setResult(new ItemStack(Material.AIR));
            }
            else {
                inventory.setResult(recipe.getResult());
                return;
            }
        }

        for (ItemStack item : inventory.getMatrix()) {
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                inventory.setResult(new ItemStack(Material.AIR));
            }
        }
    }

    @EventHandler
    public void onTake(CraftItemEvent e) {
        if (itemsRequiringResearch.containsKey(TagWizard.getItemTag(e.getCurrentItem(), "id"))) {
            if (DreamingWorld.getInstance().getResearchManager().playerHasResearch((Player) e.getWhoClicked(), itemsRequiringResearch.get(TagWizard.getItemTag(e.getCurrentItem(), "id")))) {
                return;
            }
            else {
                e.getWhoClicked().sendMessage(ChatColor.DARK_RED + "You don't have needed research in your inventory.");
                e.setCancelled(true);
            }
        }
    }
}
