package xyz.ycode.plugin.chat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.ycode.plugin.chat.listeners.AntiSwearListener;
import xyz.ycode.plugin.chat.listeners.PlayerChatListener;
import xyz.ycode.plugin.chat.managers.CommandManager;

public final class Main extends JavaPlugin {
    private static Main instance;
    public static Main plugin;

    public Main() {
        plugin = this;
    }

    public static Main getInstance() {
        return instance;
    }
    public static Main getPlugin() {
        return plugin;
    }
    @Override
    public void onEnable() {
        Bukkit.broadcastMessage()
        instance = this;
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        CommandManager commandManager = new CommandManager();
        commandManager.registerCommands();
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), this);
        this.getServer().getPluginManager().registerEvents(new AntiSwearListener(), this);
    }

    @Override
    public void onDisable() {
    }
}
