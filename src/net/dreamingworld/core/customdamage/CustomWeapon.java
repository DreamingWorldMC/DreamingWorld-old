package net.dreamingworld.core.customdamage;

import java.util.HashMap;
import java.util.Map;

public class CustomWeapon {

    private Map<String, Double> weapons;

    public CustomWeapon() {
        weapons = new HashMap<>();
    }

    public void addWeapon(String name, double damagePoints) {
        weapons.put(name, damagePoints);
    }

    public double getWeapon(String name) {
        if (name == null || !weapons.containsKey(name))
            return -1;
        return weapons.get(name);
    }

}
