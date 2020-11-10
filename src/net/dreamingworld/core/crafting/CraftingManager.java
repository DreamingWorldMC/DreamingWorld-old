package net.dreamingworld.core.crafting;

import net.dreamingworld.core.TagWizard;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;

import java.util.HashSet;
import java.util.Set;

public class CraftingManager implements Listener {

    private Set<CustomRecipe> recipes;

    public CraftingManager() {
        recipes = new HashSet<>();
    }


    public void registerRecipe(CustomRecipe recipe) {
        recipe.register();
        recipes.add(recipe);
    }

    public Set<CustomRecipe> getRecipes() {
        return recipes;
    }


    @EventHandler
    public void onCraftPrepare(PrepareItemCraftEvent e) {
        CraftingInventory inventory = e.getInventory();
        for (CustomRecipe recipe : recipes) {
            if (e.getRecipe().getResult().isSimilar(recipe.getResult())) {
                if (!recipe.isValid(inventory.getMatrix())) {
                    inventory.setResult(new ItemStack(Material.AIR));
                    break;
                }
                else {
                    inventory.setResult(recipe.getResult());
                    return;
                }
            }
        }

        for (ItemStack item : inventory.getMatrix()) {
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                inventory.setResult(new ItemStack(Material.AIR));
            }
        }
    }
}
