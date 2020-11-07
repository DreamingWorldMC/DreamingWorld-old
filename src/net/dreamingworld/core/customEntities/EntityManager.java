package net.dreamingworld.core.customEntities;

import net.dreamingworld.DreamingWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class EntityManager implements Listener {

    public Map<String, CustomEntity> entities;

    public EntityManager() {

        entities = new HashMap<>();

    }

    public void addEntity(String name, CustomEntity ce) {
        entities.put(name, ce);
    }

    public org.bukkit.entity.Entity summonEntity(Location loc, String entity) {

        if (!entities.containsKey(entity)) return null;

        LivingEntity ent = (LivingEntity) loc.getWorld().spawnEntity(loc, entities.get(entity).entityType);
        ent.setCustomName(entities.get(entity).name);
        ent.setMaxHealth(entities.get(entity).Health);
        ent.setHealth(entities.get(entity).Health);
        ent.setMetadata("id", new FixedMetadataValue(DreamingWorld.getInstance(), entity));


        if (entities.get(entity).hasArmor) {
            ent.getEquipment().setBoots(entities.get(entity).itemOnBoots);
            ent.getEquipment().setChestplate(entities.get(entity).itemOnChest);
            ent.getEquipment().setHelmet(entities.get(entity).itemOnHead);
            ent.getEquipment().setLeggings(entities.get(entity).itemOnlegs);
            ent.getEquipment().setItemInHand(entities.get(entity).itemInHand);
        }

        ent.getEquipment().setChestplateDropChance(0);
        ent.getEquipment().setBootsDropChance(0);
        ent.getEquipment().setHelmetDropChance(0);
        ent.getEquipment().setLeggingsDropChance(0);


        ent.setRemoveWhenFarAway(true);
        return ent;
    }


    @EventHandler
    public void onKill(EntityDeathEvent e) {
        if (e.getEntity().hasMetadata("id") || !entities.containsKey(e.getEntity().getMetadata("id").get(0).asString()))
            return;

        e.setDroppedExp(entities.get(e.getEntity().getMetadata("id").get(0).asString()).expDrop);

        e.getDrops().clear();

        for (Map.Entry<ItemStack, Integer> x : entities.get(e.getEntity().getMetadata("id").get(0).asString()).drops.entrySet()) {
            if (ThreadLocalRandom.current().nextInt(0, 101) < x.getValue())
                e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), x.getKey());
        }

    }



}