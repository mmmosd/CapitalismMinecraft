package _.capitalismminecraft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;

public class Shop {
    public class ESItem {
        ItemStack item;
        int price;
        String owner;

        public ESItem(ItemStack item, int price, String owner) {
            this.item = item;
            this.price = price;
            this.owner = owner;
        }
    }

    public class PriceInfo {
        int min_price, max_price, now_price;

        public PriceInfo(int min_price, int max_price, int now_price) {
            this.min_price = min_price;
            this.max_price = max_price;
            this.now_price = now_price;
        }
    }

    List<ItemStack> button_items = new ArrayList<ItemStack>();
    HashMap<Material, PriceInfo> shop_items = new HashMap<Material, PriceInfo>();
    Stack<ESItem> ExchangeItem = new Stack<ESItem>();

    List<Material> SellData = new ArrayList<Material>();
    List<Material> BuyData = new ArrayList<Material>();

    public void SaveES(File f) {
        CapitalismMinecraft plugin = CapitalismMinecraft.instance;

        Bukkit.getScheduler().runTaskTimerAsynchronously(CapitalismMinecraft.instance, new Runnable() {

            @Override
            public void run() {
                try {
                    FileWriter writer = new FileWriter(f, false);

                    for (int i = 0; i < ExchangeItem.size(); i++) {
                        int num = i;
                        int price = ExchangeItem.get(i).price;
                        String owner = ExchangeItem.get(i).owner;

                        writer.write(num + "|" + price + "|" + owner + "\n"); // seller_price (txt)
                        plugin.getConfig().set("ES|" + num, ExchangeItem.get(i).item); // "ES|seller" : item (config)
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
                String[] str = fileLine.split("\\|");
                int num = Integer.parseInt(str[0]); // txt
                int price = Integer.parseInt(str[1]); // txt
                String owner = str[2]; // txt
                ItemStack stack =  plugin.getConfig().getItemStack("ES|" + num); // config

                ExchangeItem.push(new ESItem(stack, price, owner));
            }

            reader.close();
        } catch (FileNotFoundException e3) {
            e3.printStackTrace();
        } catch (IOException e4) {
            e4.printStackTrace();
        }
    }

     void init_items() {
        ItemStack item = null;
        ItemMeta meta = null;
        List<Component> lores = new ArrayList<>();

        // 원목 0~9 (1 골드)
        shop_items.put(Material.OAK_LOG, new PriceInfo(1, 2, 1));
        item = new ItemStack(Material.OAK_LOG);
        meta = item.getItemMeta();
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GOLD + "가격 : " + shop_items.get(Material.OAK_LOG) + "🪙"));
        meta.lore(lores);
        item.setItemMeta(meta);
        button_items.add(item);

        shop_items.put(Material.BIRCH_LOG, new PriceInfo(1, 2, 1));
        item = new ItemStack(Material.BIRCH_LOG);
        meta = item.getItemMeta();
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GOLD + "가격 : " + shop_items.get(Material.BIRCH_LOG) + "🪙"));
        meta.lore(lores);
        item.setItemMeta(meta);
        button_items.add(item);

