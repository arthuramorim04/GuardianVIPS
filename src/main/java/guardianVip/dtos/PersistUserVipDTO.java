package guardianVip.dtos;

import guardianVip.dtos.interfaces.ConvertDTO;
import guardianVip.entity.UserVip;
import guardianVip.entity.VipActive;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
public class PersistUserVipDTO implements ConvertDTO<UserVip, PersistUserVipDTO> {
    private UUID userUUID;
    private String userName;

    @Override
    public UserVip convert() {
        return new UserVip(userName, userUUID, new ArrayList<>());
    }

    @Override
    public PersistUserVipDTO convert(UserVip userVip) {
        this.userUUID = userVip.getUuid();
        this.userName = userVip.getName();
        return this;
    }
}

