package guardianVip.entity;

public class KeyVip {

    private Long key;
    private String keyString;
    private String vipName;
    private Long days;
    private Long hours;
    private Long minutes;
    private Long remainingUse;
    private boolean isEnable;
    private String player;

    public KeyVip() {
    }

    public KeyVip(Long key, String keyString, String vipName, Long days, Long hours, Long minutes, Long remainingUse, boolean isEnable) {
        this.key = key;
        this.keyString = keyString;
        this.vipName = vipName;
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.remainingUse = remainingUse;
        this.isEnable = isEnable;
    }

    public KeyVip(Long key, String keyString, String vipName, Long days, Long hours, Long minutes, Long remainingUse, boolean isEnable, String player) {
        this.key = key;
        this.keyString = keyString;
        this.vipName = vipName;
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.remainingUse = remainingUse;
        this.isEnable = isEnable;
        this.player = player;
    }


    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getKeyString() {
        return keyString;
    }

    public void setKeyString(String keyString) {
        this.keyString = keyString;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
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

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public Long getRemainingUse() {
        return remainingUse;
    }

    public void setRemainingUse(Long remainingUse) {
        this.remainingUse = remainingUse;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
