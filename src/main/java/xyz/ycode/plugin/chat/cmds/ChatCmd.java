package xyz.ycode.plugin.chat.cmds;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ycode.plugin.chat.Main;
import xyz.ycode.plugin.chat.objects.Command;
import xyz.ycode.plugin.chat.utils.Util;

import java.util.Iterator;

public class ChatCmd extends Command {
    public static boolean chat;

    public ChatCmd() {
        super("chat", "Chat command", "/chat <on/off/clear>","ycode.chat.admin", new String[]{"ch"});
        chat = true;
    }
    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        if (args.length >= 1 && args.length <= 1) {
            if (args[0].equalsIgnoreCase("on")) {
                if (chat) {
                    Util.sendMsg(sender, Main.getInstance().getConfig().getString("chat_is_on"));
                    return false;
                } else {
                    chat = true;
                    Bukkit.broadcastMessage(Util.fix(Main.getInstance().getConfig().getString("chat_on")).replace("%player%", sender.getName()));
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("off")) {
                if (!chat) {
                    Util.sendMsg(sender, Main.getInstance().getConfig().getString("chat_is_off"));
                    return false;
                } else {
                    chat = false;
                    Bukkit.broadcastMessage(Util.fix(Main.getInstance().getConfig().getString("chat_off")).replace("%player%", sender.getName()));
                    return true;
                }
            } else {
                if (args[0].equalsIgnoreCase("clear")) {
                    Iterator var5 = Bukkit.getOnlinePlayers().iterator();
                    while(var5.hasNext()) {
                        Player online = (Player)var5.next();
                        for(int i = 0; i < 100; ++i) {
                            online.sendMessage("   ");
                        }
                    }
                    Bukkit.broadcastMessage(Util.fix(Main.getInstance().getConfig().getString("chat_clear")).replace("%player%", sender.getName()));
                }
                return false;
            }
        } else {
            Util.sendUsage(sender, this.getUsage());
            return false;
        }
    }
}
