package net.dreamingworld.core;

import net.dreamingworld.DreamingWorld;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class AutoBroadcast {

    private final File file;
    private final YamlConfiguration config;

    private int current = 0;
    private final List<String> messages;

    public AutoBroadcast() {
        file = new File(DreamingWorld.dataDirectory, "../broadcast.yml");
        config = YamlConfiguration.loadConfiguration(file);

        messages = config.getStringList("messages");

        if (messages == null || messages.isEmpty()) {
            return;
        }

        Bukkit.getScheduler().runTaskTimerAsynchronously(DreamingWorld.getInstance(), () -> {
            if (current < messages.size()) {
                Bukkit.broadcastMessage(Util.formatString(messages.get(current)));
            }

            current++;
            if (current >= messages.size()) {
                current = 0;
            }
        }, 0, config.getInt("interval"));
    }
}
