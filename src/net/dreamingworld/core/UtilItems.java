package net.dreamingworld.core;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class UtilItems {

    private static ItemStack nothing;

    private static ItemStack arrowBack;
    private static ItemStack arrowForward;

    public static void initialize() {
        nothing = new ItemStack(Material.STAINED_GLASS_PANE);
        nothing.setDurability((short) 7);

        ItemMeta meta = nothing.getItemMeta();
        meta.setDisplayName(" ");

        List<String> lore = new ArrayList<>();
        lore.add(" ");
        meta.setLore(lore);

        nothing.setItemMeta(meta);



        arrowBack = new ItemStack(Material.ARROW);
        meta = arrowBack.getItemMeta();
        meta.setDisplayName("Back");
        arrowBack.setItemMeta(meta);

        arrowForward = new ItemStack(Material.ARROW);
        meta = arrowForward.getItemMeta();
        meta.setDisplayName("Forward");
        arrowForward.setItemMeta(meta);
    }


    public static ItemStack nothing() {
        return nothing;
    }
    public static ItemStack arrowBack() {
        return arrowBack;
    }
    public static ItemStack arrowForward() {
        return arrowForward;
    }
}
