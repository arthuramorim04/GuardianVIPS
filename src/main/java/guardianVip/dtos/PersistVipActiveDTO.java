package guardianVip.dtos;

import guardianVip.dtos.interfaces.ConvertDTO;
import guardianVip.entity.Vip;
import guardianVip.entity.VipActive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PersistVipActiveDTO implements ConvertDTO<VipActive, PersistVipActiveDTO> {
    private UUID userUUID;
    private String commandGroup;
    private LocalDateTime activationDate;
    private LocalDateTime expiredAt;
    private String vipName;
    private List<String> commandsRemovelVip;

    @Override
    public VipActive convert() {
        Vip vip = new Vip(vipName, commandGroup, new ArrayList<>(), commandsRemovelVip, "");
        return new VipActive(vip, activationDate, expiredAt);
    }

    @Override
    public PersistVipActiveDTO convert(VipActive vipActive) {
        return null;
    }
}
