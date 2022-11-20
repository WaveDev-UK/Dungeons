package dev.wave.geniusdungeons.boss;

import dev.wave.geniusdungeons.configuration.Config;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import tech.candy_dev.candycommons.configuration.Serializable;
import tech.candy_dev.candycommons.file.yaml.YamlFile;
import tech.candy_dev.candycommons.item.Item;
import tech.candy_dev.candycommons.util.WeightedChooser;

import java.util.*;

public class Boss extends Serializable {

    private final String id;
    private double xp;
    private double money;
    private List<BossDrop> drops;
    private List<BossEntity> bossEntities;
    private final WeightedChooser<BossDrop> chooser;

    private final Queue<Location> spawnLocations;

    private final List<Location> spawnLocationList;

    public Boss(String id) {
        this.id = id;
        this.spawnLocations = new PriorityQueue<>();
        this.spawnLocationList = new ArrayList<>();
        this.chooser = new WeightedChooser<>();
    }

    public void addSpawn(Location location) {
        spawnLocationList.add(location);
        spawnLocations.add(location);
        Config.BOSSES.getBossConfig().update();
    }

    public void removeSpawn(Location location) {
        spawnLocationList.remove(location);
        spawnLocations.remove(location);
        Config.BOSSES.getBossConfig().update();
    }

    public Boss setXp(double xp) {
        this.xp = xp;
        return this;
    }

    public Boss setBossEntities(List<BossEntity> candyEntities) {
        this.bossEntities = candyEntities;
        return this;
    }

    public Boss setDrops(List<BossDrop> drops) {
        this.drops = drops;
        for (BossDrop bossDrop : drops) {
            chooser.insertElement(bossDrop, bossDrop.getWeight());
        }
        return this;
    }

    public Boss setMoney(double money) {
        this.money = money;
        return this;
    }

    public String getId() {
        return id;
    }

    public double getXp() {
        return xp;
    }

    public Queue<Location> getSpawnLocations() {
        return spawnLocations;
    }

    public Location getNextLocation() {
        Location location = spawnLocations.poll();
        spawnLocations.add(location);
        return location;
    }

    public List<BossEntity> getBossEntities() {
        return bossEntities;
    }

    public List<BossDrop> getDrops() {
        return drops;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("xp", xp);
        map.put("money", money);

        for (int i = 0; i < bossEntities.size(); i++) {
            BossEntity candyEntity = bossEntities.get(i);
            Map<String, Object> entity = candyEntity.serialize();
            for (String key : entity.keySet()) {
                map.put("entities." + i + "." + key, entity.get(key));
            }
        }

        if (drops != null) {
            for (int i = 0; i < drops.size(); i++) {
                BossDrop bossDrop = drops.get(i);
                Map<String, Object> itemSerialized = bossDrop.serialize();
                for (String key : itemSerialized.keySet()) {
                    map.put("drops." + i + "." + key, itemSerialized.get(key));
                }
            }

        }

        if (!spawnLocationList.isEmpty()) {
            int i = 1;
            for (Location location : spawnLocationList) {
                Map<String, Object> locationSerialized = location.serialize();
                for (String key : locationSerialized.keySet()) {
                    map.put("spawns." + i + "." + key, locationSerialized.get(key));
                }
                i++;
            }
        }

        return map;
    }

    public static Boss deserialize(YamlFile yamlFile, String path) {
        YamlConfiguration c = yamlFile.getConfig();
        String id = c.getString(path + ".id");
        Boss boss = new Boss(id);
        boss.setXp(c.getDouble(path + ".xp"));
        List<BossEntity> candyEntities = new ArrayList<>();
        for (String entity : c.getConfigurationSection(path + ".entities").getKeys(false)) {
            BossEntity candyEntity = BossEntity.deserialize(yamlFile, path + ".entities." + entity);
            candyEntities.add(candyEntity);
        }
        boss.setBossEntities(candyEntities);
        List<BossDrop> items = new ArrayList<>();
        if (c.contains(path + ".drops")) {
            for (String key : c.getConfigurationSection(path + ".drops").getKeys(false)) {
                String itemPath = path + ".drops." + key;
                BossDrop bossDrop = BossDrop.deserialize(yamlFile, itemPath);
                items.add(bossDrop);
            }
        }
        if (c.contains(path + ".spawns")) {
            Map<String, Object> keys = new HashMap<>();
            for (String key : c.getConfigurationSection(path + ".spawns").getKeys(false)) {
                keys.put(key, c.get(key));
            }
            boss.spawnLocations.add(Location.deserialize(keys));
            boss.spawnLocationList.add(Location.deserialize(keys));
        }
        if (c.contains(path + ".money")) {
            boss.setMoney(c.getDouble(path + ".money"));
        }
        boss.setDrops(items);
        return boss;
    }


    public double getMoney() {
        return money;
    }

    public List<Item> getRandomDrops() {
        if (Config.DROPS_PER_KILL.getInt() < 1) {
            return null;
        }

        List<Item> items = new ArrayList<>();
        for (int i = 1; i <= Config.DROPS_PER_KILL.getInt(); i++) {
            items.add(chooser.getRandomElement().getItem());
        }

        return items;
    }

    public List<Location> getSpawnLocationList() {
        return spawnLocationList;
    }
}
