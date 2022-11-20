package dev.wave.geniusdungeons.boss;

import dev.wave.geniusdungeons.GeniusDungeons;
import dev.wave.geniusdungeons.configuration.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import java.util.*;

public class BossManager {

    private final Map<String, Boss> bossMap;

    private final Map<Boss, List<Entity>> activeBosses;

    public BossManager() {
        this.bossMap = new HashMap<>();
        this.activeBosses = new HashMap<>();
        loadBosses();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(GeniusDungeons.getInstance(), this::runBossSpawn, Config.SPAWN_INTERVAL.getInt(), Config.SPAWN_INTERVAL.getInt());
    }

    private void loadBosses() {
        List<Boss> bossList = Config.BOSSES.getBossConfig().getBosses();
        for (Boss boss : bossList) {
            bossMap.put(boss.getId().toLowerCase(), boss);
        }
    }

    public Boss getBoss(String id) {
        return bossMap.get(id.toLowerCase());
    }

    public List<Entity> getActiveBosses(Boss boss) {
        return activeBosses.get(boss);
    }

    public void addActiveBoss(Boss boss, Entity entity) {
        List<Entity> entities = getActiveBosses(boss);
        if (entities == null) {
            entities = new ArrayList<>();
        }
        entities.add(entity);
        activeBosses.put(boss, entities);
    }

    public void removeActiveBoss(Boss boss, Entity entity) {
        if (getActiveBosses(boss) == null) {
            return;
        }

        List<Entity> activeBoss = getActiveBosses(boss);

        activeBoss.remove(entity);
        activeBosses.put(boss, activeBoss);
    }

    public void clearActiveBoss(Boss boss) {
        if (getActiveBosses(boss) == null) {
            return;
        }

        for (Entity entity : getActiveBosses(boss)) {
            entity.remove();
        }

        activeBosses.put(boss, new ArrayList<>());
    }

    public Collection<Boss> getBosses() {
        return bossMap.values();
    }

    public Boss getBoss(Entity entity) {
        for (Boss boss : getBosses()) {
            List<Entity> active = getActiveBosses(boss);
            if (active == null || active.isEmpty()) {
                continue;
            }

            if (active.contains(entity)) {
                return boss;
            }
        }
        return null;
    }

    public void runBossSpawn() {
        for (Boss boss : getBosses()) {
            if (getActiveBosses(boss) != null && !getActiveBosses(boss).isEmpty()) {
                continue;
            }

            if (boss.getSpawnLocations().isEmpty()) {
                continue;
            }

            for (BossEntity bossEntity : boss.getBossEntities()) {
                if (bossEntity.getAmount() < 1) {
                    continue;
                }
                for (int i = 1; i <= bossEntity.getAmount(); i++) {
                    bossEntity.spawn(boss.getNextLocation(), boss);
                }
            }
        }
    }

    public void runForceSpawn() {
        for (Boss boss : getBosses()) {
            clearActiveBoss(boss);
        }
        runBossSpawn();
    }

}
