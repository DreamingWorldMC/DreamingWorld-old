package net.dreamingworld.core.geminfusion;

import net.dreamingworld.core.TagWizard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GemInfusionManager {

    private List<GemInfusionRecipe> recipes;
    private Map<String, Integer> stabilizers;

    public GemInfusionManager() {
        recipes = new ArrayList<>();
        stabilizers = new HashMap<>();
    }

    public void addRecipe (GemInfusionRecipe r) {
        recipes.add(r);
    }

    public void addStabilizer(String str, int int_) {
        stabilizers.put(str, int_);
    }

    public int getStabilizer(String str) {
        if (stabilizers.containsKey(str)) {
            return stabilizers.get(str);
        }
        else {
            return 0;
        }
    }

    public GemInfusionRecipe getPossibleRecipe(ItemStack mainItem, List<Item> items) {
        for (GemInfusionRecipe x : recipes) {

            String mi = TagWizard.getItemTag(mainItem, "id");
            if (mi == null) {
                mi = mainItem.getType().toString();
            }
            if (x.mainItem.equals(mi)) {
                List<String> xx = new ArrayList<>();
                xx.addAll(x.items);
                Boolean worked = true;
                for (Item y : items) {
                    mi = TagWizard.getItemTag(y.getItemStack(), "id");
                    if (mi == null) {
                        mi = mainItem.getType().toString();
                    }
                    if (xx.contains(mi)) {
                        xx.remove(x.items.indexOf(mi));
                    }
                    else {
                        worked = false;
                        break;
                    }
                }
                if (worked && xx.size() == 0) {
                    return x;
                }
            }
        }
        return null;
    }
}
