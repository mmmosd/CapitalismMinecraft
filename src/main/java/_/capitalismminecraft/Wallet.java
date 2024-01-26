package _.capitalismminecraft;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;

import java.util.HashMap;


public class Wallet {
    public class SendInfo {
        Player target;
        int amount;

        public SendInfo(Player target, int amount) {
            this.target = target;
            this.amount = amount;
        }
    }

    public HashMap<String, Integer> Wlist = new HashMap<String, Integer>();
    public HashMap<String, SendInfo> Sending = new HashMap<String, SendInfo>();

    public void Save() {
        CapitalismMinecraft plugin = CapitalismMinecraft.instance;

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player p : plugin.getServer().getOnlinePlayers()) {
                    plugin.getConfig().set(p.getName(), Wlist.get(p.getName()));
                    plugin.saveConfig();
                }
            }
        }, 20 * 30, 20 * 30);
    }

    public void Load() {
        CapitalismMinecraft plugin = CapitalismMinecraft.instance;
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            String name = p.getName();
            int money = plugin.getConfig().getInt(name);

            Wlist.put(name, money);
        }

        for (OfflinePlayer p : plugin.getServer().getOfflinePlayers()) {
            String name = p.getName();
            int money = plugin.getConfig().getInt(name);

            Wlist.put(name, money);
        }
    }

    public void CreateWallet(Player p) {
        Wlist.putIfAbsent(p.getName(), -1000);
    }

    public void AddMoney(Player p, int amount) {
        Wlist.replace(p.getName(), Wlist.get(p.getName()) + amount);
    }

    public void SubMoney(Player p, int amount) {
        Wlist.replace(p.getName(), Wlist.get(p.getName()) - amount);
    }

    public void SendMoneyAtoB(Player a, Player b, int amount) {
        String A = a.getName(), B = b.getName();

        if (Wlist.containsKey(A) && Wlist.containsKey(B)) {
            if (Wlist.get(A) >= amount) {
                SubMoney(a, amount);
                AddMoney(b, amount);

                a.sendMessage(Component.text(ChatColor.LIGHT_PURPLE + b.getName() + ChatColor.WHITE + "님 에게 " + ChatColor.GREEN + amount + "🪙" + ChatColor.WHITE + "를 보냈습니다!"));
                b.sendMessage(Component.text(ChatColor.LIGHT_PURPLE + a.getName() + ChatColor.WHITE + "님 에게 " + ChatColor.GREEN + amount + "🪙" + ChatColor.WHITE + "를 받았습니다!"));

                a.playSound(a.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
                b.playSound(b.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
            }
            else {
                a.sendMessage(Component.text(ChatColor.RED + "돈이 부족합니다."));
                a.playSound(a.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            }
        }
        else if (!Wlist.containsKey(A)) {
            a.sendMessage(Component.text(ChatColor.RED + "지갑이 존재하지 않습니다. 지갑을 생성하십시오."));
            a.playSound(a.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
        }
        else if (!Wlist.containsKey(B)) {
            a.sendMessage(Component.text(ChatColor.RED + "받는 사람의 지갑이 존재하지 않습니다."));
            a.playSound(a.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
        }
    }
}
