package tech.candy_dev.geniusdungeons.boss;

import org.bukkit.configuration.file.YamlConfiguration;
import tech.candy_dev.candycommons.configuration.Serializable;
import tech.candy_dev.candycommons.entity.CandyEntity;
import tech.candy_dev.candycommons.file.yaml.YamlFile;
import tech.candy_dev.candycommons.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Boss extends Serializable {

    private final String id;
    private double xp;
    private List<Item> drops;
    private List<BossEntity> bossEntities;

    public Boss(String id) {
        this.id = id;
    }

    public Boss setXp(double xp) {
        this.xp = xp;
        return this;
    }

    public Boss setBossEntities(List<BossEntity> candyEntities) {
        this.bossEntities = candyEntities;
        return this;
    }

    public Boss setDrops(List<Item> drops) {
        this.drops = drops;
        return this;
    }

    public String getId() {
        return id;
    }

    public double getXp() {
        return xp;
    }


    public List<BossEntity> getBossEntities() {
        return bossEntities;
    }

    public List<Item> getDrops() {
        return drops;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("xp", xp);

        for (int i = 0; i < bossEntities.size(); i++) {
            BossEntity candyEntity = bossEntities.get(i);
            Map<String, Object> entity = candyEntity.serialize();
            for (String key : entity.keySet()) {
                map.put("entities." + i + "." + key, entity.get(key));
            }
        }

        if (drops != null) {
            for (int i = 0; i < drops.size(); i++) {
                Item item = drops.get(i);
                Map<String, Object> itemSerialized = item.serialize();
                for (String key : itemSerialized.keySet()) {
                    map.put("drops." + i + "." + key, itemSerialized.get(key));
                }
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
        List<Item> items = new ArrayList<>();
        if (c.contains(path + ".drops")) {
            for (String key : c.getConfigurationSection(path + ".drops").getKeys(false)) {
                String itemPath = path + ".drops." + key;
                Item item = Item.deserialize(yamlFile, itemPath);
                items.add(item);
            }
        }
        boss.setDrops(boss.getDrops());
        return boss;
    }

}
