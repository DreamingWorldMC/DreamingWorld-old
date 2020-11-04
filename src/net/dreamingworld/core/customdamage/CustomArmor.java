package net.dreamingworld.core.customdamage;


import java.util.HashMap;
import java.util.Map;

public class CustomArmor {

    private Map<String, Integer> armor;

    public CustomArmor() {
        armor = new HashMap<>();
    }

    public void addPiece(String name, int armorPoints) {
        armor.put(name, armorPoints);
    }

    public int getPiece(String name) {
        if (name == null || !armor.containsKey(name))
            return -1;

        return armor.get(name);
    }

}
