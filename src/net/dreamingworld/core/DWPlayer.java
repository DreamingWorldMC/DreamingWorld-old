package net.dreamingworld.core;

import net.dreamingworld.DreamingWorld;
import net.dreamingworld.core.vanity.VanitySlot;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DWPlayer {

    private final File playerFile;
    private final YamlConfiguration playerConfig;

    private final Player bukkitPlayer;

    private final List<ItemStack> vanityItems;

    public DWPlayer(Player bukkitPlayer) {
        playerFile = new File(DreamingWorld.dataDirectory + "players/", bukkitPlayer.getUniqueId().toString());
        playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        this.bukkitPlayer = bukkitPlayer;

        // Loading vanity
        if (playerConfig.contains("vanity")) {
            vanityItems = (List<ItemStack>) playerConfig.getList("vanity");
        } else {
            vanityItems = new ArrayList<>();
        }
    }


    public ItemStack getVanityItem(VanitySlot slot) {
        int s = slot.getId();

        if (s >= vanityItems.size()) {
            return null;
        }

        return vanityItems.get(s);
    }


    public Player getBukkitPlayer() {
        return bukkitPlayer;
    }


    public void savePlayer() {
        try {
            playerConfig.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
