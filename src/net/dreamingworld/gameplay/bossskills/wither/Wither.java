package net.dreamingworld.gameplay.bossskills.wither;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.customentities.CustomEntity;
import net.dreamingworld.core.structures.EasyBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

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
