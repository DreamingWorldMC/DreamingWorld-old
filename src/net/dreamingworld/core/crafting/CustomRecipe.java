package net.dreamingworld.core.crafting;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.TagWizard;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.*;

public class CustomRecipe {

    private String[] shape;
    private ItemStack result;

    private ShapedRecipe shapedRecipe;

    private Map<Character, Material> vanillaItems;
    private Map<Character, String> customItems;

    protected String requiredResearch = "non";

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
        List<String> items = new ArrayList<>();
        //List<String> items2 = new ArrayList<>();

        for (String i1 : shape) {
            for (char i2 : i1.toCharArray()) {
                ItemStack item = customItems.containsKey(i2) ? DreamingWorld.getInstance().getItemManager().get(customItems.get(i2)) : vanillaItems.containsKey(i2) ? new ItemStack(vanillaItems.get(i2)) : new ItemStack(Material.AIR, 0);
                if (TagWizard.getItemTag(item, "id") != null) {
                    items.add(TagWizard.getItemTag(item, "id"));
                } else {
                    items.add(item.getType().toString());
                }
            }
        }

        int j = 0;
        for (ItemStack i : matrix) {
            if (i == null) {
                break;
            }

            if (!items.get(j).equals(TagWizard.getItemTag(i, "id") != null ? TagWizard.getItemTag(i, "id") : i.getType().toString())) {
                return false;
            }

            j++;
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

    public void setResearch(String r) {
        requiredResearch = r;
    }
}
