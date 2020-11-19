package net.dreamingworld.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagWizard {

    public static void addItemTag(ItemStack item, String tag, String value) {
        if (item.getType() == Material.AIR) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        List<String> lore;
        if (meta.hasLore()) {
            lore = meta.getLore();
        } else {
            lore = new ArrayList<>();
        }

        Map<String, String> data = null;
        int n = -1;
        for (String s : lore) {
            String vs = makeVisible(s);
            if (vs.startsWith("$DATA")) {
                data = getTagData(vs);
                n = lore.indexOf(s);
                break;
            }
        }

        if (data == null) {
            data = new HashMap<>();
        }

        data.put(tag, value);

        if (n == -1) {
            lore.add(makeInvisible(makeTagData(data)));
        } else {
            lore.set(n, makeInvisible(makeTagData(data)));
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public static String getItemTag(ItemStack item, String tag) {
        if (!item.hasItemMeta()) {
            return null;
        }

        ItemMeta meta = item.getItemMeta();

        List<String> lore;
        if (meta != null && meta.hasLore()) {
            lore = meta.getLore();
        } else {
            return null;
        }

        Map<String, String> data = null;
        int n = -1;
        for (String s : lore) {
            String vs = makeVisible(s);
            if (vs.startsWith("$DATA")) {
                data = getTagData(vs);
                n = lore.indexOf(s);
                break;
            }
        }

        if (data == null) {
            return null;
        }

        return data.get(tag);
    }


    public static boolean hasData(ItemStack item) {
        if (!item.hasItemMeta()) {
            return false;
        }

        ItemMeta m = item.getItemMeta();

        if (!m.hasLore()) {
            return false;
        }

        for (String s : m.getLore()) {
            if (makeVisible(s).startsWith("$DATA")) {
                return true;
            }
        }

        return false;
    }


    private static String makeTagData(Map<String, String> data) {
        return "$DATA:" + new Gson().toJson(data);
    }

    private static Map<String, String> getTagData(String data) {
        return new Gson().fromJson(data.replace("$DATA:", ""), new TypeToken<HashMap<String, String>>() {}.getType());
    }


    private static String makeInvisible(String string) {
        return StringUtils.chop(string.replaceAll("(.)?", "\u00a7$1"));
    }

    private static String makeVisible(String string) {
        return string.replaceAll("(.)(.)?", "$2");
    }
}
