package net.dreamingworld.core.customEntities;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CustomEntity {

    protected String name;
    protected int Health;
    protected EntityType entityType;

    protected int expDrop;
    protected Map<ItemStack, Integer> drops;

    protected ItemStack itemInHand;
    protected ItemStack itemOnHead;
    protected ItemStack itemOnChest;
    protected ItemStack itemOnlegs;
    protected ItemStack itemOnBoots;

    protected String spawnType;

    protected boolean hasArmor;


}
