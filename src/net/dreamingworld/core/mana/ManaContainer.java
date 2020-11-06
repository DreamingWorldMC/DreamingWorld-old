package net.dreamingworld.core.mana;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.Util;
import net.dreamingworld.core.blocks.CustomBlock;
import net.minecraft.server.v1_8_R3.MathHelper;
import org.bukkit.Location;

import java.util.Set;

public abstract class ManaContainer extends CustomBlock {

    public ManaContainer() {

    }


    public void setMana(Location location, int mana) {
        DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(location, "storedMana", String.valueOf(mana));
    }

    protected void setMaxMana(Location location, int maxMana) {
        DreamingWorld.getInstance().getBlockManager().getBlockDataManager().setBlockTag(location, "capacity", String.valueOf(maxMana));
    }


    public int getMaxMana(Location location) {
        String s = DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(location, "capacity");

        if (s == null)
            return -1;

        return Integer.parseInt(s);
    }

    public int getMana(Location location) {
        String s = DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(location, "storedMana");

        if (s == null)
            return -1;

        return Integer.parseInt(s);
    }


    public void manaTick(Location location) {
        int sm = getMana(location);
        int cp = getMaxMana(location);

        int storedMana = Math.max(sm, 0);
        int capacity = cp >= 0 ? cp : storedMana;

        String inpSet = DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(location, "inputs");

        if (inpSet == null)
            return;

        Set<String> inputs = Util.setFromJson(inpSet);

        for (String inp : inputs) {
            String[] inpCoords = inp.split("_");
            Location input = new Location(location.getWorld(), Integer.parseInt(inpCoords[0]), Integer.parseInt(inpCoords[1]), Integer.parseInt(inpCoords[2]));

            if (storedMana < capacity) {
                if (DreamingWorld.getInstance().getBlockManager().getCustomBlockAt(input) == null)
                    continue;

                int inputMana = getMana(input);
                if (inputMana <= 0)
                    continue;

                int needed = capacity - storedMana;
                if (needed <= 0)
                    break;

                String s = DreamingWorld.getInstance().getBlockManager().getBlockDataManager().getBlockTag(input, "maxOutput");
                int canGetNow = MathHelper.clamp(needed, 0, s == null ? inputMana : Integer.parseInt(s));

                storedMana += canGetNow;
                inputMana -= canGetNow;

                setMana(input, inputMana);
            }
        }

        setMana(location, storedMana);
        setMaxMana(location, capacity);
    }
}
