package net.dreamingworld.core.geminfusion;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class GemInfusionRecipe {
    protected Map<String, Integer> gems;
    protected ItemStack result;
    protected String mainItem;
    protected List<String> items;
    protected Float instability;
    protected String reserach;

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

    public void setReserach(String reserach) {
        this.reserach = reserach;
    }

    @Override
    public String toString() {
        return "GemInfusionRecipe{" +
                "gems=" + gems +
                ", result=" + result +
                ", mainItem='" + mainItem + '\'' +
                ", items=" + items +
                ", instability=" + instability +
                ", reserach='" + reserach + '\'' +
                '}';
    }
}
