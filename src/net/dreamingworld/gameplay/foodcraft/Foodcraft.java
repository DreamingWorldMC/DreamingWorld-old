package net.dreamingworld.gameplay.foodcraft;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.gameplay.foodcraft.entities.*;
import net.dreamingworld.gameplay.foodcraft.food.RawDuck;

public class Foodcraft {
    public static void initialize() {
        long begin = System.currentTimeMillis();


        // Food
        DreamingWorld.getInstance().getFoodManager().addFoodItem(new RawDuck());

        // Entities
        DreamingWorld.getInstance().getEntityManager().addEntity("duck", new Duck());

        long time = System.currentTimeMillis() - begin;
        DreamingWorld.getInstance().getLogger().info("Foodcraft initialized [" + time + " ms]");
    }
}
