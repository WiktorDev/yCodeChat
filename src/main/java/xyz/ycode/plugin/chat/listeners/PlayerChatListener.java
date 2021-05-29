package xyz.ycode.plugin.chat.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import xyz.ycode.plugin.chat.Main;
import xyz.ycode.plugin.chat.cmds.ChatCmd;
import xyz.ycode.plugin.chat.managers.AdManager;
import xyz.ycode.plugin.chat.utils.Util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

public class PlayerChatListener implements Listener {
    private static Map<Player, Long> cooldown = new HashMap();

    @EventHandler(
            priority = EventPriority.LOW
    )
    public void onChat(AsyncPlayerChatEvent e) {
        if (!e.isCancelled()) {
            Player p = e.getPlayer();

            if (p.hasPermission("ycode.chat.color")) {
                if (p.hasPermission("ycode.chat.special")) {
                    e.setMessage(Util.fix(e.getMessage()));
                } else {
                    e.setMessage(Util.fix(e.getMessage().replace("&l", "").replace("&k", "").replace("&n", "").replace("&m", "").replace("&o", "")));
                }
            }


            if (!p.hasPermission("ycode.chat.bypass")) {
                if (!ChatCmd.chat) {
                    e.setCancelled(true);
                    p.sendMessage(Util.fix(Main.getInstance().getConfig().getString("chat_currently_off")));
                } else if (cooldown.get(p) != null && System.currentTimeMillis() <= Long.valueOf((Long)cooldown.get(p))) {
                    e.setCancelled(true);
                    p.sendMessage(Util.fix(Main.getInstance().getConfig().getString("chat_cooldown")));
                } else {
                    cooldown.put(p, System.currentTimeMillis() + Main.getInstance().getConfig().getInt("cooldown"));
                    if (AdManager.CheckMessage(p, e.getMessage())) {
                        e.setCancelled(true);
                    }
                }
            }
        }

    }
}