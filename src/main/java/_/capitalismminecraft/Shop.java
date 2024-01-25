package _.capitalismminecraft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.chat.hover.content.Item;

public class Shop {
    public class ESItem {
        ItemStack item;
        int price;

        public ESItem(ItemStack item, int price) {
            this.item = item;
            this.price = price;
        }
    }

    HashMap<Material, Integer> Item = new HashMap<Material, Integer>();
    HashMap<String, ESItem> ExchangeItem = new HashMap<String, ESItem>();

    HashMap<Material, Integer> SellData = new HashMap<Material, Integer>();
    HashMap<Material, Integer> BuyData = new HashMap<Material, Integer>();

    public void SaveES(File f) {
        CapitalismMinecraft plugin = CapitalismMinecraft.instance;

        Bukkit.getScheduler().runTaskTimerAsynchronously(CapitalismMinecraft.instance, new Runnable() {

            @Override
            public void run() {
                try {
                    FileWriter writer = new FileWriter(f, false);

                    for (Entry<String, ESItem> entry : ExchangeItem.entrySet()) {
                        String seller = entry.getKey();
                        int price = entry.getValue().price;

                        writer.write(seller + "_" + price + "\n"); // seller_price (txt)
                        plugin.getConfig().set("ES_" + seller, entry.getValue().item); // "ES_seller" : item (config)
                    }

                    writer.close();
                    plugin.saveConfig();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 20 * 30, 20 * 30);

    }

    public void LoadES(File f) {
        try {
            CapitalismMinecraft plugin = CapitalismMinecraft.instance;
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String fileLine = null;

            while ((fileLine = reader.readLine()) != null) {
                String[] str = fileLine.split("\\_");
                String seller = str[0]; // txt
                int price = Integer.parseInt(str[1]); // txt
                ItemStack stack =  plugin.getConfig().getItemStack("ES_" + seller); // config

                ExchangeItem.putIfAbsent(seller, new ESItem(stack, price));
            }

            reader.close();
        } catch (FileNotFoundException e3) {
            e3.printStackTrace();
        } catch (IOException e4) {
            e4.printStackTrace();
        }
    }

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
        Inventory inventory = Bukkit.createInventory(p.getInventory().getHolder(), 27, Component.text("거래소"));

        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, CapitalismMinecraft.instance.menu.items.get(0));
        }

        p.closeInventory();
        p.openInventory(inventory);
        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1, 1);
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
