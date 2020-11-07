package net.dreamingworld.core.crafting;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.TagWizard;
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
        int c = 0;
        for (String row : shape) {
            int c1 = 0;
            for (char ch : row.toCharArray()) {
                if (customItems.containsKey(ch)) {
                    if (!DreamingWorld.getInstance().getItemManager().checkItemAuthenticity(matrix[c + c1], customItems.get(ch))) {
                        return false;
                    }
                }

                else {
                    if (TagWizard.hasData(matrix[c + c1])) {
                        return false;
                    }
                }

                c1++;
            }

            c += 3;
        }

        return true;
    }


    protected String[] getShape() {
        return shape;
    }

    protected Map<Character, ItemStack> getIngredients() {
        Map<Character, ItemStack> ingredients = new HashMap<>();

        for (Map.Entry<Character, ItemStack> e : recipe.getIngredientMap().entrySet()) {
            ItemStack ingr = customItems.containsKey(e.getKey()) ? DreamingWorld.getInstance().getItemManager().get(customItems.get(e.getKey())) : e.getValue();
            ingredients.put(e.getKey(), ingr);
        }

        return ingredients;
    }


    protected void register() {
        Bukkit.addRecipe(recipe);
    }
}
