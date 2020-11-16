package net.dreamingworld.core.research;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.TagWizard;
import net.dreamingworld.core.Util;
import net.dreamingworld.gameplay.manacraft.blocks.SteamTurbine;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResearchManager {

    private Map<String, Research> researches;

    private Map<String, ItemStack> researchFinalBooks;
    private Map<String, ItemStack> researchBooks;

    // First one is which one and second one is from what
    private Map<String, String> researchedFrom;

    public ResearchManager() {
        researchedFrom = new HashMap<>();
        researches = new HashMap<>();
    }

    public void initializeResearchItems() {
        DreamingWorld.getInstance().getBlockManager().registerBlock(new ResearchBlock());

        for (Map.Entry<String, Research> x : researches.entrySet()) {
            ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta meta = x.getValue().book;
            meta.setDisplayName(Util.formatString("&fResearch paper"));

            List<String> lore = new ArrayList<>();

            lore.add(x.getValue().name);

            meta.setLore(lore);
            item.setItemMeta(meta);

            TagWizard.addItemTag(item, "id", "final_research");
            TagWizard.addItemTag(item, "research", x.getKey());

            researchFinalBooks.put(x.getKey(), item);
        }

        for (Map.Entry<String, Research> x : researches.entrySet()) {
            ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta meta = (BookMeta)item.getItemMeta();
            meta.setDisplayName(Util.formatString("&fUnfinished research paper"));

            List<String> lore = new ArrayList<>();

            meta.setPage(0, "∷ᒷᓭ∴ᔑ∷ᓵ⍑ \n \n Unfinished Research");

            lore.add(x.getValue().name);

            meta.setLore(lore);
            item.setItemMeta(meta);

            TagWizard.addItemTag(item, "id", "research");
            TagWizard.addItemTag(item, "research", x.getKey());

            researchBooks.put(x.getKey(), item);

        }
    }

    public void addResearch(Research research) {
        researches.put(research.id, research);
    }

    public ItemStack getFinalResearchItem(String id) {
        return researchFinalBooks.get(id);
    }

    public ItemStack getResearchItem(String id) {
        return researchBooks.get(id);
    }

    public Research getResearch(String id) {
        return researches.get(id);
    }

    /**
     * @return researches which have this research as there parent
     */
    public List<String> getResearchChildren(String id) {
        List<String> researches = new ArrayList<>();

        for (Map.Entry<String, String> x : researchedFrom.entrySet()) {
            if (x.getValue().equals(id)) {
                researches.add(x.getKey());
            }
        }

        return researches;
    }

    /**
     * @return true if player has specific research item in there inventory
     */
    public boolean playerHasResearch(Player p, String research) {
        for (ItemStack x : p.getInventory().getContents()) {
            if (x.getType() == Material.WRITTEN_BOOK && TagWizard.getItemTag(x, "final_research") != null) {
                if (research.equals(TagWizard.getItemTag(x, "research"))) {
                    return true;
                }
            }
        }
        return false;
    }
}
