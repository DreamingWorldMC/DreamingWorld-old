package net.dreamingworld.core.ranks;

import net.dreamingworld.DreamingWorld;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.ServerOperator;

import java.util.ArrayList;
import java.util.List;

public class DWPermissibleBase extends PermissibleBase {

    private final List<String> rankPerms;
    private final List<String> defPerms;

    public DWPermissibleBase(ServerOperator op) {
        super(op);

        String rank = DreamingWorld.getInstance().getRankManager().getPlayerRank(((Player) op).getUniqueId());
        rankPerms = DreamingWorld.getInstance().getRankManager().getRankPermissions(rank);

        defPerms = new ArrayList<>();

        for (Permission p : Bukkit.getPluginManager().getDefaultPermissions(false)) {
            defPerms.add(p.getName());
        }
    }

    @Override
    public boolean hasPermission(String inName) {
        if (rankPerms == null) {
            return false;
        }

        if (rankPerms.contains("*")) {
            return true;
        }

        if (rankPerms.contains(inName) || defPerms.contains(inName)) {
            return true;
        }

        String base = inName.split("\\.")[0];

        if (rankPerms.contains(base + ".*")) {
            return true;
        }

        return false;
    }

    @Override
    public boolean hasPermission(Permission perm) {
        return hasPermission(perm.getName());
    }
}
