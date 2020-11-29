package net.dreamingworld.gameplay.manacraft.researches.geminfusion.altar;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.blocks.CustomBlock;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class MainPedestal extends CustomBlock {

    public MainPedestal()  {
        id = "main_pedestal";
        item = new ItemStack(Material.COBBLE_WALL);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Main pedestal");

        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem(id, item);

        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
    }

    @Override
    public void place(Block block) {
        DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(block.getLocation(), "item", "false");
    }

    @Override
    public void tick(Location location) {
        PacketWizard.sendParticle(EnumParticle.CRIT_MAGIC, location.add(0.5, 1.5, 0.5), 10, 0.4f, 0.5f);
    }


    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null || e.getAction() != Action.RIGHT_CLICK_BLOCK || DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getClickedBlock().getLocation()) == null || TagWizard.getItemTag(e.getItem(), "id") == null || !DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getClickedBlock().getLocation()).equals("main_pedestal"))
            return;

        if (e.getPlayer().getItemInHand() != null) {

        }


    }
}
