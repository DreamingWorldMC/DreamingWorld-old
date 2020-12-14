package net.dreamingworld.gameplay.manacraft;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.gameplay.manacraft.researches.activatedmanapickaxe.ActivatedManaPickaxeResearch;
import net.dreamingworld.gameplay.manacraft.researches.advancedtools.AdvancedTools;
import net.dreamingworld.gameplay.manacraft.researches.altarchecker.AltarCheckerResearch;
import net.dreamingworld.gameplay.manacraft.researches.astrum.*;
import net.dreamingworld.gameplay.manacraft.mobs.AstralCreature;
import net.dreamingworld.gameplay.manacraft.researches.advancedmaterials.AdvancedMaterials;
import net.dreamingworld.gameplay.manacraft.researches.astrumbow.AstrumBowResearch;
import net.dreamingworld.gameplay.manacraft.researches.explorersboots.ExplorersBootsResearch;
import net.dreamingworld.gameplay.manacraft.researches.geminfusion.GemInfusionResearch;
import net.dreamingworld.gameplay.manacraft.researches.hotpeddle.HotPeddleResearch;
import net.dreamingworld.gameplay.manacraft.researches.irontree.*;
import net.dreamingworld.gameplay.manacraft.researches.manaarmour.ManaArmourResearch;
import net.dreamingworld.gameplay.manacraft.researches.managem.ManaGem;
import net.dreamingworld.gameplay.manacraft.researches.manainfusion.ManaInfusion;
import net.dreamingworld.gameplay.manacraft.researches.manamanipulation.ManaManipulation;
import net.dreamingworld.gameplay.manacraft.researches.manapickaxe.ManaPickaxeResearch;
import net.dreamingworld.gameplay.manacraft.researches.personalmanacapacitors.PersonalManaCapacitors;
import net.dreamingworld.gameplay.manacraft.researches.plantsandcreatures.PlantsAndCreatures;
import net.dreamingworld.gameplay.manacraft.researches.steamtomana.SteamToMana;
import net.dreamingworld.gameplay.manacraft.researches.vapour.Vapour;

public class Manacraft {

    public static void initialize() {
        long begin = System.currentTimeMillis();

        // Researches
        DreamingWorld.getInstance().getResearchManager().addResearch(new AdvancedMaterials());
        DreamingWorld.getInstance().getResearchManager().addResearch(new ManaManipulation());
        DreamingWorld.getInstance().getResearchManager().addResearch(new AdvancedTools());
        DreamingWorld.getInstance().getResearchManager().addResearch(new ManaInfusion());
        DreamingWorld.getInstance().getResearchManager().addResearch(new PlantsAndCreatures());
        DreamingWorld.getInstance().getResearchManager().addResearch(new IronTreeResearch());
        DreamingWorld.getInstance().getResearchManager().addResearch(new Vapour());
        DreamingWorld.getInstance().getResearchManager().addResearch(new HotPeddleResearch());
        DreamingWorld.getInstance().getResearchManager().addResearch(new Astrum());
        DreamingWorld.getInstance().getResearchManager().addResearch(new AstrumBowResearch());
        DreamingWorld.getInstance().getResearchManager().addResearch(new SteamToMana());
        DreamingWorld.getInstance().getResearchManager().addResearch(new ExplorersBootsResearch());
        DreamingWorld.getInstance().getResearchManager().addResearch(new ManaGem());
        DreamingWorld.getInstance().getResearchManager().addResearch(new GemInfusionResearch());
        DreamingWorld.getInstance().getResearchManager().addResearch(new AltarCheckerResearch());
        DreamingWorld.getInstance().getResearchManager().addResearch(new PersonalManaCapacitors());
        DreamingWorld.getInstance().getResearchManager().addResearch(new ManaPickaxeResearch());
        DreamingWorld.getInstance().getResearchManager().addResearch(new ActivatedManaPickaxeResearch());
        DreamingWorld.getInstance().getResearchManager().addResearch(new ManaArmourResearch());
        
        // Mobs
        DreamingWorld.getInstance().getEntityManager().addEntity("astral_creature", new AstralCreature());

        long time = System.currentTimeMillis() - begin;

        DreamingWorld.getInstance().getLogger().info("Manacraft initialized [" + time + " ms]");
    }
}
