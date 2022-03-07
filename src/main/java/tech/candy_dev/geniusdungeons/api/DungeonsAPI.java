package tech.candy_dev.geniusdungeons.api;

import org.bukkit.entity.Player;
import tech.candy_dev.candycommons.user.User;
import tech.candy_dev.candycommons.util.Util;
import tech.candy_dev.geniusdungeons.user.DungeonUserData;

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
}
