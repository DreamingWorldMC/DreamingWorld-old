package net.dreamingworld.crafting;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.TagWizard;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashMap;
import java.util.Map;

public class CustomRecipe {

    private String[] shape;
    private ShapedRecipe recipe;
    private Map<Character, String> customItems;

    public CustomRecipe(ItemStack item) {
        recipe = new ShapedRecipe(item);
        customItems = new HashMap<>();
    }


    public void shape(String[] shape) {
        recipe.shape(shape);
        this.shape = shape;
    }


    public void setVanillaIngredient(char symbol, Material ingredient) {
        recipe.setIngredient(symbol, ingredient);
    }

    public void setCustomIngredient(char symbol, String id) {
        ItemStack item = DreamingWorld.getInstance().getItemManager().get(id);

        if (item == null) {
            DreamingWorld.getInstance().getLogger().warning("Item with id '" + id + "' not found");
            return;
        }

        recipe.setIngredient(symbol, item.getData());
        customItems.put(symbol, id);
    }


    public ItemStack getResult() {
        return recipe.getResult();
    }

    protected boolean isValid(ItemStack[] matrix) {
        for (Map.Entry<Character, String> e : customItems.entrySet()) {
            int c = 0;
            for (String row : shape) {
                int c2 = 0;
                for (char ch : row.toCharArray()) {
                    if (ch == e.getKey())
                        if (!DreamingWorld.getInstance().getItemManager().checkItemAuthenticity(matrix[c + c2], e.getValue())) // Double if because I want
                            return false;

                    c2++;
                }

                c += 3;
            }
        }

        return true;
    }


    protected void register() {
        Bukkit.addRecipe(recipe);
    }
}
