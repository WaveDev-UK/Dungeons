package dev.wave.geniusdungeons.command.subcommand;

import dev.wave.geniusdungeons.GeniusDungeons;
import org.bukkit.Location;
import tech.candy_dev.candycommons.command.CommandContext;
import tech.candy_dev.candycommons.command.SubCommand;
import tech.candy_dev.candycommons.message.CommonMessage;
import tech.candy_dev.candycommons.message.Placeholder;
import tech.candy_dev.candycommons.util.Util;
import dev.wave.geniusdungeons.boss.Boss;
import dev.wave.geniusdungeons.configuration.Messages;

import java.util.Collections;
import java.util.List;

public class RemoveSpawnCommand extends SubCommand {
    public RemoveSpawnCommand() {
        super("/dungeons removespawn {boss} {id}", "geniusdungeons.dungeons.removespawn", Collections.singletonList("removespawn"));
    }

    @Override
    public void perform(CommandContext commandContext) {
        Boss boss = GeniusDungeons.getInstance().getBossManager().getBoss(commandContext.getArgs()[1]);
        if (boss == null) {
            Messages.BOSS_NOT_FOUND.send(commandContext.getCommandSender(), new Placeholder("{boss}", commandContext.getArgs()[1]));
            return;
        }

        if (!Util.isInteger(commandContext.getArgs()[2])) {
            CommonMessage.NOT_A_NUMBER.send(commandContext.getCommandSender(), new Placeholder("{arg}", commandContext.getArgs()[2]));
            return;
        }

        int index = Integer.parseInt(commandContext.getArgs()[2]);

        List<Location> spawnLocations = boss.getSpawnLocationList();

        if(spawnLocations.get(index-1) == null){
            Messages.BOSS_SPAWN_NOT_FOUND.send(commandContext.getCommandSender(), new Placeholder("{id}", index + ""), new Placeholder("{boss}", boss.getId()));
            return;
        }

        boss.removeSpawn(spawnLocations.get(index-1));

        Messages.BOSS_SPAWN_REMOVED.send(commandContext.getCommandSender(), new Placeholder("{id}", index + ""), new Placeholder("{boss}", boss.getId()));
    }
}
