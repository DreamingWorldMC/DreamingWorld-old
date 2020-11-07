package net.dreamingworld.core.crafting;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.UtilItems;
import net.dreamingworld.core.ui.ChestUI;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RecipeBook implements Listener {

    private List<Map<ItemStack, ItemStack[]>> pages;
    private List<ChestUI> chests;

    public RecipeBook() {
        pages = new ArrayList<>();
        chests = new ArrayList<>();

        handlePages();
        generateChests();
    }


    public void showToPlayer(Player player, int page) {
        chests.get(page).show(player);
    }


    private void generateChests() {
        int pg = 0;
        for (Map<ItemStack, ItemStack[]> page : pages) {
            ChestUI ui = new ChestUI("DreamingWorld Recipe Book", 1);

            int i = 1;
            for (ItemStack preview : page.keySet()) {
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
