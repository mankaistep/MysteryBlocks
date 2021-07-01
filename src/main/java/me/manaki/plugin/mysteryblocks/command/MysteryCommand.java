package me.manaki.plugin.mysteryblocks.command;

import me.manaki.plugin.mysteryblocks.util.MysteryUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MysteryCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length == 0) {
            sender.sendMessage("/mysteryblocks(/md) getplacer <id>");
            return false;
        }

        if (args[0].equals("getplacer")) {
            var id = args[1];
            var is = MysteryUtils.getPlacer(id);
            var p = (Player) sender;
            p.getInventory().addItem(is);
            sender.sendMessage("§aOkay done");
        }

        return false;
    }

}
