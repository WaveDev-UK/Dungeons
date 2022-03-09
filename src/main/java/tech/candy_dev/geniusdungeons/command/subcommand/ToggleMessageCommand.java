package tech.candy_dev.geniusdungeons.command.subcommand;

import tech.candy_dev.candycommons.command.CommandContext;
import tech.candy_dev.candycommons.command.SubCommand;
import tech.candy_dev.candycommons.message.Placeholder;
import tech.candy_dev.candycommons.user.User;
import tech.candy_dev.candycommons.util.Util;
import tech.candy_dev.geniusdungeons.GeniusDungeons;
import tech.candy_dev.geniusdungeons.configuration.Messages;
import tech.candy_dev.geniusdungeons.user.DungeonUserData;

import java.util.Arrays;
import java.util.List;

public class ToggleMessageCommand extends SubCommand {
    public ToggleMessageCommand() {
        super("/dungeons togglemessage", "geniusdungeons.dungeons.togglemessage", Arrays.asList("togglemessage", "togglemsg"));
        setPlayer(true);
    }

    @Override
    public void perform(CommandContext commandContext) {
        User user = Util.getUser(commandContext.getPlayer().getUniqueId());
        DungeonUserData dungeonUserData = user.getUserData(DungeonUserData.class);
        dungeonUserData.setReceivingMessages(!dungeonUserData.isReceivingMessages());
        Messages.MESSAGES_TOGGLED.send(commandContext.getCommandSender(), new Placeholder("{toggle}", dungeonUserData.isReceivingMessages() ? "Enabled" : "Disabled"));
    }
}
