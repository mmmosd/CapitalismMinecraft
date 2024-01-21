package _.capitalismminecraft;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;


public class Wallet {
    public HashMap<UUID, Integer> Wlist = new HashMap<UUID, Integer>();

    public void Save(File f, CapitalismMinecraft plugin) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {

            @Override
            public void run() {
                try {
                    FileWriter writer = new FileWriter(f, false);
                    for(UUID uuid : Wlist.keySet()){
                        writer.write(uuid.toString() + "|" + Wlist.get(uuid) + "\n");
                    }
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 20 * 30, 20 * 30);
    }

    public void Load(File f) {
        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
                String fileLine = null;
                while ((fileLine = reader.readLine()) != null) {

                    UUID uuid = UUID.fromString(fileLine.split("\\|")[0]);
                    String str = fileLine.split("\\|")[1];

                    Wlist.put(uuid, Integer.parseInt(str));
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e3) {
            e3.printStackTrace();
        } catch (IOException e4) {
            e4.printStackTrace();
        }
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

    public void CreateWallet(Player p) {
        Wlist.putIfAbsent(p.getUniqueId(), 0);
    }

    public void AddMoney(Player p, int amount) {
        Wlist.replace(p.getUniqueId(), Wlist.get(p.getUniqueId()) + amount);
    }

    public void SubMoney(Player p, int amount) {
        Wlist.replace(p.getUniqueId(), Wlist.get(p.getUniqueId()) - amount);
    }

    public void SendMoneyAtoB(Player a, Player b, int amount) {
        UUID A = a.getUniqueId(), B = b.getUniqueId();

        if (Wlist.containsKey(A) && Wlist.containsKey(B)) {
            if (Wlist.get(A) >= amount) {
                SubMoney(a, amount);
                AddMoney(b, amount);

                a.sendMessage(Component.text(ChatColor.LIGHT_PURPLE + b.getName() + ChatColor.WHITE + "님 에게 " + ChatColor.GREEN + amount + "$" + ChatColor.WHITE + "를 보냈습니다!"));
                b.sendMessage(Component.text(ChatColor.LIGHT_PURPLE + a.getName() + ChatColor.WHITE + "님 에게 " + ChatColor.GREEN + amount + "$" + ChatColor.WHITE + "를 받았습니다!"));

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
