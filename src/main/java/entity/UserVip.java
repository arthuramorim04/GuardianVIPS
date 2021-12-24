package entity;

import java.util.List;
import java.util.UUID;

public class UserVip {

    private String name;
    private UUID uuid;
    private List<VipActive> vipsActivated;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public List<VipActive> getVipsActivated() {
        return vipsActivated;
    }

    public void setVipsActivated(List<VipActive> vipsActivated) {
        this.vipsActivated = vipsActivated;
    }
}
