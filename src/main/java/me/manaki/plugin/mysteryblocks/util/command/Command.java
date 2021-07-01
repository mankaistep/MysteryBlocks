package me.manaki.plugin.mysteryblocks.util.command;

import me.manaki.plugin.mysteryblocks.MysteryBlocks;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Command {
    
    private int tickDelay;
    private String cmd;
    private CommandType type;

    public Command(String s) {
        for (CommandType t : CommandType.values()) {
            if (s.contains("[" + t.name().toLowerCase() + "] ")) {
                this.type = t;
                this.cmd = s.replace("[" + t.name().toLowerCase() + "] ", "");
            }
        }
        if (s.equals("")) s = "*";

        // Delay
        String regex = "\\{(?<delay>\\d+)}\\s";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(this.cmd);
        if (m.find()) this.tickDelay = Integer.parseInt(m.group("delay"));

        this.cmd = this.cmd.replace("{" + this.tickDelay + "} ", "");
    }

    public String getCommand() {
        return this.cmd;
    }

    public CommandType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "[" + this.type.name().toLowerCase() + "] " + this.cmd;
    }

    public void execute(Player player, Map<String, String> placeholders) {
        var cmd = this.cmd;
        for (Map.Entry<String, String> e : placeholders.entrySet()) {
            cmd = cmd.replace(e.getKey(), e.getValue());
        }
        execute(player, cmd);
    }

    public void execute(Player player, String cmd) {
        if (this.tickDelay > 0) {
            Bukkit.getScheduler().runTaskLater(MysteryBlocks.get(), () -> {
                this.type.execute(cmd, player);
            }, this.tickDelay);
        }
        else this.type.execute(cmd, player);
    }

}
