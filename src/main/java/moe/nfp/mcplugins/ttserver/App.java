package moe.nfp.mcplugins.ttserver;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.FurnaceRecipe;

public class App extends JavaPlugin {
    private static JavaPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getLogger().info("TTServerPlugin Loading!");
        Bukkit.getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new AnvilCraft(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Fishing(), this);
        Bukkit.getServer().addRecipe(new FurnaceRecipe(new ItemStack(Material.GOLD_NUGGET), Material.GOLD_ORE));
    }
    @Override
    public void onDisable() {
        plugin = null;
        getLogger().info("TTServerPlugin closing!");
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static void logInfo(String msg) {
        if (plugin == null) return;
        plugin.getLogger().info(msg);
    }
}