package net.dreamingworld.customfood;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.TagWizard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.HashMap;

public class FoodManager implements Listener {

    public HashMap<String, FoodItem> food;

    public FoodManager() {
        food = new HashMap<>();
    }

    public void addFoodItem(FoodItem fi) {
        food.put(fi.name, fi);
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        if (food.containsKey(TagWizard.getItemTag(e.getItem(), "id"))) {
            e.getPlayer().setFoodLevel(e.getPlayer().getFoodLevel() + food.get(TagWizard.getItemTag(e.getItem(), "id")).foodPoints);
            if (food.get(TagWizard.getItemTag(e.getItem(), "id")).effects != null) {
                e.getPlayer().addPotionEffects(food.get(TagWizard.getItemTag(e.getItem(), "id")).effects);
            }


        }
    }

}
