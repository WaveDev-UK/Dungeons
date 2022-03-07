package tech.candy_dev.geniusdungeons.boss;

import tech.candy_dev.geniusdungeons.configuration.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BossManager {

    private Map<String, Boss> bossMap;

    public BossManager() {
        this.bossMap = new HashMap<>();
        loadBosses();
    }

    private void loadBosses() {
        List<Boss> bossList = Config.BOSSES.getBossConfig().getBosses();
        for (Boss boss : bossList) {
            bossMap.put(boss.getId().toLowerCase(), boss);
        }
    }

    public Boss getBoss(String id){
        return bossMap.get(id.toLowerCase());
    }
}
