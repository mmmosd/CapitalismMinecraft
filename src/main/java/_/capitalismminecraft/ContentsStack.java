package _.capitalismminecraft;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
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
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ContentsStack {
    public ItemStack TpPaper() {
        NamespacedKey key = new NamespacedKey(CapitalismMinecraft.getPlugins(), "tp");
        ItemStack item = new ItemStack(Material.MOJANG_BANNER_PATTERN);
        ItemMeta im = item.getItemMeta();
        im.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        im.displayName(Component.text(ChatColor.DARK_AQUA + "텔레포트 이용권"));
        im.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
        item.setItemMeta(im);

        return item;
    }

    public void OpenTpGUI(Player p) {
        Inventory inventory = Bukkit.createInventory(p.getInventory().getHolder(), 27, Component.text("텔레포트"));

        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, CapitalismMinecraft.instance.menu.button_items.get(0));
        }

        int num = 0;

        ItemStack item = new ItemStack(Material.CONDUIT);
        ItemMeta im = item.getItemMeta();
        im.displayName(Component.text(ChatColor.LIGHT_PURPLE + "스폰(0, 0)" + ChatColor.AQUA + "으로 텔레포트"));
        item.setItemMeta(im);
        inventory.setItem(num++, item);

        NamespacedKey key = new NamespacedKey(CapitalismMinecraft.getPlugins(), "Name");
        for (Player online_p : CapitalismMinecraft.instance.getServer().getOnlinePlayers()) {
            if (online_p.getUniqueId().equals(p.getUniqueId())) continue;

            item = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) item.getItemMeta();

            skullMeta.setOwningPlayer(online_p);
            skullMeta.displayName(Component.text(ChatColor.LIGHT_PURPLE + online_p.getName() + ChatColor.AQUA + "님에게 텔레포트"));
            item.setItemMeta(skullMeta);

            if (!item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) { // 아이템에 코드가 없을 때
                im = item.getItemMeta();
                im.getPersistentDataContainer().set(key, PersistentDataType.STRING, online_p.getName());
                item.setItemMeta(im);
            }

            inventory.setItem(num++, item);
        }

        p.closeInventory();
        p.openInventory(inventory);
        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1, 1);
    }
}
