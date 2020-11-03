package net.dreamingworld.gameplay.manacraft.blocks;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.PacketWizard;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.UtilItems;
import net.dreamingworld.core.blocks.CustomBlock;
import net.dreamingworld.core.crafting.CustomRecipe;
import net.dreamingworld.core.ui.ChestUI;
import net.dreamingworld.core.ui.SlotInteractType;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BasicManaGenerator extends CustomBlock {

    public BasicManaGenerator() {
        id = "basic_mana_generator";
        item = new ItemStack(Material.FURNACE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Basic Mana Generator");

        List<String> lore = new ArrayList<>();
        lore.add(Util.formatString("&7Outputs &420 lmml&r/&7tick"));

        meta.setLore(lore);
        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem(id, item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "IGI", "NBN", "IGI" });
        recipe.setVanillaIngredient('I', Material.IRON_INGOT);
        recipe.setVanillaIngredient('G', Material.GOLD_INGOT);
        recipe.setVanillaIngredient('B', Material.IRON_BLOCK);
        recipe.setCustomIngredient('N', "ignium");

        DreamingWorld.getInstance().getCraftingManager().registerCraft(recipe);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        if (id.equals(DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(e.getClickedBlock().getLocation()))) {
            e.setCancelled(true);

            ChestUI ui = new ChestUI("Basic Mana Generator", 3);
            ui.fill(UtilItems.nothing());

            ui.putItem(6, 0, new ItemStack(Material.AIR));
            ui.putItem(6, 2, new ItemStack(Material.AIR));
            ui.setSlotInteractType(6, 0, SlotInteractType.TAKE_ONLY);
            ui.setSlotInteractType(6, 2, SlotInteractType.TAKE_ONLY);

            ui.putItem(2, 1, new ItemStack(Material.AIR));
            ui.setSlotInteractType(2, 1, SlotInteractType.PUT_AND_TAKE);

            ItemStack manameter = new ItemStack(Material.STAINED_GLASS_PANE);
            manameter.setDurability((short) 3);
            ItemMeta meta = manameter.getItemMeta();
            meta.setDisplayName("some/1000 lmml");
            manameter.setItemMeta(meta);

            ui.putItem(4, 1, manameter);

            ui.show(e.getPlayer());
        }
    }

    @Override
    public void tick(Location location) {
        PacketWizard.sendParticle(EnumParticle.ENCHANTMENT_TABLE, location.add(0.5, 0.5, 0.5), 100);
    }
}
