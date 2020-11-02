package net.dreamingworld.core.ui;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class ChestUI {

    private final Inventory inventory;
    private List<Integer> writable;

    public ChestUI(String title, int rowCount) {
        inventory = Bukkit.createInventory(null, rowCount * 9);

    }


}
