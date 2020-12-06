package net.dreamingworld.gameplay.manacraft.researches.astrum;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.Util;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StarDust implements Listener {

    public StarDust() {
        ItemStack item = new ItemStack(Material.SUGAR);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Util.formatString("&3&k$&r&3Star dust&k$"));

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        DreamingWorld.getInstance().getItemManager().registerItem("star_dust", item);

        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.isCancelled()) {
            return;
        }

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getPlayer().getItemInHand() != null && TagWizard.getItemTag(e.getPlayer().getItemInHand(), "id") != null && TagWizard.getItemTag(e.getPlayer().getItemInHand(), "id").equals("star_dust") && e.getClickedBlock().getType() == Material.IRON_BLOCK && DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getClickedBlock().getLocation()) == null && DreamingWorld.getInstance().getResearchManager().playerHasResearch(e.getPlayer(), "astrum")) {
            e.getClickedBlock().setType(Material.AIR);
            e.getClickedBlock().getWorld().dropItem(e.getClickedBlock().getLocation(), DreamingWorld.getInstance().getItemManager().get("astrum_ingot"));

            PacketWizard.sendParticle(EnumParticle.ENCHANTMENT_TABLE, e.getClickedBlock().getLocation().add(0.5, 0.5, 0.5), 10);
            PacketWizard.sendParticle(EnumParticle.CRIT_MAGIC, e.getClickedBlock().getLocation().add(0.5, 0.5, 0.5), 10);
            PacketWizard.sendParticle(EnumParticle.VILLAGER_HAPPY, e.getClickedBlock().getLocation().add(0.5, 0.5, 0.5), 10);

            if (e.getItem().getAmount() > 1) {
                e.getItem().setAmount(e.getItem().getAmount() - 1);
            } else {
                e.getPlayer().getInventory().setItemInHand(new ItemStack(Material.AIR));
            }
        }
    }
}
