package net.dreamingworld.gameplay.obelisks;

import net.dreamingworld.DreamingWorld;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Obelisks {

    public Obelisks() {
        DreamingWorld.getInstance().getBlockManager().registerBlock(new ObsidianObeliskTop());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new CobblestoneObeliskTop());
        DreamingWorld.getInstance().getBlockManager().registerBlock(new DesertObeliskTop());

        DreamingWorld.getInstance().getStructureManager().registerStructure("gem_obelisk", new GemObelisk());
    }

    private static class DesertObeliskTop extends ObeliskTop {

        public DesertObeliskTop() {
            this.airMC = 6;
            this.earthMC = 4;
            this.fireMC = 10;
            this.waterMC = 1;

            id = "sandstone_obelisk_top";

            item = new ItemStack(Material.SANDSTONE);
            item.setDurability((short) 1);

            DreamingWorld.getInstance().getItemManager().registerItem(id, item);
        }
    }

    private static class ObsidianObeliskTop extends ObeliskTop {

        public ObsidianObeliskTop() {
            this.airMC = 5;
            this.earthMC = 7;
            this.fireMC = 3;
            this.waterMC = 7;

            id = "obsidian_obelisk_top";
            item = new ItemStack(Material.OBSIDIAN);

            DreamingWorld.getInstance().getItemManager().registerItem(id, item);
        }
    }

    private static class CobblestoneObeliskTop extends ObeliskTop {

        public CobblestoneObeliskTop() {
            this.airMC = 7;
            this.earthMC = 10;
            this.fireMC = 1;
            this.waterMC = 2;

            id = "cobblestone_obelisk_top";
            item = new ItemStack(Material.MOSSY_COBBLESTONE);

            DreamingWorld.getInstance().getItemManager().registerItem(id, item);
        }
    }
}
