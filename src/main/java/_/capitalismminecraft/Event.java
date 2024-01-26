package _.capitalismminecraft;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import _.capitalismminecraft.Wallet.SendInfo;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.md_5.bungee.api.ChatColor;

public class Event implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        CapitalismMinecraft plugin = CapitalismMinecraft.instance;
        Player p = event.getPlayer();

        plugin.wallet.CreateWallet(p);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        CapitalismMinecraft plugin = CapitalismMinecraft.instance;
        Player p = (Player) event.getWhoClicked();

        if (event.getCurrentItem() != null) {
            Component clicked_item = event.getCurrentItem().displayName();

            if (clicked_item == null) return;

            if (clicked_item.equals(plugin.menu.button_items.get(0).displayName())) { // 빈 공간
                event.setCancelled(true);
                return;
            }
        
            if (event.getView().title().equals(Component.text("메뉴"))) {
                if (clicked_item.equals(plugin.menu.button_items.get(1).displayName())) { // 목재
                    event.setCancelled(true);
                    plugin.shop.OpenWoodShopGUI(p);
                    return;
                }
                else if (clicked_item.equals(plugin.menu.button_items.get(2).displayName())) { // 광물
                    event.setCancelled(true);
                    plugin.shop.OpenMineralShopGUI(p);
                    return;
                }
                else if (clicked_item.equals(plugin.menu.button_items.get(3).displayName())) { // 식료품
                    event.setCancelled(true);
                    plugin.shop.OpenFoodShopGUI(p);
                    return;
                }
                else if (clicked_item.equals(plugin.menu.button_items.get(4).displayName())) { // 거래소
                    event.setCancelled(true);
                    plugin.shop.OpenExchangeShopGUI(p);
                    return;
                }
                else if (clicked_item.equals(plugin.menu.button_items.get(5).displayName())) { // 퀘스트
                    event.setCancelled(true);
                    plugin.quest.OpenQuestGUI(p);
                    return;
                }
                else if (clicked_item.equals(plugin.menu.button_items.get(6).displayName())) { // 송금
                    event.setCancelled(true);
                    plugin.menu.OpenSendMoneyMenu(p);
                    return;
                }
            }

            if (event.getView().title().equals(Component.text("상점"))) {
            }

            if (event.getView().title().equals(Component.text("거래소"))) {
                if (clicked_item.equals(plugin.menu.button_items.get(7).displayName())) { // 거래소 등록법
                    event.setCancelled(true);
                    p.sendMessage(Component.text(ChatColor.GREEN + "등록할 아이템을 손에 들고 '등록 (가격)' 또는 'emndfhr (가격)'이라고 채팅창에 입력하세요. (가격은 숫자로만 입력하세요)"));
                    p.closeInventory();
                    return;
                }

                if (event.getSlot() < plugin.shop.ExchangeItem.size()) {
                    event.setCancelled(true);
                    plugin.shop.BuyESItem(p, event.getSlot());
                    p.closeInventory();
                    return;
                }
            }

            if (event.getView().title().equals(Component.text("퀘스트"))) {

            }

            if (event.getView().title().equals(Component.text("송금하기"))) {
                for (ItemStack target : event.getInventory().getContents()) { // 송금할 사람
                    if (event.getCurrentItem() != null) {
                        if (clicked_item.equals(target.displayName())) {
                            event.setCancelled(true);

                            Player targetP = plugin.getServer().getPlayer(((TextComponent) target.displayName()).content());
                            if (targetP == null) continue;

                            p.sendMessage(Component.text(ChatColor.RED + "보낼 금액의 액수를 채팅창에 입력하세요. (숫자만 입력하세요)"));

                            plugin.wallet.Sending.putIfAbsent(p.getName(), plugin.wallet.new SendInfo(targetP, -1));
                            p.closeInventory();
                            return;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        CapitalismMinecraft plugin = CapitalismMinecraft.instance;
        Player p = event.getPlayer();
        String msg = ((TextComponent) event.message()).content();

        if (plugin.wallet.Sending.containsKey(p.getName())) {
            if (plugin.wallet.Sending.get(p.getName()).target != null) {
                int amount = -1;

                try{
                    amount = Integer.parseInt(msg);
                }
                catch (NumberFormatException ex){
                    p.sendMessage(Component.text(ChatColor.RED + "숫자만 입력하세요."));
                    ex.printStackTrace();
                }

                if (amount != -1) {
                    event.setCancelled(true);
                    plugin.wallet.SendMoneyAtoB(p, plugin.wallet.Sending.get(p.getName()).target, amount);
                    plugin.wallet.Sending.replace(p.getName(), plugin.wallet.new SendInfo(null, -1));
                }
            }
        }

        String[] split = msg.split(" ");
        if (split.length == 2) {
            if (split[0].equals("등록") || split[0].equals("emdfhr")) {
                event.setCancelled(true);

                int price = -1;

                try{
                    price = Integer.parseInt(msg);
                }
                catch (NumberFormatException ex){
                    p.sendMessage(Component.text(ChatColor.RED + "가격은 숫자로만 입력하세요."));
                    ex.printStackTrace();
                }
                
                if (price != -1) {
                    plugin.shop.AddESItem(p, p.getInventory().getItemInMainHand(), price);
                    p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
                }
            }
        }
    }
    
    @EventHandler
    public void onAdvancementDone(PlayerAdvancementDoneEvent event) {
        Player p = event.getPlayer();

        // event.getAdvancement()
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        CapitalismMinecraft plugin = CapitalismMinecraft.instance;
        Player p = event.getPlayer();
        ItemStack[] items = p.getInventory().getContents();

        p.getInventory().clear();
        p.setLevel(0);
        p.setExp(0);

        Block left = p.getWorld().getBlockAt(p.getLocation());
        left.setType(Material.CHEST);
        Block right = p.getWorld().getBlockAt(p.getLocation().getBlockX()+1, p.getLocation().getBlockY(), p.getLocation().getBlockZ());
        right.setType(Material.CHEST);

        Chest Lchest = (Chest) left.getState(), Rchest = (Chest) right.getState();
        Inventory Linv = Lchest.getBlockInventory(), Rinv = Rchest.getBlockInventory();

        Linv.clear();
        Rinv.clear();

        for (int i = 0; i < items.length; i++) {
            if (i < 27)
                Linv.setItem(i, items[i]);
            else
                Rinv.setItem(i-27, items[i]);
        }

        new BukkitRunnable() {
            public void run() {
                p.sendMessage(Component.text(ChatColor.GREEN + "만회 나침반이 주어졌습니다."));
                p.getInventory().addItem(new ItemStack(Material.RECOVERY_COMPASS));
            }
        }.runTaskLater(plugin, 20);
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        CapitalismMinecraft plugin = CapitalismMinecraft.instance;

        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            if (event.getEntity().getKiller() != null && event.getEntity().getKiller() instanceof Player) {
                Player killer = event.getEntity().getKiller();

                plugin.wallet.SubMoney(killer, 500);
            }
            else {
                plugin.wallet.SubMoney(p, 500);
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player p = event.getPlayer();

        if (event.getAction().isRightClick()) {
            
        }
        else if (event.getAction().isLeftClick()) {
            
        }
    }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent event) {
        CapitalismMinecraft plugin = CapitalismMinecraft.instance;
        Player p = event.getPlayer();

        if (p.isSneaking()) {
            event.setCancelled(true);
            plugin.menu.OpenPlayerMenu(p);
        }
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        CapitalismMinecraft plugin = CapitalismMinecraft.instance;
        Player p = event.getPlayer();

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());

        if (event.getBlock().getType().equals(Material.OAK_LOG)
        && event.getBlock().getType().equals(Material.ACACIA_LOG)
        && event.getBlock().getType().equals(Material.SPRUCE_LOG)
        && event.getBlock().getType().equals(Material.BIRCH_LOG)
        && event.getBlock().getType().equals(Material.JUNGLE_LOG)
        && event.getBlock().getType().equals(Material.DARK_OAK_LOG)
        && event.getBlock().getType().equals(Material.MANGROVE_LOG)
        && event.getBlock().getType().equals(Material.CHERRY_LOG)
        && event.getBlock().getType().equals(Material.CRIMSON_STEM)
        && event.getBlock().getType().equals(Material.WARPED_STEM)) {
            plugin.wallet.AddMoney(p, 1);
        }
        
        if (event.getBlock().getType().equals(Material.COAL_ORE)) {
            plugin.wallet.AddMoney(p, random.nextInt(2) + 1);
        }
    }

    @EventHandler
    public void getEXP(PlayerExpChangeEvent event) {
        CapitalismMinecraft plugin = CapitalismMinecraft.instance;
        Player p = event.getPlayer();
        plugin.wallet.AddMoney(p, event.getAmount());
    }
}
