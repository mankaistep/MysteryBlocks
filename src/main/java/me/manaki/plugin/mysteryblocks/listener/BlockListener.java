package me.manaki.plugin.mysteryblocks.listener;

import com.google.common.collect.Maps;
import me.manaki.plugin.mysteryblocks.util.MysteryUtils;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Map;
import java.util.Random;

public class BlockListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerBreakBlock(BlockBreakEvent e) {
        var player = e.getPlayer();
        var b = e.getBlock();
        var id = MysteryUtils.read(b);
        if (id == null) return;

        // Is mystery block
        try {
            var md = MysteryUtils.getMysteryBlock(id);

            for (int i = 0 ; i < md.getAmount() ; i++) {
                int index = new Random().nextInt(md.getCommands().size());

                var cmd = md.getCommands().get(index);
                var message = md.getMessages().get(index);

                cmd.execute(player, Map.of("%player%", player.getName()));
                player.sendMessage(message);
            }

            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        }
        finally {
            MysteryUtils.remove(b);
            MysteryUtils.save();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e) {
        var player = e.getPlayer();
        var b = e.getBlock();
        var is = e.getItemInHand();
        var id = MysteryUtils.read(is);
        if (id == null) return;

        MysteryUtils.add(id, b);
        MysteryUtils.save();
    }

}
