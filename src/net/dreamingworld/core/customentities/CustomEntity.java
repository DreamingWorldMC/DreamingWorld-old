package net.dreamingworld.core.customentities;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.Map;

public class CustomEntity {

    protected String name;
    protected int health;
    protected EntityType entityType;
    protected int damage = 3;

    protected int expDrop;
    protected Map<ItemStack, Integer> drops;

    protected ItemStack itemInHand;
    protected ItemStack itemOnHead;
    protected ItemStack itemOnChest;
    protected ItemStack itemOnLegs;
    protected ItemStack itemOnBoots;

    protected String spawnType;

    protected boolean hasArmor;

    protected void callOnSpawn(LivingEntity e) {};

    protected List<PotionEffect> effects;
}
