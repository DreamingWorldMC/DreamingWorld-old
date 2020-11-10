package net.dreamingworld.gameplay.bosskills;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.gameplay.bosskills.items.WitherHeart;
import net.dreamingworld.gameplay.bosskills.wither.Wither;
import net.dreamingworld.gameplay.bosskills.wither.WitherMinion;


public class BossKills {

    public static void initialize() {
        long begin = System.currentTimeMillis();
        // mics
        new DeathMessages();

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
