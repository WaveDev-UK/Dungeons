package dev.wave.geniusdungeons.listener;

import de.tr7zw.changeme.nbtapi.NBTEntity;
import dev.wave.geniusdungeons.GeniusDungeons;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import tech.candy_dev.candycommons.item.Item;
import tech.candy_dev.candycommons.message.Placeholder;
import tech.candy_dev.candycommons.user.User;
import tech.candy_dev.candycommons.util.Util;
import dev.wave.geniusdungeons.boss.Boss;
import dev.wave.geniusdungeons.configuration.Messages;
import dev.wave.geniusdungeons.user.DungeonUserData;

import java.util.List;

public class EntityKillEntityListener implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        Player player = e.getEntity().getKiller();

        if (player == null) {
            return;
        }

        Boss boss = GeniusDungeons.getInstance().getBossManager().getBoss(e.getEntity());

        if (boss == null) {
            return;
        }

        User user = Util.getUser(player.getUniqueId());

        DungeonUserData dungeonUserData = user.getUserData(DungeonUserData.class);

        NBTEntity nbtEntity = new NBTEntity(e.getEntity());

        double xp;

        if (nbtEntity.hasKey("xp")) {
            xp = nbtEntity.getDouble("xp");
        } else {
            xp = boss.getXp();
        }

        dungeonUserData.setExp(dungeonUserData.getExp() + xp);

        double money = boss.getMoney();

        if (money > 0) {
            GeniusDungeons.getInstance().getEcon().depositPlayer(player, money);
        }

        if (dungeonUserData.isReceivingMessages()) {
            for (String message : Messages.BOSS_KILLED.getStringList()) {
                message = Util.c(Placeholder.apply(message, new Placeholder("{prefix}", Messages.PREFIX.getString())));
                if (message.contains("{rewards}")) {
                    player.sendMessage(Placeholder.apply(message, new Placeholder("{reward}", Util.getFormattedNumber(xp, false) + "XP")));
                    if (money > 0) {
                        player.sendMessage(Placeholder.apply(message, new Placeholder("{reward}", "$" + Util.getFormattedNumber(money, true))));
                    }
                    continue;
                }

                player.sendMessage(message);
            }
        }

        GeniusDungeons.getInstance().getBossManager().removeActiveBoss(boss, e.getEntity());

        e.getDrops().clear();

        List<Item> drops = boss.getRandomDrops();
        if (drops == null) {
            return;
        }

        for (Item item : drops) {
            ItemStack itemStack = item.build();
            e.getDrops().add(itemStack);
        }

    }

}
