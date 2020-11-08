package net.dreamingworld.core.manainfusion;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.gameplay.manacraft.blocks.ManaInfuser;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ManaInfusionManager {

    public Map<String, ManaInfusionRecipe> recipes;

    public ManaInfusionManager() {
        recipes = new HashMap<>();
    }

    public void addRecipe(ManaInfusionRecipe r) {
        if (r.fromCustomItem) {
            recipes.put(r.customItem, r);
        }
        else {
            recipes.put(r.vanillaItem.toString(), r);
        }
    }

    public ManaInfusionRecipe getTo(ItemStack i) {
        if (recipes.containsKey(TagWizard.getItemTag(i, "id"))) {
            return recipes.get(TagWizard.getItemTag(i, "id"));
        }
        else if (TagWizard.getItemTag(i, "id") == null && recipes.containsKey(i.getType().toString())) {
            return recipes.get(i.getType().toString());
        }


        return null;
    }

}
