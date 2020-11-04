package net.dreamingworld.core.mana;

import net.dreamingworld.DreamingWorld;
import org.bukkit.Location;

import java.util.Set;

public class ManaContainer {

    protected int capacity;
    protected int storedMana;

    private Set<Location> inputs;

    public ManaContainer() {
        capacity = 100;
        storedMana = 0;
    }


    public void addInput(Location location) {
        inputs.add(location);
    }

    public void removeInput(Location location) {
        inputs.remove(location);
    }


    public void manaTick() {
        for (Location input : inputs) {
            if (storedMana < capacity) {
                String inpMana = DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(input, "mana");

                if (inpMana == null)
                    continue;

                int inputMana = Integer.parseInt(inpMana);

                if (inputMana <= 0)
                    continue;

                storedMana++;
                inputMana--;

                DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(input, "mana", String.valueOf(inputMana));
            }
        }
    }
}
