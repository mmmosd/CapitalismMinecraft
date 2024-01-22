package _.capitalismminecraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;

import java.io.File;

public final class CapitalismMinecraft extends JavaPlugin {
    private static CapitalismMinecraft instance;

    public Wallet wallet = new Wallet();
    public Shop shop = new Shop();
    public Menu menu = new Menu();

    public static CapitalismMinecraft getInstance()
    {
        if (instance == null)
        {
            instance = new CapitalismMinecraft();
        }
        return instance;
    }

    public void InitConfig() {
        saveConfig();
        File cfile = new File(getDataFolder(), "config.yml");
        if (cfile.length() == 0) {
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }

    public void updatePerSec() {
        Bukkit.getScheduler().runTaskTimer( this , new Runnable() {

            @Override
            public void run() {
                for(Player p : Bukkit.getServer().getOnlinePlayers())
                {
                    int money = wallet.Wlist.get(p.getName());
                    p.sendActionBar(Component.text(ChatColor.GREEN + "나의 자산: " + money + "$"));
                }
            }
        }, 20, 20);
    }

    public void updatePrice() {
        Bukkit.getScheduler().runTaskTimer( this , new Runnable() {

            @Override
            public void run() {
                shop.ResetPrice();
                shop.ResetData();
            }
        }, 288000, 288000);
    }
    

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Event(), this);

        InitConfig();
        updatePerSec();
        updatePrice();
    }

    @Override
    public void onDisable() {
        
    }
}
