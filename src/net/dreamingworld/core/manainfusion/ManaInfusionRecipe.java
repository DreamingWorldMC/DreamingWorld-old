package net.dreamingworld.core.manainfusion;

import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Material;

public class ManaInfusionRecipe {
    protected boolean fromCustomItem = true;
    protected Material vanillaItem;
    protected String customItem;
    protected Integer manaTakes;
    protected EnumParticle particle = EnumParticle.ENCHANTMENT_TABLE;
    protected int particleAmount = 5;
}
