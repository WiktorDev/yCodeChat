package xyz.ycode.plugin.chat.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.ycode.plugin.chat.Main;
import xyz.ycode.plugin.chat.objects.BlockedList;
import xyz.ycode.plugin.chat.objects.Command;
import xyz.ycode.plugin.chat.utils.Util;

import java.util.List;

public class AntiswearCmd extends Command {
    FileConfiguration config = ((Main)Main.getPlugin(Main.class)).getConfig();
    List<String> blockedWords;

    public AntiswearCmd() {
        super("antiswearadd", "Antiswear command", "/antiswearadd <word>","ycode.antiswear.admin", new String[]{"as"});
        this.blockedWords = BlockedList.blockedWords;
    }
    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        if (args.length != 0 && args.length <= 1) {
            if (this.blockedWords.contains(args[0])) {
                sender.sendMessage(Util.fix(Main.getInstance().getConfig().getString("word_already_exists")));
                return true;
            } else {
                this.blockedWords.add(args[0].toLowerCase());
                this.config.set("blockedwords", this.blockedWords);
                BlockedList.blockedWords = this.blockedWords;
                ((Main)Main.getPlugin(Main.class)).saveConfig();
                sender.sendMessage(Util.fix(Main.getInstance().getConfig().getString("word_add_success")));
                return true;
            }
        } else {
            Util.sendUsage(sender, this.getUsage());
            return true;
        }
    }
}
