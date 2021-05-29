package xyz.ycode.plugin.chat.objects;

import xyz.ycode.plugin.chat.Main;

import java.util.List;

public class BlockedList {
    public static List<String> blockedWords = ((Main) Main.getPlugin(Main.class)).getConfig().getStringList("blockedwords");
}
