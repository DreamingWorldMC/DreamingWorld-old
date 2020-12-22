package net.dreamingworld.core.crafting;

import com.google.common.collect.Lists;
import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.UtilItems;
import net.dreamingworld.core.ui.ChestUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class RecipeBook implements Listener {

    private final Map<ItemStack, ItemStack[]> recipes;
    private Map<UUID, Integer> playerPages;

    public RecipeBook() {
        playerPages = new HashMap<>();

        recipes = new HashMap<>();
        for (CustomRecipe r : DreamingWorld.getInstance().getCraftingManager().getRecipes()) {
            ItemStack[] recipe = new ItemStack[9];

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    recipe[i * 3 + j] = r.getIngredients().get(r.getShape()[i].toCharArray()[j]);
                }
            }

            recipes.put(r.getResult(), recipe);
        }

        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
    }


    public void showRecipeBook(Player player) {
        int p = 0;

        if (playerPages.containsKey(player.getUniqueId())) {
            p = playerPages.get(player.getUniqueId());
        }

        playerPages.put(player.getUniqueId(), p);

        Map<ItemStack, ItemStack[]> availableRecipes = new TreeMap<>(this::compareItemStacks);
        availableRecipes.putAll(recipes);

        for (ItemStack item : recipes.keySet()) {
            if (!DreamingWorld.getInstance().getCraftingManager().canCraft(player, item)) {
                availableRecipes.remove(item);
            }
        }

        List<List<ItemStack>> pages = Lists.partition(new ArrayList<>(availableRecipes.keySet()), 21);

        if (pages.size() <= p) {
            return;
        }

        List<List<ItemStack>> pageContents = Lists.partition(pages.get(p), 7);

        ChestUI book = new ChestUI("Recipe Book | Page " + (p + 1), 5);
        book.fill(UtilItems.nothing());

        ItemStack it = book.getItemInSlot(0, 0);
        TagWizard.addItemTag(it, "menu", "recipebook");
        book.putItem(0, 0, it);

        if (p != 0) {
            book.putItem(0, 4, UtilItems.arrowBack());
        }

        if ((p + 1) < pages.size()) {
            book.putItem(8, 4, UtilItems.arrowForward());
        }

        for (int i = 0; i < 3; i++) {
            List<ItemStack> items = null;

            if (i < pageContents.size()) {
                items = pageContents.get(i);
            }

            for (int j = 0; j < 7; j++) {
                if (items == null || j >= items.size()) {
                    book.putItem(j + 1, i + 1, new ItemStack(Material.AIR));
                    continue;
                }

                ItemStack item = items.get(j);
                item.setAmount(1);

                book.putItem(j + 1, i + 1, item);
            }
        }

        book.show(player);
    }

    public void showRecipeView(Player player, ItemStack item) {
        if (item == null) {
            return;
        }

        ItemStack result = new ItemStack(item);
        ItemStack[] recipe = null;

        for (Map.Entry<ItemStack, ItemStack[]> e : recipes.entrySet()) {
            String t = TagWizard.getItemTag(result, "id");

            if (t != null && t.equals(TagWizard.getItemTag(e.getKey(), "id"))) {
                recipe = e.getValue();
                break;
            }
        }

        if (recipe == null) {
            return;
        }

        ChestUI book = new ChestUI("Recipe View", 5);
        book.fill(UtilItems.nothing());

        ItemStack it = book.getItemInSlot(0, 0);
        TagWizard.addItemTag(it, "menu", "recipeview");
        book.putItem(0, 0, it);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ItemStack itm = recipe[i * 3 + j];

                if (itm != null) {
                    itm.setAmount(1);
                }

                book.putItem(j + 1, i + 1, itm);
            }
        }

        book.putItem(6, 2, result);
        book.show(player);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory i = e.getInventory();
        ItemStack item = i.getItem(0);

        if (item == null) {
            return;
        }

        String menu = TagWizard.getItemTag(item, "menu");

        if (menu == null) {
            return;
        }

        if (!menu.equals("recipebook") && !menu.equals("recipeview")) {
            return;
        }

        if (e.getSlot() < 0 || e.getSlot() >= e.getInventory().getSize()) {
            return;
        }

        if (e.getRawSlot() > e.getInventory().getSize()) {
            return;
        }

        ItemStack ci = e.getInventory().getItem(e.getSlot());

        if (ci.isSimilar(UtilItems.arrowForward())) {
            playerPages.put(e.getWhoClicked().getUniqueId(), playerPages.get(e.getWhoClicked().getUniqueId()) + 1);
            showRecipeBook((Player) e.getWhoClicked());
        } else if (ci.isSimilar(UtilItems.arrowBack())) {
            playerPages.put(e.getWhoClicked().getUniqueId(), playerPages.get(e.getWhoClicked().getUniqueId()) - 1);
            showRecipeBook((Player) e.getWhoClicked());
        } else {
            showRecipeView((Player) e.getWhoClicked(), ci);
        }
    }

    public int compareItemStacks(ItemStack o1, ItemStack o2) {
        String s0 = o1.getItemMeta().getDisplayName().replaceAll("§\\w", "");
        String s1 = o2.getItemMeta().getDisplayName().replaceAll("§\\w", "");

        return s0.compareToIgnoreCase(s1);
    }
}
