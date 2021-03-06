package net.dreamingworld.gameplay.foodcraft;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.gameplay.foodcraft.entities.*;
import net.dreamingworld.gameplay.foodcraft.food.*;
import net.dreamingworld.gameplay.foodcraft.food.RawDuck;

public class Foodcraft {
    public static void initialize() {
        long begin = System.currentTimeMillis();


        // Food
        DreamingWorld.getInstance().getFoodManager().addFoodItem(new IgniumHotSauce());

        DreamingWorld.getInstance().getFoodManager().addFoodItem(new RawDuck()); //duck
        DreamingWorld.getInstance().getFoodManager().addFoodItem(new CookedDuck());
        DreamingWorld.getInstance().getFoodManager().addFoodItem(new DuckWithHotSauce());

        DreamingWorld.getInstance().getFoodManager().addFoodItem(new RawBoar()); // boar
        DreamingWorld.getInstance().getFoodManager().addFoodItem(new CookedBoar());

        DreamingWorld.getInstance().getFoodManager().addFoodItem(new RawTurkey()); // turkey
        DreamingWorld.getInstance().getFoodManager().addFoodItem(new CookedTurkey());
        DreamingWorld.getInstance().getFoodManager().addFoodItem(new TurkeyWithHotSauce());


        // Entities
        DreamingWorld.getInstance().getEntityManager().addEntity("duck", new Duck());
        DreamingWorld.getInstance().getEntityManager().addEntity("boar", new Boar());

        long time = System.currentTimeMillis() - begin;
        DreamingWorld.getInstance().getLogger().info("Foodcraft initialized [" + time + " ms]");
    }
}
