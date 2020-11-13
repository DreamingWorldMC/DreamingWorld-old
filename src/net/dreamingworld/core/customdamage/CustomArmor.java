package net.dreamingworld.core.customdamage;


import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.TagWizard;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomArmor implements Listener {

    private Map<String, Integer> armor;
    private List<String> canPutOnHead;

    public CustomArmor() {
        armor = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
        canPutOnHead = new ArrayList<>();
    }

    public void addPiece(String name, int armorPoints) {
        armor.put(name, armorPoints);
    }

    public void addHeadPiece(String name) {
        canPutOnHead.add(name);
    }

    public int getPiece(String name) {
        if (name == null || !armor.containsKey(name))
            return -1;

        return armor.get(name);
    }

    @EventHandler
    public void onClick (PlayerInteractEvent e) {
        if (canPutOnHead.contains(TagWizard.getItemTag(e.getItem(), "id"))) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
                e.setCancelled(true);
                if (e.getPlayer().getEquipment().getHelmet() == null) {
                    e.getPlayer().getEquipment().setHelmet(e.getItem());
                    e.getPlayer().getInventory().setItemInHand(new ItemStack(Material.AIR));
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) {
            if (canPutOnHead.contains(TagWizard.getItemTag(e.getCurrentItem(), "id"))) {
                if (e.getInventory().getType() == InventoryType.CRAFTING) {
                    e.setCancelled(true);
                    if (e.getWhoClicked().getEquipment().getHelmet() == null) {
                        e.getWhoClicked().getEquipment().setHelmet(e.getCurrentItem());
                        e.setCurrentItem(new ItemStack(Material.AIR));
                    }
                }
            }
        }
        if (canPutOnHead.contains(TagWizard.getItemTag(e.getCursor(), "id"))) {
            if (e.getSlot() == 39 && e.getInventory().getType() == InventoryType.CRAFTING) {
                e.setCancelled(true);
                if (e.getWhoClicked().getEquipment().getHelmet() == null) {
                    e.getWhoClicked().getEquipment().setHelmet(e.getCursor());
                    e.setCursor(new ItemStack(Material.AIR));
                }
            }
        }
    }
}
