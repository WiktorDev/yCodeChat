package xyz.ycode.plugin.chat.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import xyz.ycode.plugin.chat.Main;
import xyz.ycode.plugin.chat.objects.BlockedList;
import xyz.ycode.plugin.chat.utils.Util;

import java.util.Iterator;
import java.util.List;

public class AntiSwearListener implements Listener {
    List<String> blockedWords;

    public AntiSwearListener() {
        this.blockedWords = BlockedList.blockedWords;
    }

    @EventHandler
    public void onPlayerSwear(AsyncPlayerChatEvent event) {
        String message = event.getMessage().toLowerCase();
        Player sender = event.getPlayer();
        Iterator var5 = this.blockedWords.iterator();

        while(var5.hasNext()) {
            String word = (String)var5.next();
            if (message.contains(word) && !sender.hasPermission("ycode.antiswear.bypass") && !sender.hasPermission("ycode.antiswear.admin")) {
                sender.sendMessage(Util.fix(Main.getInstance().getConfig().getString("illegal_words")));
                event.setCancelled(true);
                break;
            }
        }

    }
}
