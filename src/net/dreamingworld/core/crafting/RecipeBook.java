package net.dreamingworld.core.crafting;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.UtilItems;
import net.dreamingworld.core.ui.ChestUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RecipeBook implements Listener {

    private final List<Map<ItemStack, ItemStack[]>> pages;
    private final List<ChestUI> chests;

    public RecipeBook() {
        pages = new ArrayList<>();
        chests = new ArrayList<>();

        handlePages();
        generateChests();

        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
    }


    public void showToPlayer(Player player, int page) {
        chests.get(page).show(player);
    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!e.getInventory().getName().startsWith("DreamingWorld Recipes")) {
            return;
        }

        int page = Integer.parseInt(e.getInventory().getName().replace("DreamingWorld Recipes | Page ", "")) - 1;
        int slot = e.getSlot();

        if (slot < 0 || slot >= e.getInventory().getSize()) {
            return;
        }

        if (slot == 0 && e.getInventory().getItem(slot).isSimilar(UtilItems.arrowBack())) {
            showToPlayer((Player) e.getWhoClicked(), page - 1);
        } else if (slot == 8 && e.getInventory().getItem(slot).isSimilar(UtilItems.arrowForward())) {
            showToPlayer((Player) e.getWhoClicked(), page + 1);
        } else if (!e.getInventory().getItem(slot).isSimilar(UtilItems.nothing())) {
            ItemStack item = e.getInventory().getItem(slot);
            ItemStack[] items = null;

            for (Map.Entry<ItemStack, ItemStack[]> entry : pages.get(page).entrySet()) {
                String id0 = TagWizard.getItemTag(entry.getKey(), "id");
                String id1 = TagWizard.getItemTag(item, "id");

                if (id0.equals(id1)) {
                    items = entry.getValue();
                }
            }

            if (items == null) {
                return;
            }

            ChestUI ui = new ChestUI("Recipe View", 5);

            ui.fill(UtilItems.nothing());

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    ui.putItem(j + 1, i + 1, items[i * 3 + j]);
                }
            }

            ui.putItem(6, 2, item);

            ui.show((Player) e.getWhoClicked());
        }
    }


    private void generateChests() {
        int pg = 0;
        for (Map<ItemStack, ItemStack[]> page : pages) {
            ChestUI ui = new ChestUI("DreamingWorld Recipes | Page " + (pg + 1), 1);

            int i = 1;
            for (ItemStack preview : page.keySet()) {
                preview.setAmount(1);
                ui.putItem(i, preview);
                i++;
            }

            ItemStack back = pg == 0 ? UtilItems.nothing() : UtilItems.arrowBack();
            ItemStack forward = pg == pages.size() - 1 ? UtilItems.nothing() : UtilItems.arrowForward();

            ui.putItem(0, back);
            ui.putItem(8, forward);

            chests.add(ui);

            pg++;
        }
    }

    private void handlePages() {
        Set<CustomRecipe> recipes = DreamingWorld.getInstance().getCraftingManager().getRecipes();

        Map<ItemStack, ItemStack[]> pgs = new HashMap<>();

        for (CustomRecipe recipe : recipes) {
            ItemStack result = recipe.getResult();
            ItemStack[] matrix = new ItemStack[9];

            for (Map.Entry<Character, ItemStack> e : recipe.getIngredients().entrySet()) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (recipe.getShape()[i].toCharArray()[j] == e.getKey()) {
                            matrix[i * 3 + j] = e.getValue();
                            matrix[i * 3 + j].setAmount(1);
                        }
                    }
                }
            }

            pgs.put(result, matrix);
        }

        List<ItemStack[]> previews = (List<ItemStack[]>) (Object) Util.splitArray(pgs.keySet().toArray(), 7);
        List<ItemStack[][]> shapes = (List<ItemStack[][]>) (Object) Util.splitArray(pgs.values().toArray(), 7);

        for (int i = 0; i < previews.size(); i++) {
            List<ItemStack> keys = Arrays.asList(previews.get(i));
            List<ItemStack[]> values = Arrays.asList(shapes.get(i));

            pages.add(IntStream.range(0, keys.size()).boxed().collect(Collectors.toMap(keys::get, values::get)));
        }
    }
}
