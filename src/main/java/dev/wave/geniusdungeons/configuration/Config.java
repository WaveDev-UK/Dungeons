package dev.wave.geniusdungeons.configuration;

import dev.wave.geniusdungeons.boss.BossDrop;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.potion.PotionEffectType;
import tech.candy_dev.candycommons.configuration.Configuration;
import tech.candy_dev.candycommons.entity.CandyEntity;
import tech.candy_dev.candycommons.item.Item;
import tech.candy_dev.candycommons.util.Pair;
import dev.wave.geniusdungeons.boss.Boss;
import dev.wave.geniusdungeons.boss.BossEntity;

import java.util.Arrays;

@SuppressWarnings("unchecked")
public enum Config implements Configuration {

    DROPS_PER_KILL("drops-per-kill", 1),
    SPAWN_INTERVAL("spawn-interval", 300),
    BOSSES("bosses",
        new BossConfig(
            Arrays.asList(
                new Boss("ZombiePack")
                    .setDrops(Arrays.asList(
                        new BossDrop(
                            new Item()
                                .setMaterial(Material.GOLD_NUGGET)
                                .setName("&eTest Drop")
                                .setEnchantments(new Pair<>(Enchantment.ARROW_INFINITE, 1))
                                .setItemFlags(ItemFlag.HIDE_ENCHANTS), 100
                        )
                    ))
                    .setBossEntities(Arrays.asList(
                        new BossEntity(
                            new CandyEntity(EntityType.ZOMBIE)
                                .setBaby(false)
                                .setHealth(50)
                                .setName("&cZombiePack"), 4, 5),
                        new BossEntity(
                            new CandyEntity(EntityType.ZOMBIE)
                                .setName("&c&lZombie King")
                                .setHealth(100)
                                .setEquipment(
                                    new Pair<>("helmet", new Item()
                                        .setMaterial(Material.LEATHER_HELMET)),
                                    new Pair<>("chestplate", new Item().setMaterial(Material.LEATHER_CHESTPLATE)),
                                    new Pair<>("leggings", new Item().setMaterial(Material.LEATHER_LEGGINGS)),
                                    new Pair<>("boots", new Item().setMaterial(Material.LEATHER_BOOTS)),
                                    new Pair<>("hand", new Item()
                                        .setMaterial(Material.WOOD_SWORD)
                                        .setEnchantments(
                                            new Pair<>(Enchantment.DAMAGE_ALL, 5)
                                        ))
                                ), 4, 10)))
                    .setXp(5),
                new Boss("ZombieOverlord")
                    .setBossEntities(Arrays.asList(
                        new BossEntity(
                            new CandyEntity(EntityType.ZOMBIE)
                                .setName("&b&lZombie King")
                                .setHealth(250)
                                .setEquipment(
                                    new Pair<>("helmet", new Item()
                                        .setMaterial(Material.SKULL)
                                        .setOwner("CandyDev")),
                                    new Pair<>("chestplate", new Item()
                                        .setMaterial(Material.LEATHER_CHESTPLATE)
                                        .setEnchantments(new Pair<>(Enchantment.PROTECTION_ENVIRONMENTAL, 5))),
                                    new Pair<>("leggings", new Item().setMaterial(Material.LEATHER_LEGGINGS)
                                        .setEnchantments(new Pair<>(Enchantment.PROTECTION_ENVIRONMENTAL, 5))),
                                    new Pair<>("boots", new Item().setMaterial(Material.LEATHER_BOOTS)
                                        .setEnchantments(new Pair<>(Enchantment.PROTECTION_ENVIRONMENTAL, 5))),
                                    new Pair<>("hand", new Item()
                                        .setMaterial(Material.WOOD_SWORD)
                                        .setEnchantments(
                                            new Pair<>(Enchantment.DAMAGE_ALL, 5)
                                        ))
                                )
                                .setPotionEffects(
                                    new Pair<>(PotionEffectType.DAMAGE_RESISTANCE, 2),
                                    new Pair<>(PotionEffectType.INCREASE_DAMAGE, 1)
                                ), 4, 20
                        )
                    ))
            )
        )),
    XP_PER_LEVEL("xp-per-level", 100D);

    private final String path;
    private Object value;

    Config(String path, Object value) {
        this.path = path;
        this.value = value;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object o) {
        this.value = o;
    }

    public BossConfig getBossConfig() {
        return (BossConfig) value;
    }

}
