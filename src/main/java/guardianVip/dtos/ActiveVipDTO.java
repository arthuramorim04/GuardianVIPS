package guardianVip.dtos;

import guardianVip.entity.Vip;
import guardianVip.utils.ActiveVipType;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ActiveVipDTO {

    private Vip vip;
    private Player player;
    private String playerName;
    private UUID uuid;
    private Long days;
    private Long hours;
    private Long minutes;
    private ActiveVipType activeVipType;
    private Boolean isEterna = false;

    public ActiveVipDTO(Vip vip, Player player, Long days, Long hours, Long minutes, ActiveVipType activeVipType) {
        this.vip = vip;
        this.player = player;
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.activeVipType = activeVipType;
    }

    public ActiveVipDTO(Vip vip, String playerName, UUID uuid, Long days, Long hours, Long minutes, ActiveVipType activeVipType) {
        this.vip = vip;
        this.playerName = playerName;
        this.uuid = uuid;
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.activeVipType = activeVipType;
    }

    public Boolean getEterna() {
        return isEterna;
    }

    public void setEterna(Boolean eterna) {
        isEterna = eterna;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Vip getVip() {
        return vip;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setVip(Vip vip) {
        this.vip = vip;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Long getDays() {
        return days;
    }

    public void setDays(Long days) {
        this.days = days;
    }

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    public Long getMinutes() {
        return minutes;
    }

    public void setMinutes(Long minutes) {
        this.minutes = minutes;
    }

    public ActiveVipType getActiveVipType() {
        return activeVipType;
    }

    public void setActiveVipType(ActiveVipType activeVipType) {
        this.activeVipType = activeVipType;
    }
}
