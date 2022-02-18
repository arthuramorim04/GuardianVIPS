package guardianVip.tasks;

import guardianVip.GuardianVips;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class NotifyExpiredVips extends BukkitRunnable {

    private GuardianVips plugin;

    public NotifyExpiredVips(GuardianVips plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        removeExpiredTask();
    }

    private void removeExpiredTask() {
        try {
            plugin.getVipActiveService().notifyVipsOnline();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
