package net.dreamingworld.core.crafting;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CraftingManager implements Listener {

    private List<CustomRecipe> recipes;

    public CraftingManager() {
        recipes = new ArrayList<>();
    }

    public void registerCraft(CustomRecipe recipe) {
        recipe.register();
        recipes.add(recipe);
    }


    @EventHandler
    public void onCraftPrepare(PrepareItemCraftEvent e) {
        CraftingInventory inventory = e.getInventory();

        for (CustomRecipe recipe : recipes) {
            if (e.getRecipe().getResult().isSimilar(recipe.getResult())) {
                if (!recipe.isValid(inventory.getMatrix()))
                    inventory.setResult(new ItemStack(Material.AIR));

                return;
            }
        }


        for (ItemStack item : inventory.getMatrix())
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore())
                inventory.setResult(new ItemStack(Material.AIR));
    }
}
