package _.capitalismminecraft;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;

import java.io.File;
import java.io.IOException;

public final class CapitalismMinecraft extends JavaPlugin {
    public static CapitalismMinecraft instance;

    private final File ESf = new File(getDataFolder(), "/ExchangeItem.txt");

    public Wallet wallet = new Wallet();
    public Shop shop = new Shop();
    public Menu menu = new Menu();
    public Quest quest = new Quest();

    public void makeFile(File f) {
        if (!f.exists() || !f.isFile()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void InitConfig() {
        saveConfig();
        File cfile = new File(getDataFolder(), "config.yml");
        if (cfile.length() == 0) {
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }

    public void update() {
        Bukkit.getScheduler().runTaskTimer( this , new Runnable() {

            @Override
            public void run() {
                for(Player p : Bukkit.getServer().getOnlinePlayers())
                {
                    int money = wallet.Wlist.get(p.getName());
                    p.sendActionBar(Component.text(ChatColor.GOLD + "소지금: " + money + "🪙"));
                }
            }
        }, 4, 4);
    }

    public void updatePrice() {
        Bukkit.getScheduler().runTaskTimer( this , new Runnable() {

            @Override
            public void run() {
                shop.ResetPrice();
                shop.ResetData();
            }
        }, 432000, 432000); //6시간
    }
    

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Event(), this);
        instance = this;

        InitConfig();
        update();
        updatePrice();

        makeFile(ESf);
        shop.SaveES(ESf);
        shop.LoadES(ESf);
        
        wallet.Save();
        wallet.Load();

        menu.init_items();

        for (World w : Bukkit.getServer().getWorlds()) {
            w.setGameRule(GameRule.KEEP_INVENTORY, true);
            w.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
            w.setGameRule(GameRule.MOB_GRIEFING, false);
        }
    }

    @Override
    public void onDisable() {

    }
}
