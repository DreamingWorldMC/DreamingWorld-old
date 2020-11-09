package net.dreamingworld.gameplay.bossskills;

import net.dreamingworld.DreamingWorld;


public class Bossskills {

    public static void initialize() {
        long begin = System.currentTimeMillis();

        new deathMessages();



        long time = System.currentTimeMillis() - begin;

        DreamingWorld.getInstance().getLogger().info("Manacraft initialized [" + time + " ms]");
    }
}
