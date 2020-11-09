package net.dreamingworld.gameplay.bossskills;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.gameplay.bossskills.items.WitherHeart;
import net.dreamingworld.gameplay.bossskills.wither.Wither;
import net.dreamingworld.gameplay.bossskills.wither.WitherMinion;


public class Bossskills {

    public static void initialize() {
        long begin = System.currentTimeMillis();
        // mics
        new deathMessages();

        // items
        new WitherHeart();

        // entities
        DreamingWorld.getInstance().getEntityManager().addEntity("wither_minion", new WitherMinion());

        //bosses
        new Wither();

        long time = System.currentTimeMillis() - begin;

        DreamingWorld.getInstance().getLogger().info("Bossskills initialized [" + time + " ms]");
    }
}
