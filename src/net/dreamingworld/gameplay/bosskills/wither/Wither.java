package net.dreamingworld.gameplay.bosskills.wither;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.customentities.CustomEntity;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;


public class Wither extends CustomEntity implements Listener {
    public Wither() {
        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
    }

    @EventHandler
    public void onSummonWither(CreatureSpawnEvent e) {
        if (e.getEntity().getType().equals(EntityType.WITHER)) {
            e.getEntity().setMaxHealth(750);
            e.getEntity().setHealth(750);
            e.getEntity().setCustomName("|WITHER|");

            DreamingWorld.getInstance().getEntityManager().summonEntity(e.getLocation(), "wither_minion");
            DreamingWorld.getInstance().getEntityManager().summonEntity(e.getLocation(), "wither_minion");
            DreamingWorld.getInstance().getEntityManager().summonEntity(e.getLocation(), "wither_minion");
            DreamingWorld.getInstance().getEntityManager().summonEntity(e.getLocation(), "wither_minion");
        }
    }
}
