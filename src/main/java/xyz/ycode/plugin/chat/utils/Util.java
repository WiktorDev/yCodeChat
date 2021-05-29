package xyz.ycode.plugin.chat.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ycode.plugin.chat.Main;

public class Util {
    public static String fix(String text) {
        return ChatColor.translateAlternateColorCodes('&', text.replace("<%", "«").replace("%>", "»").replace("*", "࿃"));
    }
    public static void sendMsg(Player p, String msg) {
        p.sendMessage(fix(msg));
    }
    public static void sendMsg(CommandSender p, String msg) {
        p.sendMessage(fix(msg));
    }
    public static boolean sendUsage(CommandSender sender, String usage) {
        sender.sendMessage(fix(Main.getInstance().getConfig().getString("usage")).replace("%usage%", usage));
        return true;
    }
}