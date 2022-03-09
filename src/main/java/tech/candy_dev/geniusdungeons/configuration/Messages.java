package tech.candy_dev.geniusdungeons.configuration;

import tech.candy_dev.candycommons.configuration.Configuration;
import tech.candy_dev.candycommons.configuration.Message;

import java.util.Arrays;
import java.util.List;

public enum Messages implements Configuration, Message {

    PREFIX("prefix", "&bDungeons &8» &3"),
    HELP("help", Arrays.asList(
        "{prefix}List of Commands",
        "&8» &b{commands}")),
    RELOAD_STARTED("reload.started", "{prefix}Beginning to reload GeniusDungeons..."),
    RELOAD_COMPLETED("reload.completed", "{prefix}Reload completed! Time taken: {time}ms."),
    BOSS_NOT_FOUND("boss.not-found", "{prefix}Boss &9{boss} &3not found!"),
    BOSS_SPAWN_ADDED("boss.spawn-added", Arrays.asList(
            "{prefix}Spawn added to Boss: {boss}!",
            "&bLocation: {location}"
    ));

    private final String path;
    private Object value;

    Messages(String path, Object value) {
        this.path = path;
        this.value = value;
    }


    public String getPath() {
        return path;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String getString() {
        return (String) value;
    }

    @Override
    public List<String> getStringList() {
        return (List<String>) value;
    }

    @Override
    public String getPrefix() {
        return PREFIX.getString();
    }
}
