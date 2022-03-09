package tech.candy_dev.geniusdungeons.command.subcommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tech.candy_dev.candycommons.command.CommandContext;
import tech.candy_dev.candycommons.command.SubCommand;
import tech.candy_dev.candycommons.message.CommonMessage;
import tech.candy_dev.candycommons.message.Placeholder;
import tech.candy_dev.candycommons.user.User;
import tech.candy_dev.candycommons.util.Util;
import tech.candy_dev.geniusdungeons.api.DungeonsAPI;
import tech.candy_dev.geniusdungeons.configuration.Messages;
import tech.candy_dev.geniusdungeons.user.DungeonUserData;

import java.util.Collections;
import java.util.List;

public class SetLevelCommand extends SubCommand {
    public SetLevelCommand() {
        super("/dungeons setlevel {player} {level}", "geniusdungeons.dungeons.setlevel", Collections.singletonList("setlevel"));
    }

    @Override
    public void perform(CommandContext commandContext) {

        Player player = Bukkit.getPlayer(commandContext.getArgs()[1]);
        if (player == null) {
            CommonMessage.PLAYER_NOT_FOUND.send(commandContext.getCommandSender(), new Placeholder("{player}", commandContext.getArgs()[1]));
            return;
        }

        if (!Util.isDouble(commandContext.getArgs()[2])) {
            CommonMessage.NOT_A_NUMBER.send(commandContext.getCommandSender(), new Placeholder("{arg}", commandContext.getArgs()[2]));
            return;
        }

        double level = Integer.parseInt(commandContext.getArgs()[2]);

        User user = Util.getUser(player.getUniqueId());

        DungeonsAPI.getInstance().setLevel(user, level);

        Placeholder lvl = new Placeholder("{level}", Util.getFormattedNumber(level, false));
        Messages.LEVEL_SET_GIVEN.send(commandContext.getCommandSender(), new Placeholder("{player}", player.getName()), lvl);
        Messages.LEVEL_SET_RECEIVED.send(player, lvl);
    }
}
