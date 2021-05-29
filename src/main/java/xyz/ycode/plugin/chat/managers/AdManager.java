package xyz.ycode.plugin.chat.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.ycode.plugin.chat.Main;
import xyz.ycode.plugin.chat.utils.TimeUtil;
import xyz.ycode.plugin.chat.utils.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

public class AdManager {
    private static void ReklamaLog(String message) {
        try {
            File mainDir = Main.getInstance().getDataFolder();
            File saveTo = new File(mainDir, "advertisement.txt");
            FileWriter fw = new FileWriter(saveTo, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(message);
            pw.flush();
            pw.close();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

    public static boolean CheckMessage(Player p, String message) {
        if (!message.toLowerCase().contains(".pl") && !message.toLowerCase().contains(".com") && !message.toLowerCase().contains("zethc.pl") && !message.toLowerCase().contains("youtube") && !message.toLowerCase().contains("zethc") && !message.toLowerCase().contains("amazinghard")) {
            if (!message.toLowerCase().contains("I invite you to the server") && !message.toLowerCase().contains("new ip") && !message.toLowerCase().contains("zmieniamy ip") && !message.toLowerCase().contains("wbijaj na serwer") && !message.toLowerCase().contains("ench.pl") && !message.toLowerCase().contains(".ench.pl") && !message.toLowerCase().contains(".pl") && !message.toLowerCase().contains(".tasrv.com") && !message.toLowerCase().contains(".eu") && !message.toLowerCase().contains(".com") && !message.toLowerCase().contains(".com.pl") && !message.toLowerCase().contains(".aternos") && !message.toLowerCase().contains("morehc") && !message.toLowerCase().contains("renderhc") && !message.toLowerCase().contains("coremax") && !message.toLowerCase().contains("rghc") && !message.toLowerCase().contains("ragehc") && !message.toLowerCase().contains("pvpbonsko") && !message.toLowerCase().contains("himc") && !message.toLowerCase().contains("hitmc") && !message.toLowerCase().contains(".titanaxe") && !message.toLowerCase().contains(".titanaxe.com") && !message.toLowerCase().contains(".eu") && !message.toLowerCase().contains("hc4")) {
                return false;
            } else {
                Bukkit.getScheduler().runTask(Main.getInstance(), new Runnable() {
                    public void run() {
                    }
                });
                Iterator var3 = Bukkit.getOnlinePlayers().iterator();

                while(var3.hasNext()) {
                    Player admins = (Player)var3.next();
                    if (admins.hasPermission("ycode.ad.admin")) {
                        admins.sendMessage(Util.fix("&8=====&4 &lAdvertisement&8 ====="));
                        admins.sendMessage(Util.fix("&8> &7Player &6" + p.getName() + " &7is advertising the server!"));
                        admins.sendMessage(Util.fix("&8> &7His message: &6" + message));
                        admins.sendMessage(Util.fix("&8=====&4 &lAdvertisement&8 ====="));
                    }
                }

                ReklamaLog("(Data: " + TimeUtil.getDate(System.currentTimeMillis()) + ") Player " + p.getName() + "(IP: " + p.getAddress().getAddress().getHostAddress() + ") advertises the server! His message -> " + message);
                return true;
            }
        } else {
            return false;
        }
    }
}