        shop_items.put(Material.ACACIA_LOG, new PriceInfo(1, 2, 1));
        item = new ItemStack(Material.ACACIA_LOG);
        meta = item.getItemMeta();
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GOLD + "가격 : " + shop_items.get(Material.ACACIA_LOG) + "🪙"));
        meta.lore(lores);
        item.setItemMeta(meta);
        button_items.add(item);

        shop_items.put(Material.CHERRY_LOG, new PriceInfo(1, 2, 1));
        item = new ItemStack(Material.CHERRY_LOG);
        meta = item.getItemMeta();
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GOLD + "가격 : " + shop_items.get(Material.CHERRY_LOG) + "🪙"));
        meta.lore(lores);
        item.setItemMeta(meta);
        button_items.add(item);

        shop_items.put(Material.JUNGLE_LOG, new PriceInfo(1, 2, 1));
        item = new ItemStack(Material.JUNGLE_LOG);
        meta = item.getItemMeta();
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GOLD + "가격 : " + shop_items.get(Material.JUNGLE_LOG) + "🪙"));
        meta.lore(lores);
        item.setItemMeta(meta);
        button_items.add(item);

        shop_items.put(Material.SPRUCE_LOG, new PriceInfo(1, 2, 1));
        item = new ItemStack(Material.SPRUCE_LOG);
        meta = item.getItemMeta();
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GOLD + "가격 : " + shop_items.get(Material.SPRUCE_LOG) + "🪙"));
        meta.lore(lores);
        item.setItemMeta(meta);
        button_items.add(item);

        shop_items.put(Material.DARK_OAK_LOG, new PriceInfo(1, 2, 1));
        item = new ItemStack(Material.DARK_OAK_LOG);
        meta = item.getItemMeta();
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GOLD + "가격 : " + shop_items.get(Material.DARK_OAK_LOG) + "🪙"));
        meta.lore(lores);
        item.setItemMeta(meta);
        button_items.add(item);

        shop_items.put(Material.MANGROVE_LOG, new PriceInfo(1, 2, 1));
        item = new ItemStack(Material.MANGROVE_LOG);
        meta = item.getItemMeta();
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GOLD + "가격 : " + shop_items.get(Material.MANGROVE_LOG) + "🪙"));
        meta.lore(lores);
        item.setItemMeta(meta);
        button_items.add(item);

        shop_items.put(Material.WARPED_STEM, new PriceInfo(1, 2, 1));
        item = new ItemStack(Material.WARPED_STEM);
        meta = item.getItemMeta();
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GOLD + "가격 : " + shop_items.get(Material.WARPED_STEM) + "🪙"));
        meta.lore(lores);
        item.setItemMeta(meta);
        button_items.add(item);

        shop_items.put(Material.CRIMSON_STEM, new PriceInfo(1, 2, 1));
        item = new ItemStack(Material.CRIMSON_STEM);
        meta = item.getItemMeta();
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GOLD + "가격 : " + shop_items.get(Material.CRIMSON_STEM) + "🪙"));
        meta.lore(lores);
        item.setItemMeta(meta);
        button_items.add(item);

        //광물 10~20
        shop_items.put(Material.REDSTONE, new PriceInfo(1, 2, 1));
        item = new ItemStack(Material.REDSTONE);
        meta = item.getItemMeta();
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GOLD + "가격 : " + shop_items.get(Material.REDSTONE) + "🪙"));
        meta.lore(lores);
        item.setItemMeta(meta);
        button_items.add(item);

        shop_items.put(Material.LAPIS_LAZULI, new PriceInfo(1, 2, 1));
        item = new ItemStack(Material.LAPIS_LAZULI);
        meta = item.getItemMeta();
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GOLD + "가격 : " + shop_items.get(Material.LAPIS_LAZULI) + "🪙"));
        meta.lore(lores);
        item.setItemMeta(meta);
        button_items.add(item);

        shop_items.put(Material.COAL, new PriceInfo(1, 2, 1));
        item = new ItemStack(Material.COAL);
        meta = item.getItemMeta();
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GOLD + "가격 : " + shop_items.get(Material.COAL) + "🪙"));
        meta.lore(lores);
        item.setItemMeta(meta);
        button_items.add(item);

        shop_items.put(Material.QUARTZ, new PriceInfo(1, 2, 1));
        item = new ItemStack(Material.QUARTZ);
        meta = item.getItemMeta();
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GOLD + "가격 : " + shop_items.get(Material.QUARTZ) + "🪙"));
        meta.lore(lores);
        item.setItemMeta(meta);
        button_items.add(item);

        shop_items.put(Material.COPPER_INGOT, new PriceInfo(1, 4, 2));
        item = new ItemStack(Material.COPPER_INGOT);
        meta = item.getItemMeta();
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GOLD + "가격 : " + shop_items.get(Material.COPPER_INGOT) + "🪙"));
        meta.lore(lores);
        item.setItemMeta(meta);
        button_items.add(item);

        shop_items.put(Material.IRON_INGOT, new PriceInfo(1, 2, 9));
        item = new ItemStack(Material.IRON_INGOT);
        meta = item.getItemMeta();
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GOLD + "가격 : " + shop_items.get(Material.IRON_INGOT) + "🪙"));
        meta.lore(lores);
        item.setItemMeta(meta);
        button_items.add(item);

        shop_items.put(Material.GOLD_INGOT, new PriceInfo(1, 2, 1));
        item = new ItemStack(Material.GOLD_INGOT);
        meta = item.getItemMeta();
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GOLD + "가격 : " + shop_items.get(Material.GOLD_INGOT) + "🪙"));
        meta.lore(lores);
        item.setItemMeta(meta);
        button_items.add(item);

        shop_items.put(Material.DIAMOND, new PriceInfo(1, 2, 55));
        item = new ItemStack(Material.DIAMOND);
        meta = item.getItemMeta();
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GOLD + "가격 : " + shop_items.get(Material.DIAMOND) + "🪙"));
        meta.lore(lores);
        item.setItemMeta(meta);
        button_items.add(item);

        shop_items.put(Material.EMERALD, new PriceInfo(1, 2, 55));
        item = new ItemStack(Material.EMERALD);
        meta = item.getItemMeta();
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GOLD + "가격 : " + shop_items.get(Material.EMERALD) + "🪙"));
        meta.lore(lores);
        item.setItemMeta(meta);
        button_items.add(item);

        shop_items.put(Material.NETHERITE_SCRAP, new PriceInfo(1, 2, 110));
        item = new ItemStack(Material.NETHERITE_SCRAP);
        meta = item.getItemMeta();
        lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GOLD + "가격 : " + shop_items.get(Material.NETHERITE_SCRAP) + "🪙"));
        meta.lore(lores);
        item.setItemMeta(meta);
        button_items.add(item);

        //음식

    }

    public void OpenWoodShopGUI(Player p) {
        //원목만
        Inventory inventory = Bukkit.createInventory(p.getInventory().getHolder(), 27, Component.text("상점"));

        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, CapitalismMinecraft.instance.menu.button_items.get(0));
        }

        p.closeInventory();
        p.openInventory(inventory);
        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1, 1);
    }

    public void OpenMineralShopGUI(Player p) {
        //광물 블럭, 주괴, (구리, 철, 금 등 주괴로 만들 수 있는 것은 주괴로만, 섬손 금지)
        // 단, 네더라이트는 파편, 주괴 모두 가능
        // 광물은 구매 불가
        Inventory inventory = Bukkit.createInventory(p.getInventory().getHolder(), 27, Component.text("상점"));

        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, CapitalismMinecraft.instance.menu.button_items.get(0));
        }

        p.closeInventory();
        p.openInventory(inventory);
        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1, 1);
    }

    public void OpenFoodShopGUI(Player p) {
        //먹었을 때 효과가 부여되지 않는 것만 (황금사과류 제외)
        Inventory inventory = Bukkit.createInventory(p.getInventory().getHolder(), 27, Component.text("상점"));

        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, CapitalismMinecraft.instance.menu.button_items.get(0));
        }

        p.closeInventory();
        p.openInventory(inventory);
        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1, 1);
    }

    public void OpenExchangeShopGUI(Player p) {
        Inventory inventory = Bukkit.createInventory(p.getInventory().getHolder(), 27, Component.text("거래소"));

        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, CapitalismMinecraft.instance.menu.button_items.get(0));
        }

        for (int i = 0; i < ExchangeItem.size(); i++) {
            inventory.setItem(i, ExchangeItem.get(i).item);
        }

        inventory.setItem(26, CapitalismMinecraft.instance.menu.button_items.get(7));

        p.closeInventory();
        p.openInventory(inventory);
        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1, 1);
    }

    public void AddESItem(Player p, ItemStack stack, int price) {
        if (ExchangeItem.size() >= 18) p.sendMessage(Component.text(ChatColor.RED + "거래소가 꽉 찼습니다."));

        List<Component> lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GOLD + "가격 : " + price + "🪙"));
        stack.lore(lores);

        ExchangeItem.push(new ESItem(stack, price, p.getName()));
        CapitalismMinecraft.instance.getServer().sendMessage(Component.text(ChatColor.LIGHT_PURPLE + p.getName() + ChatColor.WHITE + "님이 아이템을 거래소에 등록했습니다!"));
    }

    public void BuyESItem(Player p, int num) {
        ESItem item = ExchangeItem.get(num);

        if (CapitalismMinecraft.instance.wallet.Wlist.get(p.getName()) >= item.price) {
            Player seller = CapitalismMinecraft.instance.getServer().getPlayer(item.owner);
            CapitalismMinecraft.instance.wallet.SubMoney(p, item.price);
            CapitalismMinecraft.instance.wallet.AddMoney(seller, item.price);

            ItemStack stack = item.item;
            stack.lore(new ArrayList<>());

            p.getInventory().addItem(stack);
            p.sendMessage(Component.text(ChatColor.GREEN + "성공적으로 구매하였습니다."));
            seller.sendMessage(Component.text(ChatColor.LIGHT_PURPLE + p.getName() + ChatColor.GREEN +"님이 당신의 [" + ChatColor.GOLD + item.item.getItemMeta().getDisplayName() + ChatColor.GREEN + "] 을/를 구매하였습니다!"));
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            seller.playSound(seller.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

            CapitalismMinecraft.instance.getConfig().set("ES|" + num, null);
            ExchangeItem.remove(num);
        }
        else {
            p.sendMessage(Component.text(ChatColor.RED + "돈이 부족합니다."));
        }
    }

    public void Sell(Material category, int price) {
        
    }

    public void Buy(Material category, int price) {
        
    }

    public void ResetData() {
        SellData.clear();
        BuyData.clear();
    }

    public void ResetPrice() {
        ResetData();
    }

    public int Set_items_price(int before_price) {
        int result_price = before_price;

        return result_price;
    }
}