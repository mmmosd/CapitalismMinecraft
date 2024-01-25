package _.capitalismminecraft;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;

public class Shop {
    HashMap<Material, Integer> Item = new HashMap<Material, Integer>();
    HashMap<Material, Integer> ExchangeItem = new HashMap<Material, Integer>();

    HashMap<Material, Integer> SellData = new HashMap<Material, Integer>();
    HashMap<Material, Integer> BuyData = new HashMap<Material, Integer>();

    public void OpenWoodShopGUI() {
        //원목만
    }

    public void OpenMineralShopGUI() {
        //광물 블럭, 주괴, (구리, 철, 금 등 주괴로 만들 수 있는 것은 주괴로만, 섬손 금지)
        // 단, 네더라이트는 파편, 주괴 모두 가능
    }

    public void OpenFoodShopGUI() {
        //먹었을 때 효과가 부여되지 않는 것만 (황금사과류 제외)
    }

    public void OpenExchangeShopGUI(Player p) {
        // Inventory inventory = Bukkit.createInventory(p.getInventory().getHolder(), 27, Component.text("거래소"));

        // ItemStack item = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        // ItemMeta meta = item.getItemMeta();
        // meta.displayName(Component.text(""));
        // item.setItemMeta(meta);
        // items.add(item);
        // for (int i = 0; i < 27; i++) {
        //     inventory.setItem(i, items.get(0));
        // }



        // p.closeInventory();
        // p.openInventory(inventory);
        // p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1, 1);
    }

    public void ResetData() {
        SellData.clear();
        BuyData.clear();
    }

    public void Sell(Material category, int price) {
        if (SellData.get(category) != null) {
            SellData.put(category, SellData.get(category) + price);
        }
        else {
            SellData.put(category, price);
        }
    }

    public void Buy(Material category, int price) {
        if (BuyData.get(category) != null) {
            BuyData.put(category, BuyData.get(category) + price);
        }
        else {
            BuyData.put(category, price);
        }
    }



    public void ResetPrice() {

    }

    public int Set_items_price(int before_price) {
        int result_price = before_price;
        return result_price;
    }
}
