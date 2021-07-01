package me.manaki.plugin.mysteryblocks;

import me.manaki.plugin.mysteryblocks.command.MysteryCommand;
import me.manaki.plugin.mysteryblocks.listener.BlockListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MysteryBlocks extends JavaPlugin {

    private static MysteryBlocks inst;

    @Override
    public void onEnable() {
        inst = this;
        this.saveDefaultConfig();
        this.getCommand("mysteryblocks").setExecutor(new MysteryCommand());

        Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
    }

    public static MysteryBlocks get() {
        return inst;
    }

}
