package tech.candy_dev.geniusdungeons.command.subcommand;

import tech.candy_dev.candycommons.command.CommandContext;
import tech.candy_dev.candycommons.command.SubCommand;
import tech.candy_dev.geniusdungeons.GeniusDungeons;
import tech.candy_dev.geniusdungeons.configuration.Messages;

import java.util.Collections;
import java.util.List;

public class ForceSpawnCommand extends SubCommand {
    public ForceSpawnCommand() {
        super("/dungeons forcespawn", "geniusdungeons.dungeons", Collections.singletonList("forcespawn"));
    }

    @Override
    public void perform(CommandContext commandContext) {
        GeniusDungeons.getInstance().getBossManager().runForceSpawn();
        Messages.BOSS_FORCE_SPAWN.send(commandContext.getCommandSender());
    }
}
