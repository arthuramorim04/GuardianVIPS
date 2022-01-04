package guardianVip.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class UserVip {

    private String name;
    private UUID uuid;
    private List<VipActive> vipsActivated = new ArrayList<>();

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
