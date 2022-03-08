package tech.candy_dev.geniusdungeons.boss;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import tech.candy_dev.geniusdungeons.configuration.Config;

import java.util.*;

public class BossManager {

    private final Map<String, Boss> bossMap;

    private final Map<Boss, List<Entity>> activeBosses;

    public BossManager() {
        this.bossMap = new HashMap<>();
        this.activeBosses = new HashMap<>();
        loadBosses();
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

    public Collection<Boss> getBosses() {
        return bossMap.values();
    }


}
