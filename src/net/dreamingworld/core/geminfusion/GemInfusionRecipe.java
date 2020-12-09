package net.dreamingworld.core.geminfusion;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class GemInfusionRecipe {
    protected Map<String, Integer> gems;
    protected ItemStack result;
    protected String mainItem;
    protected List<String> items;
    protected float instability;
    protected String research;

    public void setGems(Map<String, Integer> gems) {
        this.gems = gems;
    }

    public void setResult(ItemStack result) {
        this.result = result;
    }

    public void setMainItem(String mainItem) {
        this.mainItem = mainItem;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public void setInstability(Float instability) {
        this.instability = instability;
    }

    public void setResearch(String research) {
        this.research = research;
    }

    public Map<String, Integer> getGems() {
        return gems;
    }

    public ItemStack getResult() {
        return result;
    }

    public String getMainItem() {
        return mainItem;
    }

    public List<String> getItems() {
        return items;
    }

    public float getInstability() {
        return instability;
    }

    public String getResearch() {
        return research;
    }
}
