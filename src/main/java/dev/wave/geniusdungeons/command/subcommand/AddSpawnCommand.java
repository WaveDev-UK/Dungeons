package dev.wave.geniusdungeons.command.subcommand;

import dev.wave.geniusdungeons.GeniusDungeons;
import dev.wave.geniusdungeons.configuration.Messages;
import org.bukkit.Location;
import tech.candy_dev.candycommons.command.CommandContext;
import tech.candy_dev.candycommons.command.SubCommand;
import tech.candy_dev.candycommons.message.Placeholder;
import tech.candy_dev.candycommons.util.Util;
import dev.wave.geniusdungeons.boss.Boss;

import java.util.Collections;

public class AddSpawnCommand extends SubCommand {

    public AddSpawnCommand(){
        super("/dungeons addspawn {boss}", "geniusdungeons.dungeons.addspawn", Collections.singletonList("addspawn"));
    }

    @Override
    public void perform(CommandContext commandContext) {
        Boss boss = GeniusDungeons.getInstance().getBossManager().getBoss(commandContext.getArgs()[1]);
        if(boss == null){
            Messages.BOSS_NOT_FOUND.send(commandContext.getPlayer(), new Placeholder("{boss}", commandContext.getArgs()[1]));
            return;
        }

        Location location = commandContext.getPlayer().getLocation();

        boss.addSpawn(location);

        Messages.BOSS_SPAWN_ADDED.sendList(commandContext.getPlayer(), new Placeholder("{boss}", boss.getId()), Util.createLocationPlaceholder("location", location));
    }
}
