package me.manaki.plugin.mysteryblocks.mysteryblock;

import me.manaki.plugin.mysteryblocks.util.command.Command;
import org.bukkit.Material;

import java.util.List;

public class MysteryBlock {

    private final String id;
    private final int amount;
    private final List<Command> commands;
    private final List<String> messages;
    private final Material block;

    public MysteryBlock(String id, int amount, List<Command> commands, List<String> messages, Material block) {
        this.id = id;
        this.amount = amount;
        this.commands = commands;
        this.messages = messages;
        this.block = block;
    }

    public String getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public List<String> getMessages() {
        return messages;
    }

    public Material getBlock() {
        return block;
    }

}
