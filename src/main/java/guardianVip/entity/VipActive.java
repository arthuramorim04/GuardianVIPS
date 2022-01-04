package guardianVip.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@NoArgsConstructor
public class VipActive {

    private Long activeId;
    private Vip vip;
    private LocalDateTime activationDate;
    private LocalDateTime expiredAt;

    public VipActive(Vip vip, LocalDateTime activationDate, LocalDateTime expiredAt) {
        this.vip = vip;
        this.activationDate = activationDate;
        this.expiredAt = expiredAt;
    }

    public VipActive(Vip vip) {
        this.vip = vip;
        this.activeId = System.currentTimeMillis();
    }

    public Vip getVip() {
        return vip;
    }

    public void setVip(Vip vip) {
        this.vip = vip;
    }

    public LocalDateTime getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(LocalDateTime activationDate) {
        this.activationDate = activationDate;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public Long getActiveId() {
        return activeId;
    }

    public void setActiveId(Long activeId) {
        this.activeId = activeId;
    }

    public LocalDateTime addDays(Long days) {
        this.expiredAt = this.getExpiredAt().plusDays(days);
        return this.getExpiredAt();
    }

    public boolean activeVip(Long qtdDays) {
        if (this.vip != null) {
            LocalDateTime activationVip = LocalDateTime.now();
            this.activationDate = activationVip;
            this.expiredAt = activationVip.plusDays(qtdDays);
            return true;
        }
        return false;
    }

    public Long getDays() {
        return ChronoUnit.DAYS.between(activationDate, expiredAt);
    }
}
