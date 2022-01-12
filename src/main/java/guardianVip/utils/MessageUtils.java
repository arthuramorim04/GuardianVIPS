package guardianVip.utils;

import guardianVip.GuardianVips;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class MessageUtils {

    private GuardianVips plugin;

    public MessageUtils(GuardianVips plugin) {
        this.plugin = plugin;
    }

    public String getMessage(String messageType) {
        return replaceColorSimbol(plugin.getMessageConfig().getConfigFile().getString(messageType));
    }

    public String replacePlayerName(String message, String playerName) {
        return replaceColorSimbol(message).replace("%player%", playerName);
    }

    public String replaceColorSimbol(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void sendTitle(Player player, String message){
        try {
            message = replaceColorSimbol(message);
            String[] messages = message.split("-#-");
            IChatBaseComponent title = IChatBaseComponent.ChatSerializer.a("{'text': '" + messages[0] + "'}");
            IChatBaseComponent subtitle = IChatBaseComponent.ChatSerializer.a("{'text': '" + messages[1] + "'}");

            PacketPlayOutTitle titleToSend = new PacketPlayOutTitle(
                    PacketPlayOutTitle.EnumTitleAction.TITLE, title, 40, 20, 20 );
            PacketPlayOutTitle subtitleToSend = new PacketPlayOutTitle(
                    PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitle, 40, 20, 20 );

            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

            connection.sendPacket(titleToSend);
            connection.sendPacket(subtitleToSend);
        } catch (Exception e) {
            Bukkit.broadcastMessage(replaceColorSimbol(message));
            Bukkit.getConsoleSender().sendMessage(replaceColorSimbol("&cMessage on activationBroadcast must contain '-#-' to split title and caption for to send Title"));
        }
    }

}
