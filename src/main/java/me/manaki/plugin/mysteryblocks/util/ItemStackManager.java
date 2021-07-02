package me.manaki.plugin.mysteryblocks.util;

import com.google.common.collect.Maps;
import me.manaki.plugin.mysteryblocks.MysteryBlocks;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Map;

public class ItemStackManager {

    private final ItemStack is;
    private final ItemMeta meta;

    private Plugin plugin;

    public ItemStackManager(ItemStack is) {
        this.is = is;
        this.meta = isNull() ? null : is.getItemMeta();
        this.plugin = MysteryBlocks.get();
    }

    public ItemStackManager(Plugin plugin, ItemStack is) {
        this(is);
        this.plugin = plugin;
    }

    public boolean isNull() {
        return is == null || is.getType() == Material.AIR;
    }

    public boolean hasLore() {
        return meta.hasLore();
    }

    public boolean hasDisplayName() {
        if (meta == null) return false;
        return meta.hasDisplayName();
    }

    public List<String> getLore() {
        return meta.getLore();
    }

    public String getName() {
        if (hasDisplayName()) return meta.getDisplayName();
        return is.getType().name();
    }

    public void setLore(List<String> lore) {
        meta.setLore(lore);
        is.setItemMeta(meta);
    }

    public void setName(String name) {
        meta.setDisplayName(name);
        is.setItemMeta(meta);
    }

    // Tag

    public boolean hasTag(String key) {
        NamespacedKey nk = new NamespacedKey(plugin, key);
        return meta.getPersistentDataContainer().has(nk, PersistentDataType.STRING);
    }

    public String getTag(String key) {
        if (!hasTag(key)) return null;
        NamespacedKey nk = new NamespacedKey(plugin, key);
        return meta.getPersistentDataContainer().get(nk, PersistentDataType.STRING);
    }

    public Map<String, String> getTags() {
        Map<String, String> m = Maps.newLinkedHashMap();
        for (NamespacedKey nk : meta.getPersistentDataContainer().getKeys()) {
            String v = meta.getPersistentDataContainer().get(nk, PersistentDataType.STRING);
            if (v == null) continue;
            m.put(nk.getKey(), v);
        }

        return m;
    }

    public void setTag(String key, String value) {
        NamespacedKey nk = new NamespacedKey(plugin, key);
        meta.getPersistentDataContainer().set(nk, PersistentDataType.STRING, value);
        is.setItemMeta(meta);
    }


}
