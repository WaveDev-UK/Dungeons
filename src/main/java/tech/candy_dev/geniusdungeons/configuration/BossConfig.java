package tech.candy_dev.geniusdungeons.configuration;

import org.bukkit.configuration.file.YamlConfiguration;
import tech.candy_dev.candycommons.configuration.Serializable;
import tech.candy_dev.candycommons.file.yaml.YamlFile;
import tech.candy_dev.geniusdungeons.GeniusDungeons;
import tech.candy_dev.geniusdungeons.boss.Boss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BossConfig extends Serializable {

    private final List<Boss> bosses;

    public BossConfig(List<Boss> bosses) {
        this.bosses = bosses;
    }

    public List<Boss> getBosses() {
        return bosses;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        for (Boss boss : bosses) {
            Map<String, Object> bossSerialized = boss.serialize();
            for (String key : bossSerialized.keySet()) {
                map.put(boss.getId() + "." + key, bossSerialized.get(key));
            }
        }
        return map;
    }

    public static BossConfig deserialize(YamlFile yamlFile, String path) {
        YamlConfiguration c = yamlFile.getConfig();
        List<Boss> bosses = new ArrayList<>();
        for (String key : c.getConfigurationSection(path).getKeys(false)) {
            Boss boss = Boss.deserialize(yamlFile, path + "." + key);
            bosses.add(boss);
        }
        return new BossConfig(bosses);
    }

    public void update() {
        bosses.clear();
        bosses.addAll(GeniusDungeons.getInstance().getBossManager().getBosses());
        Config.BOSSES.setValue(this);
        Config.BOSSES.saveValue(GeniusDungeons.getInstance().getFile("config"));
    }



}
