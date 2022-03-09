package tech.candy_dev.geniusdungeons.command;

import tech.candy_dev.candycommons.command.CandyCommand;
import tech.candy_dev.candycommons.command.CommandContext;
import tech.candy_dev.candycommons.command.SubCommand;
import tech.candy_dev.candycommons.message.Placeholder;
import tech.candy_dev.candycommons.util.Util;
import tech.candy_dev.geniusdungeons.command.subcommand.*;
import tech.candy_dev.geniusdungeons.configuration.Messages;

public class DungeonsCommand extends CandyCommand {

    public DungeonsCommand() {
        super("dungeons");
    }

    @Override
    public void setup() {
        setPermission("geniusdungeons.dungeons");
        setDescription("Dungeons Command");
        addSubCommands(new AddSpawnCommand(), new ForceSpawnCommand(), new GiveXPCommand(), new ReloadCommand(), new RemoveSpawnCommand(), new SetLevelCommand(), new ToggleMessageCommand());
    }

    @Override
    public void perform(CommandContext commandContext) {
        for (String message : Messages.HELP.getStringList()) {

            message = Util.c(Placeholder.apply(message, new Placeholder("{prefix}", Messages.PREFIX.getString())));

            if (!message.contains("{commands}")) {
                commandContext.getCommandSender().sendMessage(message);
                continue;
            }

            for (SubCommand subCommand : getSubCommandList()) {
                if (subCommand.isPlayer() && commandContext.getPlayer() == null) {
                    continue;
                }
                if (subCommand.getPermission() != null && !commandContext.getCommandSender().hasPermission(subCommand.getPermission())) {
                    continue;
                }

                commandContext.getCommandSender().sendMessage(Placeholder.apply(message, new Placeholder("{commands}", subCommand.getUsage())));
            }
        }
    }
}
