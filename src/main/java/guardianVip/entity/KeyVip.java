package guardianVip.entity;

public class KeyVip {

    private Long key;
    private String keyString;
    private String vipName;
    private Long days;
    private Long hours;
    private Long minutes;
    private Long allowedUsage;

    public KeyVip() {
    }

    public KeyVip(Long key, String keyString, String vipName, Long days, Long allowedUsage) {
        this.key = key;
        this.keyString = keyString;
        this.vipName = vipName;
        this.days = days;
        this.allowedUsage = allowedUsage;
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

    public Long getAllowedUsage() {
        return allowedUsage;
    }

    public void setAllowedUsage(Long allowedUsage) {
        this.allowedUsage = allowedUsage;
    }

    public String serializeKey() {
        return vipName + ";" + key + ";" + keyString + ";" + days + ";"+ hours + ";"+ minutes + ";" + allowedUsage;
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

    public KeyVip deserializeKey(String keySerialized) {
        String[] split = keySerialized.split(";");

        this.vipName = split[0];
        this.key = Long.valueOf(split[1]);
        this.keyString = split[2];
        this.days = Long.valueOf(split[3]);
        this.hours = Long.valueOf(split[4]);
        this.minutes = Long.valueOf(split[5]);
        this.allowedUsage = Long.valueOf(split[6]);
        return this;
    }
}
