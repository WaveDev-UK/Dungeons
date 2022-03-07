package tech.candy_dev.geniusdungeons.command.subcommand;

import tech.candy_dev.candycommons.command.CommandContext;
import tech.candy_dev.candycommons.command.SubCommand;
import tech.candy_dev.candycommons.message.Placeholder;
import tech.candy_dev.geniusdungeons.GeniusDungeons;
import tech.candy_dev.geniusdungeons.boss.Boss;
import tech.candy_dev.geniusdungeons.boss.BossEntity;
import tech.candy_dev.geniusdungeons.configuration.Messages;

import java.util.Collections;
import java.util.List;

public class SpawnCommand extends SubCommand {
    public SpawnCommand() {
        super("/dungeons spawn {boss}", "geniusdungeons.dungeons.spawn", Collections.singletonList("spawn"));
        setPlayer(true);
    }

    @Override
    public void perform(CommandContext commandContext) {
        Boss boss = GeniusDungeons.getInstance().getBossManager().getBoss(commandContext.getArgs()[1]);
        if (boss == null) {
            Messages.BOSS_NOT_FOUND.send(commandContext.getCommandSender(), new Placeholder("{boss}", commandContext.getArgs()[1]));
            return;
        }

        for (BossEntity bossEntity : boss.getBossEntities()) {
            bossEntity.getCandyEntity().spawn(commandContext.getPlayer().getLocation());
        }

    }
}
