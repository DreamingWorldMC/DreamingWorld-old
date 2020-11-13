package net.dreamingworld.core.manainfusion;

import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ManaInfusionRecipe {
    public boolean fromCustomItem = true;
    public Material vanillaItem;
    public String customItem;
    public Integer manaTakes;

    public ItemStack result;
}
