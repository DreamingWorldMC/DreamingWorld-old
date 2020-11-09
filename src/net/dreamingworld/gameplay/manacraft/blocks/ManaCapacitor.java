package net.dreamingworld.gameplay.manacraft.blocks;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.UtilItems;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.dreamingworld.core.mana.ManaContainer;
import net.dreamingworld.core.ui.ChestUI;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Directional;

import java.util.ArrayList;
import java.util.List;

public class ManaCapacitor extends ManaContainer {

    public ManaCapacitor() {
        id = "mana_capacitor";
        item = new ItemStack(Material.DISPENSER);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Mana Capacitor");

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Stores mana");

        meta.setLore(lore);
        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem(id, item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "IGI", "ICI", "BGB" });
        recipe.setVanillaIngredient('I', Material.IRON_INGOT);
        recipe.setVanillaIngredient('G', Material.GLASS);
        recipe.setCustomIngredient('C', "mana_core");
        recipe.setVanillaIngredient('B', Material.BRICK);
        recipe.setVanillaIngredient('G', Material.GOLD_INGOT);

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.isCancelled())
            return;

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        if (id.equals(DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getClickedBlock().getLocation()))) {
            e.setCancelled(true);

            ChestUI ui = new ChestUI("Mana Capacitor", 1);
            ui.fill(UtilItems.nothing());

            ItemStack manameter = new ItemStack(Material.STAINED_GLASS_PANE);
            manameter.setDurability((short) 3);
            ItemMeta meta = manameter.getItemMeta();
            meta.setDisplayName(getMana(e.getClickedBlock().getLocation()) + "/" + getMaxMana(e.getClickedBlock().getLocation()) + " lmml");
            manameter.setItemMeta(meta);

            ui.putItem(4, 0, manameter);

            ui.show(e.getPlayer());
        }
    }


    @Override
    public void place(Block block) {
        setMaxMana(block.getLocation(), 10000);
        setMana(block.getLocation(), 0);


        block.setData((byte)9);

    }

    @Override
    public void tick(Location location) {
        manaTick(location);
        PacketWizard.sendParticle(EnumParticle.TOWN_AURA, location.add(0.5, 0.5, 0.5), 100);
    }
}
