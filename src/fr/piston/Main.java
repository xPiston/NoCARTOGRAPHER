package fr.piston;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin implements Listener {

    List<String> world = getConfig().getStringList("mondes");



    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
        //getCommand("NoCARTOGRAPHER").setExecutor(new Reload());
        this.getCommand("NoCARTOGRAPHER").setExecutor(new Reload(this));
        createConfig();
    }

    private void createConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info("Config.yml not found, creating!");
                //MONDES
                String mondes = this.getConfig().getString("mondes");
                this.getConfig().set("world", mondes);

                //MSG
                String msg = this.getConfig().getString("msg");
                this.getConfig().set("This type of NPC is not available at the moment!", msg);

                saveDefaultConfig();
            } else {
                getLogger().info("Config.yml found, loading!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @EventHandler
    public void onVillagerClick(PlayerInteractEntityEvent e){
        Player player = e.getPlayer();
        Entity ent = e.getRightClicked();
        if(world != null) {
            for(String worldString : world) {
                if (player.getLocation().getWorld().getName().equalsIgnoreCase(worldString)) {
                    if (ent.getType() == EntityType.VILLAGER) {
                        Villager v = (Villager) ent;
                        if (v.getProfession().equals(Villager.Profession.CARTOGRAPHER)) {
                            e.setCancelled(true);
                            player.sendMessage(Bukkit.getPluginManager().getPlugin("NoCARTOGRAPHER").getConfig().getString("msg"));
                        }
                    }
                }
            }
        }
    }

/*Bukkit.getPluginManager().getPlugin("NoCARTOGRAPHER").getConfig().getString("mondes")*/
}
