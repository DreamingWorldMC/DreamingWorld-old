package net.dreamingworld.gameplay.bossskills;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.gameplay.bossskills.wither.WitherMinion;


public class Bossskills {

    public static void initialize() {
        long begin = System.currentTimeMillis();

        new deathMessages();
        DreamingWorld.getInstance().getEntityManager().addEntity("wither_minion", new WitherMinion());


        long time = System.currentTimeMillis() - begin;

        DreamingWorld.getInstance().getLogger().info("Bossskills initialized [" + time + " ms]");
    }
}
