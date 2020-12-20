package net.dreamingworld.core.ranks;

import net.dreamingworld.DreamingWorld;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.ServerOperator;

import java.util.List;

public class DWPermissibleBase extends PermissibleBase {

    private Player player;

    public DWPermissibleBase(ServerOperator op) {
        super(op);
        player = (Player) op;
    }

    @Override
    public boolean hasPermission(String inName) {
        String rank = DreamingWorld.getInstance().getRankManager().getPlayerRank(player.getUniqueId());
        List<String> perms = DreamingWorld.getInstance().getRankManager().getRankPermissions(rank);

        if (perms == null) {
            return false;
        }

        if (perms.contains("*")) {
            return true;
        }

        if (perms.contains(inName)) {
            return true;
        }

        String base = inName.split("\\.")[0];

        if (perms.contains(base + ".*")) {
            return true;
        }

        return false;
    }

    @Override
    public boolean hasPermission(Permission perm) {
        return hasPermission(perm.getName());
    }
}
