package xyz.ycode.plugin.chat.objects;

import org.bukkit.command.CommandSender;
import xyz.ycode.plugin.chat.Main;
import xyz.ycode.plugin.chat.utils.Util;

import java.util.Arrays;

public abstract class Command extends org.bukkit.command.Command {
    private final String name;
    private final String usage;
    private final String desc;
    private final String permission;
    public Command(String name, String desc, String usage, String permission, String... aliases) {
        super(name, desc, usage, Arrays.asList(aliases));
        this.name = name;
        this.usage = usage;
        this.desc = desc;
        this.permission = permission;
    }
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission(this.permission) && this.getPermission() != null) {
            String msg = Main.getInstance().getConfig().getString("perm").replace("%perm%", this.permission);
            Util.sendMsg(sender, msg);
            return false;
        } else {
            return this.onExecute(sender, args);
        }
    }
    public abstract boolean onExecute(CommandSender var1, String[] var2);
    public String getName() {
        return this.name;
    }
    public String getDesc() {
        return this.desc;
    }
    public String getPermission() {
        return this.permission;
    }
}