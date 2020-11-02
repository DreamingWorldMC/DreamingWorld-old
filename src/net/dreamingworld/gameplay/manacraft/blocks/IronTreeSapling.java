package net.dreamingworld.gameplay.manacraft.blocks;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.blocks.CustomBlock;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class IronTreeSapling extends CustomBlock {

    public IronTreeSapling () {
        id = "iron_tree_sapling";
        item = new ItemStack(Material.SAPLING);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Iron tree sapling");

        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem(id, item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "III", "AUA", " U " });
        recipe.setVanillaIngredient('I', Material.IRON_BLOCK);
        recipe.setCustomIngredient('A', "advanced_stick");
        recipe.setCustomIngredient('U', "uranium");

        DreamingWorld.getInstance().getCraftingManager().registerCraft(recipe);
    }

    @Override
    public void tick(Location location) {
        PacketWizard.sendParticle(EnumParticle.FIREWORKS_SPARK, location.add(0.5, 0.5, 0.5), 1);
    }

    @EventHandler
    public void onGrow(StructureGrowEvent e) {
        if (DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getLocation()) == "iron_tree_sapling") {
            e.setCancelled(true);
            e.getWorld().getBlockAt(e.getLocation()).setType(Material.AIR);

        }


    }

}
