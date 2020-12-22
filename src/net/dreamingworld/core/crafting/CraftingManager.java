package net.dreamingworld.core.crafting;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.TagWizard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.*;

import java.util.*;

public class CraftingManager implements Listener {

    private Set<CustomRecipe> recipes;
    private List<ItemStack> vanillaResults;

    private Map<String, String> itemsRequiringResearch;

    public CraftingManager() {
        recipes = new HashSet<>();
        itemsRequiringResearch = new HashMap<>();

        vanillaResults = new ArrayList<>();

        Iterator<Recipe> it = Bukkit.recipeIterator();
        while (it.hasNext()) {
            Recipe r = it.next();
            vanillaResults.add(r.getResult());
        }
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

        rcp: for (CustomRecipe recipe : recipes) {
            for (ItemStack item : vanillaResults) {
                if (item.isSimilar(e.getRecipe().getResult())) {
                    continue rcp;
                }
            }

            if (!recipe.isValid(inventory.getMatrix())) {
                inventory.setResult(new ItemStack(Material.AIR));
            } else {
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
            if (!DreamingWorld.getInstance().getResearchManager().playerHasResearch((Player) e.getWhoClicked(), itemsRequiringResearch.get(TagWizard.getItemTag(e.getCurrentItem(), "id")))) {
                e.getWhoClicked().sendMessage(ChatColor.DARK_RED + "You don't have needed research in your inventory.");
                e.setCancelled(true);
            }
        }
    }
}
