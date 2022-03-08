package tech.candy_dev.geniusdungeons.boss;

import de.tr7zw.changeme.nbtapi.NBTEntity;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import tech.candy_dev.candycommons.configuration.Serializable;
import tech.candy_dev.candycommons.entity.CandyEntity;
import tech.candy_dev.candycommons.file.yaml.YamlFile;
import tech.candy_dev.geniusdungeons.GeniusDungeons;

import java.lang.reflect.Field;
import java.util.Map;

public class BossEntity extends Serializable {

    private int amount;
    private CandyEntity candyEntity;

    public BossEntity(CandyEntity candyEntity, int amount) {
        this.candyEntity = candyEntity;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public CandyEntity getCandyEntity() {
        return candyEntity;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = candyEntity.serialize();
        map.put("amount", amount);
        return map;
    }

    public static BossEntity deserialize(YamlFile yamlFile, String path) {

        CandyEntity candyEntity = CandyEntity.deserialize(yamlFile, path);

        int amount = yamlFile.getConfig().getInt(path + ".amount");

        return new BossEntity(candyEntity, amount);
    }

    public void spawn(Location location, Boss boss) {
        Entity entity = candyEntity.spawn(location);
        GeniusDungeons.getInstance().getBossManager().addActiveBoss(boss, entity);
    }

}
