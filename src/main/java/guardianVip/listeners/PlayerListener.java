package guardianVip.listeners;

import guardianVip.GuardianVips;
import guardianVip.entity.UserVip;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    private final GuardianVips plugin;

    public PlayerListener(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {

        if (e.getPlayer().hasPlayedBefore()) {
            UserVip userVip = plugin.getUserService().loadUserVip(e.getPlayer());
            plugin.getVipActiveService().removeVipExpired(userVip, e.getPlayer());
        } else {
            plugin.getUserService().create(e.getPlayer());
        }

    }
}
