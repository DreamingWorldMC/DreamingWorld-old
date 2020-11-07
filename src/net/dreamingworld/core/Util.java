package net.dreamingworld.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.dreamingworld.DreamingWorld;

import java.util.*;

public class Util {

    public static String formatString(String string) {
        return string.replace("$(PC)", DreamingWorld.primaryColor.toString()).replace("$(SC)", DreamingWorld.secondaryColor.toString()).replace('&', '\u00a7');
    }


    public static Set<String> setFromJson(String json) {
        return json == null ? new HashSet<>() : new Gson().fromJson(json, new TypeToken<Set<String>>() {}.getType());
    }

    public static String addToJsonSet(String json, String element) {
        Set<String> set = setFromJson(json);

        set.add(element);
        return new Gson().toJson(set);
    }


    public static List<String> smartAutocomplete(List<String> variants, String[] args) {
        List<String> autocomplete = new ArrayList<>();

        for (String variant : variants)
            if (variant.startsWith(args[args.length - 1]))
                autocomplete.add(variant);

        return autocomplete;
    }


    public static <T extends Object> List<T[]> splitArray(T[] array, int max){
        int x = array.length / max;
        int r = array.length % max;

        int lower = 0;
        int upper = 0;

        List<T[]> list = new ArrayList<T[]>();
        for (int i = 0; i < x; i++){
            upper += max;
            list.add(Arrays.copyOfRange(array, lower, upper));
            lower = upper;
        }

        if (r > 0) {
            list.add(Arrays.copyOfRange(array, lower, (lower + r)));
        }

        return list;
    }
}
