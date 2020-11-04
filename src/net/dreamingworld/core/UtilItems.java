package net.dreamingworld.core;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class UtilItems {

    private static ItemStack nothing;

    public static void initialize() {
        nothing = new ItemStack(Material.STAINED_GLASS_PANE);
        nothing.setDurability((short) 7);

        ItemMeta meta = nothing.getItemMeta();
        meta.setDisplayName(" ");

        List<String> lore = new ArrayList<>();
        lore.add(" ");
        meta.setLore(lore);

        nothing.setItemMeta(meta);
    }


    public static ItemStack nothing() {
        return nothing;
    }
}
