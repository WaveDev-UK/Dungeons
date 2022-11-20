package dev.wave.geniusdungeons.command.subcommand;

import dev.wave.geniusdungeons.GeniusDungeons;
import tech.candy_dev.candycommons.command.CommandContext;
import tech.candy_dev.candycommons.command.SubCommand;
import dev.wave.geniusdungeons.configuration.Messages;

import java.util.Collections;

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
