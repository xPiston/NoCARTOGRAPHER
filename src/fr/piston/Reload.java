package fr.piston;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reload implements CommandExecutor {
    public static Main plugin;
    public Reload(Main instance)
    {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length > 0)
        {
        if (args[0].equalsIgnoreCase("reload")) {
            if (args.length > 1) {
                sender.sendMessage(ChatColor.RED + "To many arguments.");
                return false;
            }
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (hasReload(player)) {
                    plugin.getPluginLoader().disablePlugin(plugin);
                    plugin.getPluginLoader().enablePlugin(plugin);
                    sender.sendMessage("[§7§lNoCARTOGRAPHER§f]" + ChatColor.YELLOW + " Reloaded");
                } else {
                    sender.sendMessage(ChatColor.RED + "You do not have access to that.");
                }
            }
            return true;
        }
        } else {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (hasReload(player)) {
                    sender.sendMessage(ChatColor.GRAY + plugin.getDescription().getFullName());
                    sender.sendMessage(ChatColor.GRAY + "/reload" + " reload the plugin");
                }
            }
        }
        return false;
    }

    public boolean hasReload(Player player) {
        if (player.hasPermission("NoCARTOGRAPHER.reload")) {
            return true;
        } else if  (player.hasPermission("NoCARTOGRAPHER.*")) {
            return true;
        }
        return false;
    }
}