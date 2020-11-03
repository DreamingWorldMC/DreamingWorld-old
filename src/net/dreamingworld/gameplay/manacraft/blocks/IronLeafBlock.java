package net.dreamingworld.gameplay.manacraft.blocks;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.blocks.CustomBlock;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;

public class IronLeafBlock extends CustomBlock {

    public IronLeafBlock ()  {
        id = "iron_leaf";
        item = new ItemStack(Material.LEAVES);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Iron leaf");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);


        DreamingWorld.getInstance().getItemManager().registerItem(id, item);
    }

    @Override
    public void tick(Location location) {
        PacketWizard.sendParticle(EnumParticle.VILLAGER_HAPPY, location.add(0.5, 0.5, 0.5), 5);
    }

    @EventHandler
    public void onDecay(LeavesDecayEvent e) throws IOException {
        if (id.equals(DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getBlock().getLocation()))) {
            DreamingWorld.getInstance().getBlockManager().removeBlock(e.getBlock().getLocation(), "iron_leaf");

        }
    }

}
