package _.capitalismminecraft;

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
    public void OpenPlayerMenu(Player p) {
        Inventory inventory = Bukkit.createInventory(p.getInventory().getHolder(), 27, Component.text("MENU"));
        ItemStack item = null;
        ItemMeta meta = null;

        item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.RED + "MENU"));
        item.setItemMeta(meta);
        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, item);
        }

        item = new ItemStack(Material.OAK_LOG);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.YELLOW + "목재 상점"));
        item.setItemMeta(meta);
        inventory.setItem(0, item);

        item = new ItemStack(Material.DIAMOND);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.AQUA + "광물 상점"));
        item.setItemMeta(meta);
        inventory.setItem(1, item);

        item = new ItemStack(Material.WHEAT);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.GOLD + "농산물&수산물 상점"));
        item.setItemMeta(meta);
        inventory.setItem(2, item);

        item = new ItemStack(Material.POTION);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.LIGHT_PURPLE + "물약&인챈트 상점"));
        item.setItemMeta(meta);
        inventory.setItem(3, item);

        item = new ItemStack(Material.MOJANG_BANNER_PATTERN);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.YELLOW + "거래소"));
        item.setItemMeta(meta);
        inventory.setItem(7, item);

        item = new ItemStack(Material.LIGHTNING_ROD);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.YELLOW + "송금"));
        item.setItemMeta(meta);
        inventory.setItem(8, item);

        p.openInventory(inventory);
        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1, 1);
    }
}
