package net.dreamingworld.gameplay.foodcraft;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.gameplay.foodcraft.entities.*;

public class Foodcraft {
    public static void initialize() {
        long begin = System.currentTimeMillis();

        // entities
        DreamingWorld.getInstance().getEntityManager().addEntity("duck", new Duck());


        long time = System.currentTimeMillis() - begin;
        DreamingWorld.getInstance().getLogger().info("Foodcraft initialized [" + time + " ms]");
    }
}
