package net.dreamingworld.gameplay.qolgear;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.gameplay.qolgear.blocks.Elevator;

public class QOLGear {
    public static void initialize() {
        DreamingWorld.getInstance().getBlockManager().registerBlock(new Elevator());
    }
}
