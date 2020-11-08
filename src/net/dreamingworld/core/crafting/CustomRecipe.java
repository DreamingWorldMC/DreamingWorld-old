package net.dreamingworld.core.crafting;

import net.dreamingworld.DreamingWorld;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashMap;
import java.util.Map;

public class CustomRecipe {

    private String[] shape;
    private ItemStack result;

    private ShapedRecipe shapedRecipe;

    private Map<Character, Material> vanillaItems;
    private Map<Character, String> customItems;

    public CustomRecipe(ItemStack item) {
        vanillaItems = new HashMap<>();
        customItems = new HashMap<>();

        shapedRecipe = new ShapedRecipe(item);

        result = item;
    }


    public void shape(String[] shape) {
        shapedRecipe.shape(shape);
        this.shape = shape;
    }


    public void setVanillaIngredient(char symbol, Material ingredient) {
        vanillaItems.put(symbol, ingredient);
        shapedRecipe.setIngredient(symbol, ingredient);
    }

    public void setCustomIngredient(char symbol, String id) {
        ItemStack item = DreamingWorld.getInstance().getItemManager().get(id);

        if (item == null) {
            DreamingWorld.getInstance().getLogger().warning("Item with id '" + id + "' not found");
            return;
        }

        shapedRecipe.setIngredient(symbol, item.getData());
        customItems.put(symbol, id);
        vanillaItems.put(symbol, item.getType());
    }


    public ItemStack getResult() {
        return result;
    }

    protected boolean isValid(ItemStack[] matrix) {
        int c = 0;
        for (String row : shape) {
            int c1 = 0;
            for (char symbol : row.toCharArray()) {
                ItemStack item = customItems.containsKey(symbol) ? DreamingWorld.getInstance().getItemManager().get(customItems.get(symbol)) : vanillaItems.containsKey(symbol) ? new ItemStack(vanillaItems.get(symbol)) : new ItemStack(Material.AIR, 0);
                ItemStack item2 = matrix[c + c1];

                if (!item.isSimilar(item2) && !(item.getType() == Material.AIR && item2.getType() == Material.AIR)) {
                    return false;
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

        for (Map.Entry<Character, Material> e : vanillaItems.entrySet()) {
            ItemStack ingredient = customItems.containsKey(e.getKey()) ? DreamingWorld.getInstance().getItemManager().get(customItems.get(e.getKey())) : new ItemStack(e.getValue());
            ingredients.put(e.getKey(), ingredient);
        }

        return ingredients;
    }


    protected void register() {
        Bukkit.addRecipe(shapedRecipe);
    }
}
