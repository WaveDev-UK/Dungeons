package tech.candy_dev.geniusdungeons;

import org.bukkit.plugin.java.JavaPlugin;
import tech.candy_dev.candycommons.CandyCommons;
import tech.candy_dev.candycommons.configuration.Configuration;
import tech.candy_dev.candycommons.configuration.Message;
import tech.candy_dev.candycommons.configuration.Serialization;
import tech.candy_dev.candycommons.file.yaml.YamlFile;
import tech.candy_dev.geniusdungeons.api.DungeonsAPI;
import tech.candy_dev.geniusdungeons.boss.Boss;
import tech.candy_dev.geniusdungeons.boss.BossEntity;
import tech.candy_dev.geniusdungeons.boss.BossManager;
import tech.candy_dev.geniusdungeons.command.DungeonsCommand;
import tech.candy_dev.geniusdungeons.configuration.BossConfig;
import tech.candy_dev.geniusdungeons.configuration.Config;
import tech.candy_dev.geniusdungeons.configuration.Messages;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class GeniusDungeons extends JavaPlugin {

    private static GeniusDungeons instance;

    private BossManager bossManager;

    private Map<String, YamlFile> configurationFiles;

    @Override
    public void onEnable() {
        instance = this;
        CandyCommons.getInstance().registerSerialization(Boss.class);
        CandyCommons.getInstance().registerSerialization(BossEntity.class);
        CandyCommons.getInstance().registerSerialization(BossConfig.class);
        new DungeonsAPI();
        reload();
        new DungeonsCommand();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static GeniusDungeons getInstance() {
        return instance;
    }

    public void reload() {
        this.configurationFiles = new HashMap<>();
        for (String file : Arrays.asList("config", "messages")) {
            configurationFiles.put(file, new YamlFile(file + ".yml", this.getDataFolder().getAbsolutePath(), null, this));
        }
        Configuration.loadConfig(configurationFiles.get("config"), Config.values());
        Configuration.loadConfig(configurationFiles.get("messages"), Messages.values());
        this.bossManager = new BossManager();
    }

    public BossManager getBossManager() {
        return bossManager;
    }

    public YamlFile getFile(String file) {
        return configurationFiles.get(file);
    }

}
