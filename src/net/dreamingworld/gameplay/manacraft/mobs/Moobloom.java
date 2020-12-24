package net.dreamingworld.gameplay.manacraft.mobs;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.customentities.CustomEntity;
import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;

public class Moobloom extends CustomEntity implements Listener {

    public Moobloom() {
        name = "Moobloom";
        health = 10;
        entityType = EntityType.SHEEP;
        damage = 0;

        spawnType = "DAY_TOP";

        expDrop = 15;

        hasArmor = false;

        drops = new HashMap<>();
        effects = new ArrayList<>();

        drops.put(DreamingWorld.getInstance().getItemManager().get("star_dust"), 75);

        effects.add(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));

    }

    @Override
    protected void callOnSpawn(LivingEntity e) {
        ((Sheep)e).setColor(DyeColor.YELLOW);
    }

    @EventHandler
    public void onShear(PlayerShearEntityEvent e) {

    }

}
