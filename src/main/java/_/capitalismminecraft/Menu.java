package _.capitalismminecraft;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;

public class Menu {
    List<ItemStack> button_items = new ArrayList<ItemStack>();

    void init_items() {
        // 0. 클릭 금지 구역
        // 1. 목재 상점
        // 2. 광물 상점
        // 3. 농수산물 상점
        // 4. 물약, 인챈트 상점
        // 5. 거래소
        // 6. 송금

        ItemStack item = null;
        ItemMeta meta = null;

        item = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        meta = item.getItemMeta();
        meta.displayName(Component.text(""));
        item.setItemMeta(meta);
        button_items.add(item);

        //1~6 메인 메뉴
        item = new ItemStack(Material.OAK_LOG);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.YELLOW + "목재 상점"));
        item.setItemMeta(meta);
        button_items.add(item);

        item = new ItemStack(Material.DIAMOND);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.AQUA + "광물 상점"));
        item.setItemMeta(meta);
        button_items.add(item);

        item = new ItemStack(Material.WHEAT);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.GOLD + "농산물&수산물 상점"));
        item.setItemMeta(meta);
        button_items.add(item);

        item = new ItemStack(Material.MOJANG_BANNER_PATTERN);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.GREEN + "거래소"));
        item.setItemMeta(meta);
        button_items.add(item);

        item = new ItemStack(Material.BOOK);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.YELLOW + "퀘스트"));
        item.setItemMeta(meta);
        button_items.add(item);

        item = new ItemStack(Material.LIGHTNING_ROD);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.GREEN + "송금하기"));
        item.setItemMeta(meta);
        button_items.add(item);

        //7 거래소 등록
        item = new ItemStack(Material.LIGHTNING_ROD);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.GREEN + "거래소 등록 방법"));
        item.setItemMeta(meta);
        button_items.add(item);
    }

    public void OpenPlayerMenu(Player p) {
        Inventory inventory = Bukkit.createInventory(p.getInventory().getHolder(), 27, Component.text("메뉴"));

        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, button_items.get(0));
        }

        inventory.setItem(2, button_items.get(1));
        inventory.setItem(4, button_items.get(2));
        inventory.setItem(6, button_items.get(3));
        inventory.setItem(12, button_items.get(4));
        inventory.setItem(14, button_items.get(5));
        inventory.setItem(26, button_items.get(6));

        p.closeInventory();
        p.openInventory(inventory);
        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1, 1);
    }

    public void OpenSendMoneyMenu(Player p) {
        Inventory inventory = Bukkit.createInventory(p.getInventory().getHolder(), 27, Component.text("송금하기"));

        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, button_items.get(0));
        }

        int num = 0;
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        for (Player online_p : CapitalismMinecraft.instance.getServer().getOnlinePlayers()) {
            if (online_p.getUniqueId().equals(p.getUniqueId())) continue;

            skullMeta.setOwningPlayer(online_p);
            skullMeta.displayName(Component.text(online_p.getName()));
            item.setItemMeta(skullMeta);
            
            inventory.setItem(num++, item);
        }

        p.closeInventory();
        p.openInventory(inventory);
        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1, 1);
    }
}
