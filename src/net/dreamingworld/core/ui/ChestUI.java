package net.dreamingworld.core.ui;

import net.dreamingworld.DreamingWorld;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class ChestUI implements Listener {

    private final Inventory inventory;
    private final Map<Integer, SlotInteractType> slots;

    public ChestUI(String title, int rowCount) {
        inventory = Bukkit.createInventory(null, rowCount * 9);
        slots = new HashMap<>();

        for (int i = 0; i < inventory.getSize(); i++)
            setSlotInteractType(i, SlotInteractType.HANDS_OFF);

        Bukkit.getPluginManager().registerEvents(this, DreamingWorld.getInstance());
    }

    public void setSlotInteractType(int id, SlotInteractType type) {
        slots.put(id, type);
    }

    public void setSlotInteractType(int x, int y, SlotInteractType type) {
        int id = y * 9 + x;
        setSlotInteractType(id, type);
    }


    public void show(Player player) {
        player.openInventory(inventory);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getRawSlot() < e.getInventory().getSize()) {
            Bukkit.getLogger().info("hello!");
        }
    }
}
