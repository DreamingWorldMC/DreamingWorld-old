package net.dreamingworld.core;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemManager {

    private Map<String, ItemStack> items;

    public ItemManager() {
        items = new HashMap<>();
    }

    public List<String> getIds() {
        return new ArrayList<>(items.keySet());
    }

    public void registerItem(String id, ItemStack item) {
        if (!items.containsKey(id)) {
            TagWizard.addItemTag(item, "id", id);
            items.put(id, item);
        }
    }

    public boolean checkItemAuthenticity(ItemStack item, String id) {
        String a = TagWizard.getItemTag(item, "id");
        return a != null && a.equals(id);
    }

    public ItemStack get(String id) {
        if (!items.containsKey(id))
            return null;

        return new ItemStack(items.get(id));
    }
}
