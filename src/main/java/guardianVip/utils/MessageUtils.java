package guardianVip.utils;

import guardianVip.GuardianVips;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class MessageUtils {

    private GuardianVips plugin;

    public MessageUtils(GuardianVips plugin) {
        this.plugin = plugin;
    }

    public String getMessage(String messageType) {
        return plugin.getMessageConfig().getConfigFile().getString(messageType);
    }

    public String replacePlayerName(String message, String playerName) {
        return message.replace("%player%", playerName);
    }

    public void sendTitle(Player player, String message){

        IChatBaseComponent text = IChatBaseComponent.ChatSerializer.a("{'text': '" + message + "'}");

        PacketPlayOutTitle titleToSend = new PacketPlayOutTitle(
                PacketPlayOutTitle.EnumTitleAction.TITLE, text, 40, 20, 20 );

        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        connection.sendPacket(titleToSend);
    }

}
