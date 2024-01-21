package _.capitalismminecraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;

import java.io.File;

public final class CapitalismMinecraft extends JavaPlugin {
    private final File Wlistf = new File(getDataFolder(), "/Wlist.txt");
    public Wallet wallet = new Wallet();
    public Shop shop = new Shop();
    public Menu menu = new Menu();

    @Override
    public void onEnable() {
        Event.setPlugin(this);
        getServer().getPluginManager().registerEvents(new Event(), this);

        wallet.makeFile(Wlistf);
        wallet.Save(Wlistf, this);
        wallet.Load(Wlistf);

        Bukkit.getScheduler().runTaskTimer( this , new Runnable() {

            @Override
            public void run() {
                for(Player p : Bukkit.getServer().getOnlinePlayers())
                {
                    int money = wallet.Wlist.get(p.getUniqueId());
                    p.sendActionBar(Component.text(ChatColor.GREEN + "나의 자산: " + money + "$"));
                }
            }
        }, 4, 4);
    }

    @Override
    public void onDisable() {
        
    }
}
