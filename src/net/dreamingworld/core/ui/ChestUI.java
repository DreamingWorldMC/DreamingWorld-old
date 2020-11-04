package net.dreamingworld.core.ui;

import net.dreamingworld.DreamingWorld;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ChestUI implements Listener {

    private final String title;
    private final Inventory inventory;
    private final Map<Integer, SlotInteractType> slots;

    public ChestUI(String title, int rowCount) {
        this.title = title;

        inventory = Bukkit.createInventory(null, rowCount * 9, title);
        slots = new HashMap<>();

        for (int i = 0; i < inventory.getSize(); i++)
            setSlotInteractType(i, SlotInteractType.HANDS_OFF);

        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
    }


    public void setSlotInteractType(int id, SlotInteractType type) {
        slots.put(id, type);
    }

    public void setSlotInteractType(int x, int y, SlotInteractType type) {
        setSlotInteractType(y * 9 + x, type);
    }


    public void putItem(int id, ItemStack item) {
        inventory.setItem(id, item);
    }

    public void putItem(int x, int y, ItemStack item) {
        putItem(y * 9 + x, item);
    }

    public void fill(ItemStack item) {
        for (int i = 0; i < inventory.getSize(); i++)
            putItem(i, item);
    }


    public void show(Player player) {
        player.openInventory(inventory);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getInventory().getName().equals(title))
            return;

        if (e.getRawSlot() >= e.getInventory().getSize())
            return;

        SlotInteractType t = slots.get(e.getSlot());

        if (e.getAction() == InventoryAction.PLACE_ALL || e.getAction() == InventoryAction.PLACE_ONE || e.getAction() == InventoryAction.PLACE_SOME) {
            if (t == SlotInteractType.HANDS_OFF || t == SlotInteractType.TAKE_ONLY)
                e.setCancelled(true);
        } else if ((e.getAction() == InventoryAction.PICKUP_ALL || e.getAction() == InventoryAction.PICKUP_ONE || e.getAction() == InventoryAction.PICKUP_SOME || e.getAction() == InventoryAction.PICKUP_HALF) || (e.getAction() == InventoryAction.DROP_ALL_SLOT || e.getAction() == InventoryAction.DROP_ONE_SLOT)) {
            if (t == SlotInteractType.HANDS_OFF || t == SlotInteractType.PUT_ONLY)
                e.setCancelled(true);
        }
    }
}
