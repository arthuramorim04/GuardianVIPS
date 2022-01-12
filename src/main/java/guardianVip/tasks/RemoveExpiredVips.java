package guardianVip.tasks;

import guardianVip.GuardianVips;
import guardianVip.entity.UserVip;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;

public class RemoveExpiredVips extends BukkitRunnable {

    private GuardianVips plugin;

    public RemoveExpiredVips(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        removeExpiredTask();
    }

    private void removeExpiredTask() {
        System.out.println("Verificando vips expirando.");
        plugin.getVipActiveService().removeExpiredVipsIfN();
        // verificar quem tem menos de 24h e esta online
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
    }

    @Override
    public synchronized BukkitTask runTask(Plugin plugin) throws IllegalArgumentException, IllegalStateException {
        return super.runTask(plugin);
    }

    @Override
    public synchronized BukkitTask runTaskAsynchronously(Plugin plugin) throws IllegalArgumentException, IllegalStateException {
        return super.runTaskAsynchronously(plugin);
    }

    @Override
    public synchronized BukkitTask runTaskLater(Plugin plugin, long delay) throws IllegalArgumentException, IllegalStateException {
        return super.runTaskLater(plugin, delay);
    }

    @Override
    public synchronized BukkitTask runTaskLaterAsynchronously(Plugin plugin, long delay) throws IllegalArgumentException, IllegalStateException {
        return super.runTaskLaterAsynchronously(plugin, delay);
    }

    @Override
    public synchronized BukkitTask runTaskTimer(Plugin plugin, long delay, long period) throws IllegalArgumentException, IllegalStateException {
        return super.runTaskTimer(plugin, delay, period);
    }

    @Override
    public synchronized BukkitTask runTaskTimerAsynchronously(Plugin plugin, long delay, long period) throws IllegalArgumentException, IllegalStateException {
        return super.runTaskTimerAsynchronously(plugin, delay, period);
    }

    @Override
    public synchronized int getTaskId() throws IllegalStateException {
        return super.getTaskId();
    }
}
