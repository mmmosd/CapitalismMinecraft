package _.capitalismminecraft;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.kyori.adventure.text.Component;

public class Quest {
    public class Questinfo {
        Material item;
        int count;
        int money;
    }

    public void OpenQuestGUI(Player p) {
        Inventory inventory = Bukkit.createInventory(p.getInventory().getHolder(), 27, Component.text("퀘스트"));

        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, CapitalismMinecraft.instance.menu.button_items.get(0));
        }

        p.closeInventory();
        p.openInventory(inventory);
        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1, 1);
    }

    public void ResetQuest() {

    }
}
