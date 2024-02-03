package _.capitalismminecraft;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.persistence.PersistentDataType;

public class Menu {
    public List<ItemStack> button_items = new ArrayList<ItemStack>();

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

        // 빈 공간 0
        item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        meta = item.getItemMeta();
        meta.displayName(Component.text(""));
        item.setItemMeta(meta);
        item.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        button_items.add(item);

        //뒤로가기 1
        item = new ItemStack(Material.WRITABLE_BOOK);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.LIGHT_PURPLE + "뒤로가기"));
        item.setItemMeta(meta);
        item.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        button_items.add(item);

        //2~9 메인 메뉴
        item = new ItemStack(Material.OAK_LOG);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.YELLOW + "목재 상점"));
        item.setItemMeta(meta);
        item.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        button_items.add(item);

        item = new ItemStack(Material.DIAMOND);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.DARK_AQUA + "광물 상점"));
        item.setItemMeta(meta);
        item.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        button_items.add(item);

        item = new ItemStack(Material.WHEAT);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.GOLD + "농수산물 상점"));
        item.setItemMeta(meta);
        item.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        button_items.add(item);

        item = new ItemStack(Material.END_CRYSTAL);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.LIGHT_PURPLE + "컨텐츠 상점"));
        item.setItemMeta(meta);
        item.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        button_items.add(item);

        item = new ItemStack(Material.MOJANG_BANNER_PATTERN);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.GOLD + "거래소"));
        item.setItemMeta(meta);
        item.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        button_items.add(item);

        item = new ItemStack(Material.BOOK);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.YELLOW + "퀘스트"));
        item.setItemMeta(meta);
        item.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        button_items.add(item);

        item = new ItemStack(Material.ENCHANTING_TABLE);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.AQUA + "강화"));
        item.setItemMeta(meta);
        item.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        button_items.add(item);

        item = new ItemStack(Material.LIGHTNING_ROD);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.GREEN + "송금하기"));
        item.setItemMeta(meta);
        item.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        button_items.add(item);

        // 거래소 등록 10
        item = new ItemStack(Material.LIGHTNING_ROD);
        meta = item.getItemMeta();
        meta.displayName(Component.text(ChatColor.GREEN + "거래소 등록 방법"));
        item.setItemMeta(meta);
        item.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        button_items.add(item);
    }

    public void OpenPlayerMenu(Player p) {
        Inventory inventory = Bukkit.createInventory(p.getInventory().getHolder(), 27, Component.text("메뉴"));

        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, button_items.get(0));
        }

        inventory.setItem(1, button_items.get(2));
        inventory.setItem(3, button_items.get(3));
        inventory.setItem(5, button_items.get(4));
        inventory.setItem(7, button_items.get(5));
        inventory.setItem(11, button_items.get(6));
        inventory.setItem(13, button_items.get(7));
        inventory.setItem(15, button_items.get(8));
        inventory.setItem(26, button_items.get(9));

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
        NamespacedKey key = new NamespacedKey(CapitalismMinecraft.getPlugins(), "Name");
        for (Player online_p : CapitalismMinecraft.instance.getServer().getOnlinePlayers()) {
            if (online_p.getUniqueId().equals(p.getUniqueId())) continue;

            ItemStack item = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) item.getItemMeta();

            skullMeta.setOwningPlayer(online_p);
            skullMeta.displayName(Component.text(online_p.getName()));
            item.setItemMeta(skullMeta);

            if (!item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) { // 아이템에 코드가 없을 때
                ItemMeta im = item.getItemMeta();
                im.getPersistentDataContainer().set(key, PersistentDataType.STRING, online_p.getName());
                item.setItemMeta(im);
            }
            
            inventory.setItem(num++, item);
        }

        inventory.setItem(26, CapitalismMinecraft.instance.menu.button_items.get(1));

        p.closeInventory();
        p.openInventory(inventory);
        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1, 1);
    }
}
