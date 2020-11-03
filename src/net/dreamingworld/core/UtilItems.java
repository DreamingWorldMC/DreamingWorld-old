package net.dreamingworld.core;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UtilItems {

    private static ItemStack nothing;

    public static void initialize() {
        nothing = new ItemStack(Material.STAINED_GLASS_PANE);
        nothing.setDurability((short) 7);

        ItemMeta meta = nothing.getItemMeta();
        meta.setDisplayName(" ");

        nothing.setItemMeta(meta);
    }


    public static ItemStack nothing() {
        return nothing;
    }
}
