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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class GreenIronMaker extends CustomBlock {

    public GreenIronMaker() {
        id = "green_iron_maker";
        item = new ItemStack(Material.IRON_PLATE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Green iron maker");

        List<String> lore = new ArrayList<>();
        lore.add(Util.formatString("&7Helpful for getting green iron."));

        meta.setLore(lore);
        item.setItemMeta(meta);

        DreamingWorld.getInstance().getItemManager().registerItem(id, item);

        CustomRecipe recipe = new CustomRecipe(item);
        recipe.shape(new String[] { "IBI", "NNN", "IBI" });
        recipe.setVanillaIngredient('I', Material.IRON_INGOT);
        recipe.setVanillaIngredient('B', Material.IRON_BLOCK);
        recipe.setCustomIngredient('N', "ignium");

        DreamingWorld.getInstance().getCraftingManager().registerRecipe(recipe);

        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
    }


    @Override
    public void tick(Location location) {
        PacketWizard.sendParticle(EnumParticle.VILLAGER_HAPPY, location.add(0.5, 0, 0.5), 2);

        if (location.getWorld().getBlockAt(location).isBlockIndirectlyPowered()) {
            Collection<Entity> entities = location.getWorld().getNearbyEntities(location.add(0, 1,0), 1, 1, 1);
            for (Entity entity : entities) {
                if (entity instanceof Item) {
                    ItemStack item = ((Item) entity).getItemStack();
                    if (DreamingWorld.getInstance().getItemManager().checkItemAuthenticity(item, "iron_wood")) {
                        for (int i = 0; i < item.getAmount(); i++)
                            location.getWorld().dropItem(location, DreamingWorld.getInstance().getItemManager().get("green_iron_nugget"));
                        PacketWizard.sendParticle(EnumParticle.VILLAGER_ANGRY, location.add(0.5, 0, 0.5), 2);
                        entity.remove();
                    } else if (DreamingWorld.getInstance().getItemManager().checkItemAuthenticity(item, "iron_leaf")) {
                        for (int i = 0; i < item.getAmount(); i++)
                            location.getWorld().dropItem(location, DreamingWorld.getInstance().getItemManager().get("green_iron_nugget"));
                        PacketWizard.sendParticle(EnumParticle.VILLAGER_ANGRY, location.add(0.5, 0, 0.5), 2);
                        entity.remove();
                    }
                }
            }
        }

    }


}
