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
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.hover.content.Item;

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
        int before_count, now_count;

        public PriceInfo(int min_price, int max_price, int now_price, int before_count, int now_count) {
            this.min_price = min_price;
            this.max_price = max_price;
            this.now_price = now_price;
            this.before_count = before_count;
            this.now_count = now_count;
        }

        public int getBuyPrice() {
            return ((int) (now_price + (now_price*(20/100)) + 4));
        }
    }

    List<ItemStack> button_items = new ArrayList<ItemStack>();
    HashMap<Material, PriceInfo> shop_items = new HashMap<Material, PriceInfo>();
    Stack<ESItem> ExchangeItem = new Stack<ESItem>();

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

    public void SavePrice(File f) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(CapitalismMinecraft.instance, new Runnable() {

            @Override
            public void run() {
                try {
                    FileWriter writer = new FileWriter(f, false);

                    for (Entry<Material, PriceInfo> set : shop_items.entrySet()) {
                        writer.write(set.getKey().name() + "|" + set.getValue().now_price + "|" + set.getValue().before_count + "|" + set.getValue().now_count + "\n"); // seller_price (txt)
                    }

                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 20 * 30, 20 * 30);
    }

    public void LoadPrice(File f) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String fileLine = null;

            while ((fileLine = reader.readLine()) != null) {
                String[] str = fileLine.split("\\|");
                Material mat = Material.getMaterial(str[0]); // txt
                int price = Integer.parseInt(str[1]); // txt
                int beforeC = Integer.parseInt(str[2]); // txt
                int afterC = Integer.parseInt(str[3]); // txt

                shop_items.replace(mat, new PriceInfo(shop_items.get(mat).min_price, shop_items.get(mat).max_price, price, beforeC, afterC));
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

        // 원목 0~9 (1 골드)
        shop_items.put(Material.OAK_LOG, new PriceInfo(1, 2, 1, 0, 0));
        item = new ItemStack(Material.OAK_LOG);
        button_items.add(item);

        shop_items.put(Material.BIRCH_LOG, new PriceInfo(1, 2, 1, 0, 0));
        item = new ItemStack(Material.BIRCH_LOG);
        button_items.add(item);

        shop_items.put(Material.ACACIA_LOG, new PriceInfo(1, 2, 1, 0, 0));
        item = new ItemStack(Material.ACACIA_LOG);
        button_items.add(item);

        shop_items.put(Material.CHERRY_LOG, new PriceInfo(1, 2, 1, 0, 0));
        item = new ItemStack(Material.CHERRY_LOG);
        button_items.add(item);

        shop_items.put(Material.JUNGLE_LOG, new PriceInfo(1, 2, 1, 0, 0));
        item = new ItemStack(Material.JUNGLE_LOG);
        button_items.add(item);

        shop_items.put(Material.SPRUCE_LOG, new PriceInfo(1, 2, 1, 0, 0));
        item = new ItemStack(Material.SPRUCE_LOG);
        button_items.add(item);

        shop_items.put(Material.DARK_OAK_LOG, new PriceInfo(1, 2, 1, 0, 0));
        item = new ItemStack(Material.DARK_OAK_LOG);
        button_items.add(item);

        shop_items.put(Material.MANGROVE_LOG, new PriceInfo(1, 2, 1, 0, 0));
        item = new ItemStack(Material.MANGROVE_LOG);
        button_items.add(item);

        shop_items.put(Material.WARPED_STEM, new PriceInfo(1, 2, 1, 0, 0));
        item = new ItemStack(Material.WARPED_STEM);
        button_items.add(item);

        shop_items.put(Material.CRIMSON_STEM, new PriceInfo(1, 2, 1, 0, 0));
        item = new ItemStack(Material.CRIMSON_STEM);
        button_items.add(item);

        //광물 10~19
        shop_items.put(Material.REDSTONE, new PriceInfo(1, 2, 1, 0, 0));
        item = new ItemStack(Material.REDSTONE);
        button_items.add(item);

        shop_items.put(Material.LAPIS_LAZULI, new PriceInfo(1, 2, 1, 0, 0));
        item = new ItemStack(Material.LAPIS_LAZULI);
        button_items.add(item);

        shop_items.put(Material.COAL, new PriceInfo(1, 2, 1, 0, 0));
        item = new ItemStack(Material.COAL);
        button_items.add(item);

        shop_items.put(Material.QUARTZ, new PriceInfo(1, 2, 1, 0, 0));
        item = new ItemStack(Material.QUARTZ);
        button_items.add(item);

        shop_items.put(Material.COPPER_INGOT, new PriceInfo(1, 4, 2, 0, 0));
        item = new ItemStack(Material.COPPER_INGOT);
        button_items.add(item);

        shop_items.put(Material.IRON_INGOT, new PriceInfo(6, 12, 9, 0, 0));
        item = new ItemStack(Material.IRON_INGOT);
        button_items.add(item);

        shop_items.put(Material.GOLD_INGOT, new PriceInfo(6, 12, 9, 0, 0));
        item = new ItemStack(Material.GOLD_INGOT);
        button_items.add(item);

        shop_items.put(Material.DIAMOND, new PriceInfo(40, 70, 55, 0, 0));
        item = new ItemStack(Material.DIAMOND);
        button_items.add(item);

        shop_items.put(Material.EMERALD, new PriceInfo(40, 70, 55, 0, 0));
        item = new ItemStack(Material.EMERALD);
        button_items.add(item);

        shop_items.put(Material.NETHERITE_SCRAP, new PriceInfo(100, 300, 200, 0, 0));
        item = new ItemStack(Material.NETHERITE_SCRAP);
        button_items.add(item);

        //음식 20~33
        shop_items.put(Material.BREAD, new PriceInfo(1, 4, 2, 0, 0));
        item = new ItemStack(Material.BREAD);
        button_items.add(item);

        shop_items.put(Material.APPLE, new PriceInfo(1, 4, 2, 0, 0));
        item = new ItemStack(Material.APPLE);
        button_items.add(item);

        shop_items.put(Material.GOLDEN_APPLE, new PriceInfo(40, 70, 55, 0, 0));
        item = new ItemStack(Material.GOLDEN_APPLE);
        button_items.add(item);

        shop_items.put(Material.ENCHANTED_GOLDEN_APPLE, new PriceInfo(100, 300, 200, 0, 0));
        item = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
        button_items.add(item);

        shop_items.put(Material.BAKED_POTATO, new PriceInfo(1, 6, 3, 0, 0));
        item = new ItemStack(Material.BAKED_POTATO);
        button_items.add(item);

        shop_items.put(Material.BEETROOT, new PriceInfo(1, 4, 2, 0, 0));
        item = new ItemStack(Material.BEETROOT);
        button_items.add(item);

        shop_items.put(Material.CARROT, new PriceInfo(1, 4, 2, 0, 0));
        item = new ItemStack(Material.CARROT);
        button_items.add(item);

        shop_items.put(Material.GOLDEN_CARROT, new PriceInfo(20, 40, 30, 0, 0));
        item = new ItemStack(Material.GOLDEN_CARROT);
        button_items.add(item);

        shop_items.put(Material.COOKED_BEEF, new PriceInfo(2, 6, 4, 0, 0));
        item = new ItemStack(Material.COOKED_BEEF);
        button_items.add(item);

        shop_items.put(Material.COOKED_PORKCHOP, new PriceInfo(2, 6, 4, 0, 0));
        item = new ItemStack(Material.COOKED_PORKCHOP);
        button_items.add(item);

        shop_items.put(Material.COOKED_MUTTON, new PriceInfo(2, 6, 4, 0, 0));
        item = new ItemStack(Material.COOKED_MUTTON);
        button_items.add(item);

        shop_items.put(Material.COOKED_CHICKEN, new PriceInfo(2, 6, 4, 0, 0));
        item = new ItemStack(Material.COOKED_CHICKEN);
        button_items.add(item);

        shop_items.put(Material.COOKED_COD, new PriceInfo(4, 8, 6, 0, 0));
        item = new ItemStack(Material.COOKED_COD);
        button_items.add(item);

        shop_items.put(Material.COOKED_SALMON, new PriceInfo(4, 8, 6, 0, 0));
        item = new ItemStack(Material.COOKED_SALMON);
        button_items.add(item);
    }

    public void update_inventory() {
        for (Player p : CapitalismMinecraft.instance.getServer().getOnlinePlayers()) {
            if (p.getOpenInventory().title().equals(Component.text("나무 상점"))) {
                OpenWoodShopGUI(p);
            }

            if (p.getOpenInventory().title().equals(Component.text("광물 상점"))) {
                OpenMineralShopGUI(p);
            }

            if (p.getOpenInventory().title().equals(Component.text("음식 상점"))) {
                OpenFoodShopGUI(p);
            }

            if (p.getOpenInventory().title().equals(Component.text("거래소"))) {
                OpenExchangeShopGUI(p);
            }
        }
    }

    public void OpenWoodShopGUI(Player p) {
        //원목만
        Inventory inventory = Bukkit.createInventory(p.getInventory().getHolder(), 27, Component.text("나무 상점"));

        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, CapitalismMinecraft.instance.menu.button_items.get(0));
        }

        for (int i = 0; i <= 9; i++) {
            ItemStack item = button_items.get(i);
            PriceInfo info = shop_items.get(item.getType());

            List<Component> lores = new ArrayList<>();
            lores.add(Component.text(ChatColor.BLUE + "판매" + ChatColor.GOLD + " 가격 : " + info.now_price + "🪙"));
            lores.add(Component.text(ChatColor.GRAY + "[우클릭] 아이템 1개 판매, [쉬프트키 + 우클릭] 아이템 64개 판매"));
            lores.add(Component.text(ChatColor.RED + "구매" + ChatColor.GOLD + " 가격 : " + info.getBuyPrice() + "🪙"));
            lores.add(Component.text(ChatColor.GRAY + "[좌클릭] 아이템 1개 구매, [쉬프트키 + 좌클릭] 아이템 64개 구매"));
            item.lore(lores);
            
            inventory.setItem(i, item);
        }

        inventory.setItem(26, CapitalismMinecraft.instance.menu.button_items.get(1));

        p.closeInventory();
        p.openInventory(inventory);
        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1, 1);
    }

    public void OpenMineralShopGUI(Player p) {
        //광물 블럭, 주괴, (구리, 철, 금 등 주괴로 만들 수 있는 것은 주괴로만, 섬손 금지)
        // 단, 네더라이트는 파편, 주괴 모두 가능
        // 광물은 구매 불가
        Inventory inventory = Bukkit.createInventory(p.getInventory().getHolder(), 27, Component.text("광물 상점"));

        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, CapitalismMinecraft.instance.menu.button_items.get(0));
        }

        int num = 0;
        for (int i = 10; i <= 19; i++) {
            ItemStack item = button_items.get(i);
            PriceInfo info = shop_items.get(item.getType());

            List<Component> lores = new ArrayList<>();
            lores.add(Component.text(ChatColor.BLUE + "판매" + ChatColor.GOLD + " 가격 : " + info.now_price + "🪙"));
            lores.add(Component.text(ChatColor.GRAY + "[우클릭] 아이템 1개 판매, [쉬프트키 + 우클릭] 아이템 64개 판매"));
            lores.add(Component.text(ChatColor.RED + "구매가 불가능한 상품입니다."));
            item.lore(lores);
            
            inventory.setItem(num++, item);
        }

        inventory.setItem(26, CapitalismMinecraft.instance.menu.button_items.get(1));

        p.closeInventory();
        p.openInventory(inventory);
        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1, 1);
    }

    public void OpenFoodShopGUI(Player p) {
        //먹었을 때 효과가 부여되지 않는 것만 (황금사과류 제외)
        Inventory inventory = Bukkit.createInventory(p.getInventory().getHolder(), 27, Component.text("음식 상점"));

        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, CapitalismMinecraft.instance.menu.button_items.get(0));
        }

        int num = 0;
        for (int i = 20; i <= 33; i++) {
            ItemStack item = button_items.get(i);
            PriceInfo info = shop_items.get(item.getType());

            if (i == 22 || i == 23) {
                List<Component> lores = new ArrayList<>();
                lores.add(Component.text(ChatColor.BLUE + "판매" + ChatColor.GOLD + " 가격 : " + info.now_price + "🪙"));
                lores.add(Component.text(ChatColor.GRAY + "[우클릭] 아이템 1개 판매, [쉬프트키 + 우클릭] 아이템 64개 판매"));
                lores.add(Component.text(ChatColor.RED + "구매가 불가능한 상품입니다."));
                item.lore(lores);
            }
            else {
                List<Component> lores = new ArrayList<>();
                lores.add(Component.text(ChatColor.BLUE + "판매" + ChatColor.GOLD + " 가격 : " + info.now_price + "🪙"));
                lores.add(Component.text(ChatColor.GRAY + "[우클릭] 아이템 1개 판매, [쉬프트키 + 우클릭] 아이템 64개 판매"));
                lores.add(Component.text(ChatColor.RED + "구매" + ChatColor.GOLD + " 가격 : " + info.getBuyPrice() + "🪙"));
                lores.add(Component.text(ChatColor.GRAY + "[좌클릭] 아이템 1개 구매, [쉬프트키 + 좌클릭] 아이템 64개 구매"));
                item.lore(lores);
            }
            
            inventory.setItem(num++, item);
        }

        inventory.setItem(26, CapitalismMinecraft.instance.menu.button_items.get(1));

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

        inventory.setItem(18, CapitalismMinecraft.instance.menu.button_items.get(8));
        inventory.setItem(26, CapitalismMinecraft.instance.menu.button_items.get(1));

        p.closeInventory();
        p.openInventory(inventory);
        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1, 1);
    }

    public void AddESItem(Player p, ItemStack stack, int price) {
        if (ExchangeItem.size() >= 18) {
            p.sendMessage(Component.text(ChatColor.RED + "거래소가 꽉 찼습니다.")); 
            return;
        }

        List<Component> lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.GOLD + "가격 : " + price + "🪙"));
        stack.lore(lores);

        ExchangeItem.push(new ESItem(stack, price, p.getName()));
        CapitalismMinecraft.instance.getServer().sendMessage(Component.text(ChatColor.LIGHT_PURPLE + p.getName() + ChatColor.WHITE + "님이 아이템을 거래소에 등록했습니다!"));
    
        update_inventory();
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

            update_inventory();
        }
        else {
            p.sendMessage(Component.text(ChatColor.RED + "돈이 부족합니다."));
            p.playSound(p.getLocation(), Sound.BLOCK_CHAIN_PLACE, 1, 1);
        }
    }

    public void Sell(Player p, Material category, Boolean isone) {
        PriceInfo info = shop_items.get(category);
        int price = info.now_price;
        int player_item_count = 0;

        for (ItemStack stack : p.getInventory().getContents()) {
            if (stack != null && stack.getType() != null && stack.getType().equals(category)) player_item_count += stack.getAmount();
        }

        shop_items.get(category).now_count++;

        if (isone) {            
            if (player_item_count >= 1) {
                CapitalismMinecraft.instance.wallet.AddMoney(p, price);

                p.getInventory().removeItem(new ItemStack(category));
                p.sendMessage(Component.text(ChatColor.GREEN + "성공적으로 판매하였습니다." + ChatColor.GOLD + " +" + price + "🪙"));
                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
            }
            else {
                p.sendMessage(Component.text(ChatColor.RED + "판매할 아이템이 부족합니다."));
                p.playSound(p.getLocation(), Sound.BLOCK_CHAIN_PLACE, 1, 1);
            }
        }
        else {
            if (player_item_count <= 64) {
                price *= player_item_count;
                CapitalismMinecraft.instance.wallet.AddMoney(p, price);

                p.getInventory().removeItem(new ItemStack(category, player_item_count));
                p.sendMessage(Component.text(ChatColor.GREEN + "성공적으로 판매하였습니다." + ChatColor.GOLD + " +" + price + "🪙"));
                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
            }
            else {
                price *= 64;
                CapitalismMinecraft.instance.wallet.AddMoney(p, price);

                p.getInventory().removeItem(new ItemStack(category, 64));
                p.sendMessage(Component.text(ChatColor.GREEN + "성공적으로 판매하였습니다." + ChatColor.GOLD + " +" + price + "🪙"));
                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
            }
        }
        
    }

    public void Buy(Player p, Material category, Boolean isone) {
        PriceInfo info = shop_items.get(category);
        int price = info.getBuyPrice();
        
        if (isone) {
            if (CapitalismMinecraft.instance.wallet.Wlist.get(p.getName()) >= price) {
                CapitalismMinecraft.instance.wallet.SubMoney(p, price);

                p.getInventory().addItem(new ItemStack(category));
                p.sendMessage(Component.text(ChatColor.GREEN + "성공적으로 구매하였습니다." + ChatColor.GOLD + " -" + price + "🪙"));
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            }
            else {
                p.sendMessage(Component.text(ChatColor.RED + "돈이 부족합니다."));
                p.playSound(p.getLocation(), Sound.BLOCK_CHAIN_PLACE, 1, 1);
            }
        }
        else {
            price *= 64;

            if (CapitalismMinecraft.instance.wallet.Wlist.get(p.getName()) >= price) {
                CapitalismMinecraft.instance.wallet.SubMoney(p, price);

                p.getInventory().addItem(new ItemStack(category, 64));
                p.sendMessage(Component.text(ChatColor.GREEN + "성공적으로 구매하였습니다." + ChatColor.GOLD + " -" + price + "🪙"));
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            }
            else {
                p.sendMessage(Component.text(ChatColor.RED + "돈이 부족합니다."));
                p.playSound(p.getLocation(), Sound.BLOCK_CHAIN_PLACE, 1, 1);
            }
        }
    }

    public void ResetPrice() {
        for (ItemStack stack : button_items) {
            Set_item_price(stack.getType());
        }

        update_inventory();
    }

    public void Set_item_price(Material material) {
        PriceInfo info = shop_items.get(material);
        int result_price = info.now_price;

        if (info.before_count > 0) { // 이전 데이터가 있어야 가격 변화
            result_price = (int) ((info.now_price * info.now_count)/info.before_count);
        }
        else { // 이전 데이터가 없을 때는 랜덤으로
            result_price = (int)(Math.random()*info.max_price);
        }

        if (result_price <= info.min_price) result_price = info.min_price;
        if (result_price >= info.max_price) result_price = info.max_price;

        shop_items.get(material).before_count = info.now_count;
        shop_items.get(material).now_count = 0;
        shop_items.get(material).now_price = result_price;
    }
}