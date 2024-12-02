package _.capitalismminecraft;

import _.capitalismminecraft.Skill.CoolDown;
import _.capitalismminecraft.Commands.bp;
import _.capitalismminecraft.Commands.bpTab;
import _.capitalismminecraft.Data.CreateData;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public final class CapitalismMinecraft extends JavaPlugin {
    public static CapitalismMinecraft instance;

    private final File ESf = new File(getDataFolder(), "/ExchangeItem.txt");
    private final File Pricef = new File(getDataFolder(), "/Price.txt");

    public Wallet wallet = new Wallet();
    public Shop shop = new Shop();
    public Menu menu = new Menu();
    public Quest quest = new Quest();
    public Skill skill = new Skill();

    public static NamespacedKey skillkey;

    CommandSender consol = Bukkit.getConsoleSender();

    public static CreateData createData;
    public static String bb = ColorChat("&b&lBuild&7&lProtector&8:: &r");
    public static HashMap<Player, String> optiongui;
    public static HashMap<Player, String> addgui;
    public static HashMap<Player, ArmorStand> armorstandData;
    public static HashMap<Player, Location> Pos1;
    public static HashMap<Player, Location> Pos2;
    public static NamespacedKey key;

    public static String ColorChat(String msg) {
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static Plugin getPlugins() {
        return instance;
    }

    public static boolean isNumberExeption(String number) {
        try {
            Integer var1 = Integer.parseInt(number);
            return false;
        } catch (NumberFormatException var2) {
            return true;
        }
    }

    public static boolean isFullInventory(Player p) {
        return p.getInventory().firstEmpty() == -1;
    }

    public static NamespacedKey getKey() {
        return key;
    }

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

                    for (ItemStack stack : p.getInventory().getContents()) {
                        if (stack == null) continue;

                        if (p.getCooldown(stack.getType()) > 0) {
                            skill.cooldown.get(p.getUniqueId()).add(skill.new CoolDown(stack.getType(), p.getCooldown(stack.getType())));
                        }
                    }
                }
            }
        }, 1, 1);
    }

    public void updatePrice() {
        Bukkit.getScheduler().runTaskTimer( this , new Runnable() {

            @Override
            public void run() {
                shop.ResetPrice();
                quest.ResetQuest();

                Bukkit.getServer().sendMessage(Component.text(ChatColor.YELLOW + "상점 가격이 변동되었습니다!"));
                Bukkit.getServer().sendMessage(Component.text(ChatColor.GREEN + "퀘스트 목록이 변경되었습니다!"));
            }
        }, 288000, 288000); //6시간 = 432000tick
    }
    

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Event(), this);
        instance = this;

        this.consol.sendMessage(ColorChat("&2Build&7Protector:: &f플러그인이 활성화되었습니다."));
        createData = new CreateData();
        createData.CreateNewDataFile();
        createData.CreatePlayerDataFile();
        optiongui = new HashMap<>();
        addgui = new HashMap<>();
        armorstandData = new HashMap<>();
        Pos1 = new HashMap<>();
        Pos2 = new HashMap<>();
        key = new NamespacedKey(this, "CapitalismMinecraft");
        skillkey = new NamespacedKey(this, "Skill");
        this.getCommand("bp").setExecutor(new bp(this));
        this.getCommand("bp").setTabCompleter(new bpTab(this));

        InitConfig();
        update();
        updatePrice();

        menu.init_items();
        shop.init_items();
        quest.ResetQuest();

        makeFile(ESf);
        shop.LoadES(ESf);
        shop.SaveES(ESf);

        makeFile(Pricef);
        shop.LoadPrice(Pricef);
        shop.SavePrice(Pricef);

        skill.LoadLevel();
        skill.SaveLevel();

        wallet.Load();
        wallet.Save();

        for (World w : Bukkit.getServer().getWorlds()) {
            w.setGameRule(GameRule.KEEP_INVENTORY, true);
            w.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
            w.setGameRule(GameRule.MOB_GRIEFING, false);
            w.setSpawnLocation(0, 0, 0);

            if (w.getEnvironment().equals(World.Environment.NORMAL) || w.getEnvironment().equals(World.Environment.CUSTOM)) {
                Location loc = w.getHighestBlockAt(0, 0).getLocation().add(0, -1, 0);

                menu.MakeArmorStand(loc.add(-6, 0, 7), 1);
                menu.MakeArmorStand(loc.add(4, 0, 2), 2);
                menu.MakeArmorStand(loc.add(4, 0, 0), 3);
                menu.MakeArmorStand(loc.add(4, 0, -2), 4);
                menu.MakeArmorStand(loc.add(-10, 0, -4), 5);
                menu.MakeArmorStand(loc.add(4, 0, 2), 6);
                menu.MakeArmorStand(loc.add(4, 0, -2), 7);
            }
        }
    }

    @Override
    public void onDisable() {
        this.consol.sendMessage(ColorChat("&2Build&7Protector:: &f플러그인이 비활성화되었습니다."));
        instance = null;
        Iterator var1 = optiongui.keySet().iterator();

        Player p;
        while(var1.hasNext()) {
            p = (Player)var1.next();
            optiongui.remove(p);
            p.closeInventory();
        }

        var1 = addgui.keySet().iterator();

        while(var1.hasNext()) {
            p = (Player)var1.next();
            addgui.remove(p);
            p.closeInventory();
        }
    }
}
