package tech.candy_dev.geniusdungeons.command.subcommand;

import tech.candy_dev.candycommons.command.CommandContext;
import tech.candy_dev.candycommons.command.SubCommand;
import tech.candy_dev.candycommons.message.Placeholder;
import tech.candy_dev.geniusdungeons.GeniusDungeons;
import tech.candy_dev.geniusdungeons.configuration.Messages;

import java.util.Arrays;
import java.util.List;

public class ReloadCommand extends SubCommand {

    public ReloadCommand() {
        super("/dungeons reload", "geniusdungeons.dungeons.reload", Arrays.asList("reload", "rl"));
    }

    @Override
    public void perform(CommandContext commandContext) {
        Messages.RELOAD_STARTED.send(commandContext.getCommandSender());
        long time = System.currentTimeMillis();
        GeniusDungeons.getInstance().reload();
        time = System.currentTimeMillis() - time;
        Messages.RELOAD_COMPLETED.send(commandContext.getCommandSender(), new Placeholder("{time}", time + ""));
    }
}
