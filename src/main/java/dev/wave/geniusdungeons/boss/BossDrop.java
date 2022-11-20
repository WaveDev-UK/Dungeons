package dev.wave.geniusdungeons.boss;

import org.bukkit.configuration.file.YamlConfiguration;
import tech.candy_dev.candycommons.configuration.Serializable;
import tech.candy_dev.candycommons.file.yaml.YamlFile;
import tech.candy_dev.candycommons.item.Item;

import java.util.Map;

public class BossDrop extends Serializable {

    private final Item item;
    private final double weight;

    public BossDrop(Item item, double weight) {
        this.item = item;
        this.weight = weight;
    }

    public Item getItem() {
        return item;
    }

    public double getWeight() {
        return weight;
    }


    @Override
    public Map<String, Object> serialize() {

        Map<String, Object> map = item.serialize();
        map.put("weight", weight);

        return map;
    }

    public static BossDrop deserialize(YamlFile yamlFile, String path) {
        YamlConfiguration c = yamlFile.getConfig();
        Item item = Item.deserialize(yamlFile, path);
        double weight = c.getDouble(path + ".weight");
        return new BossDrop(item, weight);
    }

}
