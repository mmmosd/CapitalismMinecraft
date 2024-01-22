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

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;

public class Menu {
    CapitalismMinecraft plugin = CapitalismMinecraft.getInstance();
    
    List<ItemStack> items = new ArrayList<ItemStack>();

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
        items.add(item);

        item = new ItemStack(Material.OAK_LOG);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.YELLOW + "목재 상점"));
        item.setItemMeta(meta);
        items.add(item);

        item = new ItemStack(Material.DIAMOND);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.AQUA + "광물 상점"));
        item.setItemMeta(meta);
        items.add(item);

        item = new ItemStack(Material.WHEAT);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.GOLD + "농산물&수산물 상점"));
        item.setItemMeta(meta);
        items.add(item);

        item = new ItemStack(Material.POTION);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.LIGHT_PURPLE + "물약&인챈트 상점"));
        item.setItemMeta(meta);
        items.add(item);

        item = new ItemStack(Material.MOJANG_BANNER_PATTERN);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.YELLOW + "거래소"));
        item.setItemMeta(meta);
        items.add(item);

        item = new ItemStack(Material.LIGHTNING_ROD);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.YELLOW + "송금"));
        item.setItemMeta(meta);
        items.add(item);
    }

    public void OpenPlayerMenu(Player p) {
        Inventory inventory = Bukkit.createInventory(p.getInventory().getHolder(), 27, Component.text("MENU"));

        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, items.get(0));
        }

        inventory.setItem(0, items.get(1));

        inventory.setItem(1, items.get(2));

        inventory.setItem(2, items.get(3));

        inventory.setItem(3, items.get(4));

        inventory.setItem(7, items.get(5));

        inventory.setItem(8, items.get(6));

        p.openInventory(inventory);
        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1, 1);
    }
}
