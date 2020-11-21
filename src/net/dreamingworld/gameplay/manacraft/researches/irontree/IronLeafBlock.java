package net.dreamingworld.gameplay.manacraft.researches.irontree;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.blocks.CustomBlock;
import net.dreamingworld.gameplay.manacraft.items.HotStaff;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class IronLeafBlock extends CustomBlock {

    public IronLeafBlock()  {
        id = "iron_leaf";
        item = new ItemStack(Material.LEAVES);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Iron leaf");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem(id, item);

        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
    }

    @Override
    public void tick(Location location) {
        PacketWizard.sendParticle(EnumParticle.VILLAGER_HAPPY, location.add(0.5, 0.5, 0.5), 1);
    }

    @EventHandler
    public void onDecay(LeavesDecayEvent e) throws IOException {
        if (id.equals(DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getBlock().getLocation()))) {
            DreamingWorld.getInstance().getBlockManager().removeBlock(e.getBlock().getLocation(), "iron_leaf");

            int random = ThreadLocalRandom.current().nextInt(0, 1001);

            if (random <= 2) // 0.2% chance
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), DreamingWorld.getInstance().getItemManager().get("iron_tree_sapling"));
        }
    }
}
