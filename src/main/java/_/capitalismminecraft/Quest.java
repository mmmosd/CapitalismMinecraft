package _.capitalismminecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;

public class Quest {
    public class Questinfo {
        ItemStack item;
        int count;
        int money;

        public Questinfo(ItemStack item, int count, int money) {
            this.item = item;
            this.count = count;
            this.money = money;
        }
    }

    List<Questinfo> quests = new ArrayList<Questinfo>();
    List<UUID> completList = new ArrayList<UUID>();

    public void OpenQuestGUI(Player p) {
        if (completList.contains(p.getUniqueId())) {
            p.sendMessage(Component.text(ChatColor.RED + "퀘스트는 3개 중 하나만 완료할 수 있습니다. 퀘스트 목록이 변경될 때 까지 기다리세요."));
            return;
        }

        Inventory inventory = Bukkit.createInventory(p.getInventory().getHolder(), 27, Component.text("퀘스트"));

        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, CapitalismMinecraft.instance.menu.button_items.get(0));
        }

        inventory.setItem(11, quests.get(0).item);
        inventory.setItem(13, quests.get(1).item);
        inventory.setItem(15, quests.get(2).item);

//        inventory.setItem(26, CapitalismMinecraft.instance.menu.button_items.get(1));

        p.closeInventory();
        p.openInventory(inventory);
        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1, 1);
    }

    public void update_inventory() {
        for (Player p : CapitalismMinecraft.instance.getServer().getOnlinePlayers()) {
            if (p.getOpenInventory().title().equals(Component.text("퀘스트"))) {
                OpenQuestGUI(p);
            }
        }
    }

    public void complet_quest(Player p, Material category) {
        int player_item_count = 0;
        Questinfo info = null;

        for (Questinfo q : quests) {
            if (q.item.getType().equals(category)) {info = q; break;}
        }
        if (info == null) return;

        List<ItemStack> items = new ArrayList<ItemStack>();

        for (ItemStack stack : p.getInventory().getContents()) {
            if (stack != null && stack.getType().equals(category)) {
                player_item_count += stack.getAmount();

                for (int i = 0; i < stack.getAmount(); i++) {
                    ItemStack s = stack.clone();
                    s.setAmount(1);
                    items.add(s);
                }
            }
        }
     
        if (player_item_count >= info.count) {
            CapitalismMinecraft.instance.wallet.AddMoney(p, info.money);
            completList.add(p.getUniqueId());
            int num = 0;
            for (ItemStack stack : items) {
                if (num >= info.count) break;

                p.getInventory().removeItem(stack);
                num++;
            }
            p.sendMessage(Component.text(ChatColor.GREEN + "퀘스트를 완료했습니다!" + ChatColor.GOLD + " +" + info.money + "🪙"));
            p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
            p.closeInventory();
        }
        else {
            p.sendMessage(Component.text(ChatColor.RED + "아이템이 부족합니다."));
            p.playSound(p.getLocation(), Sound.BLOCK_CHAIN_PLACE, 1, 1);
        }
    }

    public void ResetQuest() {
        CapitalismMinecraft plugin = CapitalismMinecraft.instance;
        ItemStack item = null;
        List<Component> lores = new ArrayList<>();
        int count = 0, money = 0;

        quests.clear();
        completList.clear();

        count = (int)(Math.random()*32);
        money = (int)(Math.random()*190 + 10);
        item = new ItemStack(plugin.shop.button_items.get((int)(Math.random()*36)).getType());
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GREEN + "임무 : 이 아이템 " + count + "개 주기"));
        lores.add(Component.text(ChatColor.GOLD + "보상 : " + money + "🪙"));
        lores.add(Component.text(ChatColor.RED + "주의! 퀘스트는 3개 중 하나만 완료할 수 있습니다."));
        item.lore(lores);
        quests.add(new Questinfo(item, count, money));

        count = (int)(Math.random()*32);
        money = (int)(Math.random()*190 + 10);
        item = new ItemStack(plugin.shop.button_items.get((int)(Math.random()*36)).getType());
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GREEN + "임무 : 이 아이템 " + count + "개 주기"));
        lores.add(Component.text(ChatColor.GOLD + "보상 : " + money + "🪙"));
        lores.add(Component.text(ChatColor.RED + "주의! 퀘스트는 3개 중 하나만 완료할 수 있습니다."));
        item.lore(lores);
        quests.add(new Questinfo(item, count, money));

        count = (int)(Math.random()*32);
        money = (int)(Math.random()*190 + 10);
        item = new ItemStack(plugin.shop.button_items.get((int)(Math.random()*36)).getType());
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GREEN + "임무 : 이 아이템 " + count + "개 주기"));
        lores.add(Component.text(ChatColor.GOLD + "보상 : " + money + "🪙"));
        lores.add(Component.text(ChatColor.RED + "주의! 퀘스트는 3개 중 하나만 완료할 수 있습니다."));
        item.lore(lores);
        quests.add(new Questinfo(item, count, money));

        update_inventory();
    }
}
