package me.manaki.plugin.mysteryblocks.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.manaki.plugin.mysteryblocks.MysteryBlocks;
import me.manaki.plugin.mysteryblocks.mysteryblock.MysteryBlock;
import me.manaki.plugin.mysteryblocks.mysteryblock.MysteryData;
import me.manaki.plugin.mysteryblocks.util.command.Command;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MysteryUtils {

    private static FileConfiguration dataFile = null;
    private static FileConfiguration configFile = null;
    private static String world;
    private static Map<String, MysteryBlock> mysteryBlocks;
    private static List<MysteryData> mysteryData = null;

    public static void reloadConfig() {
        // Config
        mysteryBlocks = Maps.newHashMap();
        var file = new File(MysteryBlocks.get().getDataFolder(), "config.yml");
        configFile = YamlConfiguration.loadConfiguration(file);
        world = configFile.getString("world");
        for (String id : configFile.getConfigurationSection("mystery-block").getKeys(false)) {
            int amount = configFile.getInt("mystery-block." + id + ".amount");
            List<Command> cmd = configFile.getStringList("mystery-block." + id + ".commands").stream().map(Command::new).collect(Collectors.toList());
            List<String> m = configFile.getStringList("mystery-block." + id + ".messages").stream().map(s -> s.replace("&", "§")).collect(Collectors.toList());
            var block = Material.valueOf(configFile.getString("mystery-block." + id + ".block"));
            mysteryBlocks.put(id, new MysteryBlock(id, amount, cmd, m, block));
        }
    }

    public static void init() {
        reloadConfig();

        // Data
        var file = new File(MysteryBlocks.get().getDataFolder(), "data.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dataFile = YamlConfiguration.loadConfiguration(file);
        mysteryData = Lists.newArrayList();
        for (var s : dataFile.getStringList("blocks")) {
            var id = s.split(";")[0];
            var world = s.split(";")[1];
            var x = Double.parseDouble(s.split(";")[2]);
            var y = Double.parseDouble(s.split(";")[3]);
            var z = Double.parseDouble(s.split(";")[4]);

            mysteryData.add(new MysteryData(id, world, x, y, z));
        }
    }

    public static boolean is(Material m) {
        for (Map.Entry<String, MysteryBlock> e : mysteryBlocks.entrySet()) {
            if (e.getValue().getBlock() == m) return true;
        }
        return false;
    }

    public static MysteryBlock getMysteryBlock(String id) {
        if (dataFile == null) init();
        return mysteryBlocks.getOrDefault(id, null);
    }

    public static List<MysteryData> getData() {
        if (dataFile == null) init();
        return mysteryData;
    }

    public static void add(String id, Block block) {
        if (dataFile == null) init();
        var data = new MysteryData(id, block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
        mysteryData.add(data);
    }

    public static void remove(Block block) {
        if (dataFile == null) init();
        mysteryData.removeIf(md -> md.getX() == block.getX() && md.getY() == block.getY() && md.getZ() == block.getZ() && md.getWorld().equals(block.getWorld().getName()));
    }

    public static String read(Block block) {
        if (block.getWorld().getName().equals(world)) {
            for (Map.Entry<String, MysteryBlock> e : mysteryBlocks.entrySet()) {
                if (block.getType() == e.getValue().getBlock()) return e.getKey();
            }
        }
        return null;
//        if (dataFile == null) init();
//        for (MysteryData md : mysteryData) {
//            if (md.getX() == block.getX() && md.getY() == block.getY() && md.getZ() == block.getZ() && md.getWorld().equals(block.getWorld().getName())) return md.getId();
//        }
//        return null;
    }

    public static void save() {
        if (dataFile == null) init();
        dataFile.set("blocks", mysteryData.stream().map(md -> md.getId() + ";" + md.getWorld() + ";" + md.getX() + ";" + md.getY() + ";" + md.getZ()).collect(Collectors.toList()));
        var file = new File(MysteryBlocks.get().getDataFolder(), "data.yml");
        try {
            dataFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ItemStack getPlacer(String id) {
        var md = getMysteryBlock(id);
        if (md == null) return null;
        var is = new ItemStack(md.getBlock());
        var ism = new ItemStackManager(is);

        ism.setName("§aĐặt Mystery block");
        ism.setTag("mysteryblocks.placer", id);

        return is;
    }

    public static String read(ItemStack is) {
        if (is == null) return null;
        var ism = new ItemStackManager(is);
        if (ism.hasTag("mysteryblocks.placer")) return ism.getTag("mysteryblocks.placer");
        return null;
    }

    public static String getWorld() {
        if (world == null) reloadConfig();
        return world;
    }
}
