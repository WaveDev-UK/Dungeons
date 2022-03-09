package tech.candy_dev.geniusdungeons;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import tech.candy_dev.candycommons.CandyCommons;
import tech.candy_dev.candycommons.configuration.Configuration;
import tech.candy_dev.candycommons.file.yaml.YamlFile;
import tech.candy_dev.geniusdungeons.api.DungeonsAPI;
import tech.candy_dev.geniusdungeons.api.PapiExpansion;
import tech.candy_dev.geniusdungeons.boss.Boss;
import tech.candy_dev.geniusdungeons.boss.BossDrop;
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

    private Economy econ;

    @Override
    public void onEnable() {
        instance = this;
        CandyCommons.getInstance().registerSerialization(Boss.class);
        CandyCommons.getInstance().registerSerialization(BossEntity.class);
        CandyCommons.getInstance().registerSerialization(BossConfig.class);
        CandyCommons.getInstance().registerSerialization(BossDrop.class);
        if(Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")){
            new PapiExpansion().register();
            CandyCommons.getInstance().sendConsoleMessage("PlaceholderAPI hook Found!");
        }else{
            CandyCommons.getInstance().sendConsoleMessage("Could not find PlaceholderAPI!");
        }
        if (!setupEconomy()) {
            CandyCommons.getInstance().sendConsoleMessage("&4&lERROR: &cCould not load GeniusDungeons, Vault not found!");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            return;
        } else {
            CandyCommons.getInstance().sendConsoleMessage("Vault hook loaded for GeniusDungeons!");
        }
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

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public Economy getEcon() {
        return econ;
    }
}
