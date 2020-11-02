package net.dreamingworld.core.alloys;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlloyManager implements Listener {

    private Map<Material, List<OreAlloy>> alloys;

    public AlloyManager() {
        alloys = new HashMap<>();
    }

    public void registerAlloy(Material base, OreAlloy alloy) {
        alloys.putIfAbsent(base, new ArrayList<>()); // Initializing list if it was not initialized

        if (!alloys.get(base).contains(alloy))
            alloys.get(base).add(alloy);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!alloys.containsKey(e.getBlock().getType()))
            return;

        ItemStack drop = new ItemStack(Material.AIR);
        for (OreAlloy alloy : alloys.get(e.getBlock().getType())) {
            if (alloy.dropOnBreak) {
                drop = alloy.randomize();

                if (drop.getType() != Material.AIR)
                    break;
            }
        }

        if (drop.getType() != Material.AIR && !e.getPlayer().getItemInHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH) && e.getPlayer().getItemInHand().getType() != Material.SHEARS)
            e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation(), drop);
    }

    @EventHandler
    public void onSmelt(FurnaceSmeltEvent e) {
        if (!alloys.containsKey(e.getSource().getType()))
            return;

        ItemStack drop = new ItemStack(Material.AIR);
        for (OreAlloy alloy : alloys.get(e.getSource().getType())) {
            if (alloy.dropOnSmelt) {
                drop = alloy.randomize();

                if (drop.getType() != Material.AIR)
                    break;
            }
        }

        if (drop.getType() != Material.AIR) {
            if (e.getBlock().getRelative(BlockFace.DOWN).getType() == Material.HOPPER) {
                InventoryHolder hopper = (InventoryHolder) e.getBlock().getRelative(BlockFace.DOWN).getState();

                boolean isThereNotFullStacks = false;
                for (ItemStack item : hopper.getInventory().getContents()) {
                    if (item.isSimilar(drop) && item.getAmount() < item.getMaxStackSize()) {
                        isThereNotFullStacks = true;
                        break;
                    }
                }

                if (hopper.getInventory().firstEmpty() != -1 || isThereNotFullStacks) {
                    hopper.getInventory().addItem(drop);
                    return;
                }
            }
        }

        e.getBlock().getLocation().getWorld().dropItem(e.getBlock().getLocation().add(0, 1, 0), drop);
    }
}
