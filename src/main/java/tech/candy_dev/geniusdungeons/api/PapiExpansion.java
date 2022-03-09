package tech.candy_dev.geniusdungeons.api;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tech.candy_dev.candycommons.user.User;
import tech.candy_dev.candycommons.util.Util;

public class PapiExpansion extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "geniusdungeons";
    }

    @Override
    public String getAuthor() {
        return "CandyDev";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {

        User user = Util.getUser(player.getUniqueId());
        switch (identifier) {
            case "xp":
            case "exp":
                return Util.getFormattedNumber(DungeonsAPI.getInstance().getExp(user), false);
            case "level":
                return Util.getFormattedNumber(DungeonsAPI.getInstance().getLevel(user), false);
        }

        return null;
    }

}
