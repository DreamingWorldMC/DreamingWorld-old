package net.dreamingworld.core.research;

import org.bukkit.inventory.meta.BookMeta;

import java.util.*;

public class Research {
    protected String id;
    protected String name;
    protected String description;

    // First string is riddle, second string is item, to put vanilla item you have to type in Material.ITEM.toString();
    protected Map<String, String> items;

    protected BookMeta book;
}
