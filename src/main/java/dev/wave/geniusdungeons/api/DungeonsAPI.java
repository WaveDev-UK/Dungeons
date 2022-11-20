package dev.wave.geniusdungeons.api;

import dev.wave.geniusdungeons.configuration.Config;
import dev.wave.geniusdungeons.user.DungeonUserData;
import org.bukkit.entity.Player;
import tech.candy_dev.candycommons.user.User;
import tech.candy_dev.candycommons.util.Util;

public class DungeonsAPI {

    private static DungeonsAPI instance;

    public DungeonsAPI() {
        instance = this;
    }

    public double getExp(User user) {
        DungeonUserData dungeonUserData = user.getUserData(DungeonUserData.class);
        return dungeonUserData.getExp();
    }

    public double getExp(Player player) {
        User user = Util.getUser(player.getUniqueId());
        return getExp(user);
    }

    public void setExp(User user, double exp) {
        DungeonUserData dungeonUserData = user.getUserData(DungeonUserData.class);
        dungeonUserData.setExp(exp);
    }

    public void setExp(Player player, double exp) {
        User user = Util.getUser(player.getUniqueId());
        setExp(user, exp);
    }

    public static DungeonsAPI getInstance() {
        return instance;
    }

    public double getLevel(User user) {
        DungeonUserData dungeonUserData = user.getUserData(DungeonUserData.class);
        double xp = dungeonUserData.getExp();
        double remainder = xp % Config.XP_PER_LEVEL.getDouble();
        xp = xp - remainder;
        return xp / Config.XP_PER_LEVEL.getDouble();
    }

    public void setLevel(User user, double level){
        double xp = level*Config.XP_PER_LEVEL.getDouble();
        DungeonUserData dungeonUserData = user.getUserData(DungeonUserData.class);
        dungeonUserData.setExp(xp);
    }

}
