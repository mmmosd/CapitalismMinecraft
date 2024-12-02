package _.capitalismminecraft;

import java.util.*;

import _.capitalismminecraft.Build.CreateArea;
import _.capitalismminecraft.Data.CreateData;
import _.capitalismminecraft.Data.RegionData;
import _.capitalismminecraft.GUI.EnterOption;
import _.capitalismminecraft.Items.CustomStack;
import _.capitalismminecraft.Utils.CheckRegion;
import _.capitalismminecraft.Utils.CreateRegion;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import com.iwebpp.crypto.TweetNaclFast.poly1305;

import _.capitalismminecraft.Data.RegionData;
import io.papermc.paper.advancement.AdvancementDisplay;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;

public class Event implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        CapitalismMinecraft plugin = CapitalismMinecraft.instance;
        Player p = event.getPlayer();

        plugin.wallet.CreateWallet(p);

        CreateData cd = new CreateData();
        cd.CreatePlayerDataFile();

        plugin.skill.cooldown.putIfAbsent(p.getUniqueId(), new ArrayList<Skill.CoolDown>());

        for (ItemStack stack : p.getInventory().getContents()) {
            if (stack == null) continue;

            for (Skill.CoolDown coolDown : plugin.skill.cooldown.get(p.getUniqueId())) {
                if (stack.getType().equals(coolDown.mat)) {
                    p.setCooldown(coolDown.mat, coolDown.time);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        CapitalismMinecraft plugin = CapitalismMinecraft.instance;
        Player p = (Player) event.getWhoClicked();

        if (event.getCurrentItem() != null) {
            Component clicked_item = event.getCurrentItem().displayName();

            if (event.getView().title().equals(Component.text("강화"))) {
                if (clicked_item.equals(plugin.menu.button_items.get(0).displayName())) { // 빈 공간
                    event.setCancelled(true);
                    return;
                }
    
                if (clicked_item.equals(plugin.menu.button_items.get(1).displayName())) { // 뒤로가기
                    event.setCancelled(true);
                    plugin.menu.OpenPlayerMenu(p);
                    return;
                }

                if (event.getSlot() == 22) { // 강화 버튼
                    event.setCancelled(true);
                    ItemStack item = event.getClickedInventory().getItem(13);

                    if (item == null) {
                        p.sendMessage(Component.text(ChatColor.RED + "강화 가능 아이템을 위에 올려두세요!"));
                        p.playSound(p.getLocation(), Sound.BLOCK_CHAIN_PLACE, 1, 1);
                        return;
                    }

                    int level = plugin.skill.check_level(item);
                    int money = plugin.wallet.Wlist.get(p.getName());

                    if (level == -1) {
                        p.sendMessage(Component.text(ChatColor.RED + "강화 가능 아이템을 위에 올려두세요!"));
                        p.playSound(p.getLocation(), Sound.BLOCK_CHAIN_PLACE, 1, 1);
                        return;
                    }

                    if (level == 4) {
                        p.sendMessage(Component.text(ChatColor.RED + "이 아이템은 최대 레벨입니다!"));
                        p.playSound(p.getLocation(), Sound.BLOCK_CHAIN_PLACE, 1, 1);
                        return;
                    }
        
                    if (level == 0) {
                        if (money >= 1000) {
                            plugin.wallet.SubMoney(p, 1000);
                            plugin.skill.upgrade(p, item);
                        }
                        else {
                            p.sendMessage(Component.text(ChatColor.RED + "돈이 부족합니다."));
                            p.playSound(p.getLocation(), Sound.BLOCK_CHAIN_PLACE, 1, 1);
                        }
                    }
                    else if (level == 1) {
                        if (money >= 5000) {
                            plugin.wallet.SubMoney(p, 5000);
                            plugin.skill.upgrade(p, item);
                        }
                        else {
                            p.sendMessage(Component.text(ChatColor.RED + "돈이 부족합니다."));
                            p.playSound(p.getLocation(), Sound.BLOCK_CHAIN_PLACE, 1, 1);
                        }
                    }
                    else if (level == 2) {
                        if (money >= 10000) {
                            plugin.wallet.SubMoney(p, 10000);
                            plugin.skill.upgrade(p, item);
                        }
                        else {
                            p.sendMessage(Component.text(ChatColor.RED + "돈이 부족합니다."));
                            p.playSound(p.getLocation(), Sound.BLOCK_CHAIN_PLACE, 1, 1);
                        }
                    }
                    else if (level == 3) {
                        if (money >= 30000) {
                            plugin.wallet.SubMoney(p, 30000);
                            plugin.skill.upgrade(p, item);
                        }
                        else {
                            p.sendMessage(Component.text(ChatColor.RED + "돈이 부족합니다."));
                            p.playSound(p.getLocation(), Sound.BLOCK_CHAIN_PLACE, 1, 1);
                        }
                    }
                    return;
                }

                if (event.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
                    ItemStack item = event.getCurrentItem();
                    plugin.skill.update_inventory(p, item);
                    plugin.skill.slot.replace(p.getUniqueId(), event.getSlot());
                }
                else {
                    plugin.skill.update_inventory(p, new ItemStack(Material.AIR));
                }
            }

            if (event.getView().title().equals(Component.text("텔레포트"))) {
                event.setCancelled(true);
                for (ItemStack target : event.getInventory().getContents()) {
                    if (target != null && clicked_item.equals(target.displayName())) {
                        if (p.getInventory().getItemInMainHand().getAmount() == 0) {
                            p.sendMessage(Component.text(ChatColor.RED + "텔레포트 이용권이 부족합니다."));
                            return;
                        }

                        if (event.getSlot() == 0) {
                            for (World w : plugin.getServer().getWorlds()) {
                                if (w.getEnvironment().equals(World.Environment.NORMAL)) {
                                    p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
                                    p.sendMessage(Component.text(ChatColor.DARK_AQUA + "잠시 후 이동됩니다..."));
                                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                    new BukkitRunnable() {
                                        public void run() {
                                            p.teleport(w.getHighestBlockAt(0, 0).getLocation().add(0, 2, 0));
                                            p.sendMessage(Component.text(ChatColor.GREEN + "성공적으로 이동되었습니다."));
                                            p.playSound(p.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 1, 1);
                                            p.closeInventory();
                                        }
                                    }.runTaskLater(plugin, 100);
                                    break;
                                }
                            }
                        }
                        else {
                            NamespacedKey nkey = new NamespacedKey(CapitalismMinecraft.getPlugins(), "Name");
                            if (event.getCurrentItem().getItemMeta() == null && !event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(nkey)) return;

                            String name = event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(nkey, PersistentDataType.STRING);
                            if (name == null) return;

                            Player targetP = plugin.getServer().getPlayer(name);
                            if (targetP == null) return;

                            p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
                            p.sendMessage(Component.text(ChatColor.DARK_AQUA + "잠시 후 이동됩니다..."));
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                            new BukkitRunnable() {
                                public void run() {
                                    p.teleport(targetP.getLocation());
                                    p.sendMessage(Component.text(ChatColor.GREEN + "성공적으로 이동되었습니다."));
                                    p.playSound(p.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 1, 1);
                                    p.closeInventory();
                                }
                            }.runTaskLater(plugin, 100);
                        }
                        return;
                    }
                }
            }

            if (clicked_item == null) return;
            if (event.getClickedInventory().getType().equals(InventoryType.PLAYER)) return;

            if (clicked_item.equals(plugin.menu.button_items.get(0).displayName())) { // 빈 공간
                event.setCancelled(true);
                return;
            }

            if (clicked_item.equals(plugin.menu.button_items.get(1).displayName())) { // 뒤로가기
                event.setCancelled(true);
                plugin.menu.OpenPlayerMenu(p);
                return;
            }

            if (clicked_item.equals(plugin.menu.button_items.get(10).displayName())) { // 거래소 등록법
                event.setCancelled(true);
                p.sendMessage(Component.text(ChatColor.GREEN + "등록할 아이템을 손에 들고 '등록 [가격]' 또는 'emdfhr [가격]'이라고 채팅창에 입력하세요. ([가격]은 숫자로만 입력하세요)"));
                p.closeInventory();
                return;
            }
        
            if (event.getView().title().equals(Component.text("메뉴"))) {
                if (clicked_item.equals(plugin.menu.button_items.get(9).displayName())) { // 송금
                    event.setCancelled(true);
                    plugin.menu.OpenSendMoneyMenu(p);
                    return;
                }
            }

            if (event.getView().title().equals(Component.text("나무 상점")) 
            || event.getView().title().equals(Component.text("광물 상점")) 
            || event.getView().title().equals(Component.text("농수산물 상점"))) {
                for (ItemStack stack : plugin.shop.button_items) {
                    if (event.getCurrentItem().getType().equals(stack.getType())) { // 구매, 판매
                        event.setCancelled(true);
                        if (event.isRightClick()) {
                            plugin.shop.Sell(p, stack.getType(), !event.isShiftClick());
                        }
                        else if (event.isLeftClick()) {
                            if ((10 <= plugin.shop.button_items.indexOf(stack) && plugin.shop.button_items.indexOf(stack) <= 19)
                            || (plugin.shop.button_items.indexOf(stack) == 22)
                            || (plugin.shop.button_items.indexOf(stack) == 23)) {
                                p.sendMessage(Component.text(ChatColor.RED + "구매할 수 없는 항목입니다."));
                                p.playSound(p.getLocation(), Sound.BLOCK_CHAIN_PLACE, 1, 1);
                                continue;
                            }

                            plugin.shop.Buy(p, stack.getType(), !event.isShiftClick());
                        }
                        return;
                    }
                }
            }

            if (event.getView().title().equals(Component.text("컨텐츠 상점"))) {
                for (Material mat : plugin.shop.ContentItem.keySet()) {
                    if (event.getCurrentItem().getType().equals(mat)) { // 구매
                        event.setCancelled(true);
                        if (event.isRightClick()) {
                            p.sendMessage(Component.text(ChatColor.RED + "판매할 수 없는 항목입니다."));
                            p.playSound(p.getLocation(), Sound.BLOCK_CHAIN_PLACE, 1, 1);
                        }
                        else if (event.isLeftClick()) {
                            plugin.shop.BuyContent(p, event.getCurrentItem());
                        }
                        return;
                    }
                }
            }

            if (event.getView().title().equals(Component.text("거래소"))) {


                if (event.getSlot() < plugin.shop.ExchangeItem.size()) {
                    event.setCancelled(true);
                    plugin.shop.BuyESItem(p, event.getSlot());
                    return;
                }
            }

            if (event.getView().title().equals(Component.text("퀘스트"))) {
                for (Quest.Questinfo q : plugin.quest.quests) {
                    if (event.getCurrentItem().getType().equals(q.item.getType())) { // 퀘스트 완료
                        event.setCancelled(true);
                        plugin.quest.complet_quest(p, event.getCurrentItem().getType());
                    }
                }
            }

            if (event.getView().title().equals(Component.text("송금하기"))) {
                for (ItemStack target : event.getInventory().getContents()) { // 송금할 사람
                    if (target != null && clicked_item.equals(target.displayName())) {
                        event.setCancelled(true);

                        NamespacedKey key = new NamespacedKey(CapitalismMinecraft.getPlugins(), "Name");
                        if (event.getCurrentItem().getItemMeta() == null && !event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(key)) return;

                        String name = event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);
                        if (name == null) return;

                        Player targetP = plugin.getServer().getPlayer(name);
                        if (targetP == null) return;

                        p.sendMessage(Component.text(ChatColor.RED + "보낼 금액의 액수를 채팅창에 입력하세요. (숫자만 입력하세요)"));

                        plugin.wallet.Sending.put(p.getName(), plugin.wallet.new SendInfo(targetP, -1));
                        p.closeInventory();
                        return;
                    }
                }
            }
        }

        ItemStack current;
        String region;
        RegionData rd;
        if (CapitalismMinecraft.optiongui.containsKey(p)) {
            current = event.getCurrentItem();
            if (current == null) {
                return;
            }

            event.setCancelled(true);
            if (event.getClick() != ClickType.LEFT) {
                return;
            }

            region = (String)CapitalismMinecraft.optiongui.get(p);
            rd = new RegionData(region);
            new CreateRegion(region);
            ArmorStand armorStand = (ArmorStand)CapitalismMinecraft.armorstandData.get(p);
            EnterOption eo = new EnterOption(region);
            if (current.isSimilar(CustomStack.removeregion())) {
                CreateArea.DeleteArea(p, armorStand, region);
                return;
            }

            if (current.isSimilar(CustomStack.addplayer())) {
                eo.openGUI(p, "Add");
                return;
            }

            int slot = event.getRawSlot();
            if (8 < slot && slot < 36) {
                UUID uuid = UUID.fromString((String)current.getItemMeta().getPersistentDataContainer().get(CapitalismMinecraft.key, PersistentDataType.STRING));
                rd.removeOwner(uuid);
                eo.reloadGUI("Option");
                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 0.5F);
            }
        }

        if (CapitalismMinecraft.addgui.containsKey(p)) {
            current = event.getCurrentItem();
            if (current == null) {
                return;
            }

            event.setCancelled(true);
            if (event.getClick() != ClickType.LEFT) {
                return;
            }

            region = (String)CapitalismMinecraft.addgui.get(p);
            rd = new RegionData(region);
            new CreateRegion(region);
            EnterOption eo = new EnterOption(region);
            if (current.isSimilar(CustomStack.previous())) {
                eo.openGUI(p, "Option");
                return;
            }

            int slot = event.getRawSlot();
            if (8 < slot && slot < 45) {
                UUID uuid = UUID.fromString((String)current.getItemMeta().getPersistentDataContainer().get(CapitalismMinecraft.key, PersistentDataType.STRING));
                rd.addOwner(uuid);
                eo.reloadGUI("Add");
                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 4.0F, 1.5F);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player p = (Player) event.getPlayer();

        if (CapitalismMinecraft.optiongui.containsKey(p)) {
            CapitalismMinecraft.optiongui.remove(p);
            CapitalismMinecraft.armorstandData.remove(p);
        }

        if (CapitalismMinecraft.addgui.containsKey(p)) {
            CapitalismMinecraft.addgui.remove(p);
            CapitalismMinecraft.armorstandData.remove(p);
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

                if (CapitalismMinecraft.isNumberExeption(msg)) {
                    p.sendMessage(Component.text(ChatColor.RED + "숫자만 입력하세요."));
                    plugin.wallet.Sending.replace(p.getName(), plugin.wallet.new SendInfo(null, -1));
                    return;
                }

                amount = Integer.parseInt(msg);

                if (amount >= 0) {
                    event.setCancelled(true);
                    plugin.wallet.SendMoneyAtoB(p, plugin.wallet.Sending.get(p.getName()).target, amount);
                }
                else {
                    p.sendMessage(Component.text(ChatColor.RED + "0 이하의 가격은 보낼 수 없습니다."));
                }

                plugin.wallet.Sending.replace(p.getName(), plugin.wallet.new SendInfo(null, -1));
            }
        }

        String[] split = msg.split(" ");
        if (split.length == 2) {
            if (split[0].equals("등록") || split[0].equals("emdfhr")) {
                event.setCancelled(true);

                int price = -1;

                if (CapitalismMinecraft.isNumberExeption(split[1])) {
                    p.sendMessage(Component.text(ChatColor.RED + "가격은 숫자로만 입력하세요."));
                    return;
                }

                price = Integer.parseInt(split[1]);
                
                if (price >= 0) {
                    plugin.shop.AddESItem(p, p.getInventory().getItemInMainHand(), price);
                }
                else {
                    p.sendMessage(Component.text(ChatColor.RED + "0 이하의 가격은 등록할 수 없습니다."));
                }
            }
        }
    }

    @EventHandler
    public void onAct(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        ItemStack is = p.getItemInHand();
        ItemMeta im;
        PersistentDataContainer pdc;
        NamespacedKey key;
        if (is.getType().equals(Material.BRICK)) {
            if (a != Action.RIGHT_CLICK_BLOCK) {
                return;
            }

            im = is.getItemMeta();
            if (im == null) {
                return;
            }

            if (im.getPersistentDataContainer().isEmpty()) {
                return;
            }

            if (e.getHand() == EquipmentSlot.OFF_HAND) {
                return;
            }

            int x = e.getClickedBlock().getLocation().getBlockX(), z = e.getClickedBlock().getLocation().getBlockZ();
            x = x<0?-x:x;
            z = z<0?-z:z;

            if (x < 35 && z < 35) {
                p.sendMessage(Component.text(ChatColor.RED + "스폰[0, 0] 주변에는 건설 차단 구역을 만들 수 없습니다."));
                return;
            }

            pdc = im.getPersistentDataContainer();
            key = new NamespacedKey(CapitalismMinecraft.getPlugins(), "CapitalismMinecraft");
            if (pdc.has(key, PersistentDataType.INTEGER)) {
                int large = (Integer)pdc.get(key, PersistentDataType.INTEGER);
                if (CreateArea.CreateNewArea(p, e.getClickedBlock().getLocation(), large)) {
                    if (is.getAmount() > 1) {
                        is.setAmount(is.getAmount() - 1);
                        p.getInventory().setItemInMainHand(is);
                    } else {
                        p.getInventory().setItemInMainHand((ItemStack)null);
                    }
                }

                e.setCancelled(true);
            }
        } else if (is.getType().equals(Material.GOLDEN_AXE)) {
            im = is.getItemMeta();
            if (im == null) {
                return;
            }

            if (im.getPersistentDataContainer().isEmpty()) {
                return;
            }

            if (e.getHand() == EquipmentSlot.OFF_HAND) {
                return;
            }

            pdc = im.getPersistentDataContainer();
            key = new NamespacedKey(CapitalismMinecraft.getPlugins(), "CapitalismMinecraft");
            if (pdc.has(key, PersistentDataType.STRING)) {
                if (a == Action.LEFT_CLICK_BLOCK) {
                    p.sendMessage(CapitalismMinecraft.ColorChat("&d첫 번째 포지션을 지정하였습니다."));
                    if (CapitalismMinecraft.Pos1.containsKey(p)) {
                        CapitalismMinecraft.Pos1.remove(p);
                    }

                    CapitalismMinecraft.Pos1.put(p, e.getClickedBlock().getLocation());
                    e.setCancelled(true);
                } else if (a == Action.RIGHT_CLICK_BLOCK) {
                    p.sendMessage(CapitalismMinecraft.ColorChat("&d두 번째 포지션을 지정하였습니다."));
                    if (CapitalismMinecraft.Pos2.containsKey(p)) {
                        CapitalismMinecraft.Pos2.remove(p);
                    }

                    CapitalismMinecraft.Pos2.put(p, e.getClickedBlock().getLocation());
                    e.setCancelled(true);
                }
            }
        } else {
            if (!is.getType().toString().endsWith("BUCKET")) {
                return;
            }

            if (is.getType().equals(Material.MILK_BUCKET)) {
                return;
            }

            if (e.getClickedBlock() == null) {
                return;
            }

            Location loc = e.getClickedBlock().getLocation();
            if (!loc.getBlock().isLiquid()) {
                loc = e.getClickedBlock().getRelative(e.getBlockFace()).getLocation();
            }

            CheckRegion cr = new CheckRegion(loc);
            if (cr.isBlockIsInOutLine()) {
                e.setCancelled(true);
                p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&f해당 외각 블럭에 설치할 수 없습니다."));
                return;
            }

            if (!p.isOp() && cr.isBlockIsOtherRegionInIfOwner(p)) {
                e.setCancelled(true);
                p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&f해당 위치는 건차 지역 범위 내 입니다."));
            }
        }

    }

    @EventHandler
    public void onActEntity(PlayerInteractAtEntityEvent e) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        Player p = e.getPlayer();
        if (e.getRightClicked() instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand)e.getRightClicked();
            if (armorStand.isPersistent()) {
                if (armorStand.getPersistentDataContainer().has(CapitalismMinecraft.getKey(), PersistentDataType.STRING)) {
                    String region = (String)armorStand.getPersistentDataContainer().get(CapitalismMinecraft.getKey(), PersistentDataType.STRING);
                    RegionData rd = new RegionData(region);
                    if (rd.isExistRegion()) {
                        e.setCancelled(true);
                        if (!(e.getClickedPosition().getY() < armorStand.getEyeHeight() - 0.358D)) {
                            if (!rd.getBuilder().equals(p.getUniqueId()) && !p.isOp()) {
                                p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&f당신은 건차 옵션을 열 권한이 없습니다."));
                            } else {
                                EnterOption eo = new EnterOption(region);
                                eo.openGUI(p, "Option");
                                CapitalismMinecraft.armorstandData.put(p, armorStand);
                            }
                        }
                    }
                }

                if (armorStand.getPersistentDataContainer().has(new NamespacedKey(pl, "Menu"), PersistentDataType.STRING)) {
                    e.setCancelled(true);

                    if (!(e.getClickedPosition().getY() < armorStand.getEyeHeight() - 0.358D)) {
                        String name = armorStand.getPersistentDataContainer().get(new NamespacedKey(pl, "Menu"), PersistentDataType.STRING);

                        if (name == null) return;

                        switch (name) {
                            case "wood":
                                pl.shop.OpenWoodShopGUI(p);
                                break;
                            case "mineral":
                                pl.shop.OpenMineralShopGUI(p);
                                break;
                            case "food":
                                pl.shop.OpenFoodShopGUI(p);
                                break;
                            case "content":
                                pl.shop.OpenContentShopGUI(p);
                                break;
                            case "exchange":
                                pl.shop.OpenExchangeShopGUI(p);
                                break;
                            case "quest":
                                pl.quest.OpenQuestGUI(p);
                                break;
                            case "skill":
                                pl.skill.OpenUpgradeGUI(p, new ItemStack(Material.AIR), false);
                                break;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInteractAtEntity(PlayerInteractEntityEvent event) {
        Player p = event.getPlayer();
        
        if (event.getRightClicked() instanceof Villager) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onAdvancementDone(PlayerAdvancementDoneEvent event) {
        CapitalismMinecraft plugin = CapitalismMinecraft.instance;
        Player p = event.getPlayer();

        if (event.getAdvancement().getDisplay() == null) return;

        if (event.getAdvancement().getDisplay().frame().equals(AdvancementDisplay.Frame.CHALLENGE)) { //challenge
            p.sendMessage(Component.text(ChatColor.LIGHT_PURPLE +"도전 완료!" + ChatColor.GOLD + " +2500🪙"));
            plugin.wallet.AddMoney(p, 2500);
        }
        else if (event.getAdvancement().getDisplay().frame().equals(AdvancementDisplay.Frame.GOAL)) { //goal
            p.sendMessage(Component.text(ChatColor.YELLOW +"목표 달성!" + ChatColor.GOLD + " +1000🪙"));
            plugin.wallet.AddMoney(p, 1000);
        }
        else if (event.getAdvancement().getDisplay().frame().equals(AdvancementDisplay.Frame.TASK)) { //normal
            p.sendMessage(Component.text(ChatColor.WHITE +"발전 과제 달성!" + ChatColor.GOLD + " +250🪙"));
            plugin.wallet.AddMoney(p, 250);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        CapitalismMinecraft plugin = CapitalismMinecraft.instance;
        Player p = event.getPlayer();
        ItemStack[] items = p.getInventory().getContents();

        NamespacedKey key = new NamespacedKey(CapitalismMinecraft.getPlugins(), "invensave");

        if (p.getInventory().getItemInMainHand().hasItemMeta()) {
            if (p.getInventory().getItemInMainHand().hasItemMeta()) {
                PersistentDataContainer pdc = p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
                if (pdc.has(key)) {
                    p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
                    return;
                }
            }
        }
        else if (p.getInventory().getItemInOffHand().hasItemMeta()) {
            if (p.getInventory().getItemInOffHand().hasItemMeta()) {
                PersistentDataContainer pdc = p.getInventory().getItemInOffHand().getItemMeta().getPersistentDataContainer();
                if (pdc.has(key)) {
                    p.getInventory().getItemInOffHand().setAmount(p.getInventory().getItemInOffHand().getAmount()-1);
                    return;
                }
            }
        }

        p.getInventory().clear();
        p.setLevel(0);
        p.setExp(0);

        Block down = p.getWorld().getBlockAt(p.getLocation());
        down.setType(Material.CHEST);
        Block up = p.getWorld().getBlockAt(p.getLocation().getBlockX(), p.getLocation().getBlockY()+1, p.getLocation().getBlockZ());

        if (up.getType().equals(Material.BEDROCK)) {
            up = p.getWorld().getBlockAt(p.getLocation().getBlockX(), p.getLocation().getBlockY()-1, p.getLocation().getBlockZ());
        }
        up.setType(Material.CHEST);

        Chest Achest = (Chest) down.getState(), Bchest = (Chest) up.getState();
        Inventory Ainv = Achest.getBlockInventory(), Binv = Bchest.getBlockInventory();

        Ainv.clear();
        Binv.clear();

        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) continue;

            if (i < 27)
                Ainv.setItem(i, items[i]);
            else
                Binv.setItem(i-27, items[i]);
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

                plugin.wallet.SubMoney(killer, 2500);
                plugin.getServer().sendMessage(Component.text(ChatColor.RED + "[벌금형] " + killer.getName() + "님 에게 " + ChatColor.GOLD + "-2500🪙" + ChatColor.RED + "의 벌금이 주어집니다."));
            }
            else {
                plugin.wallet.SubMoney(p, 500);
                p.sendMessage(Component.text(ChatColor.RED + "자연사는 " + ChatColor.GOLD + "-500🪙" + ChatColor.RED + "의 패널티가 주어집니다."));
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;

        if (event.getDamager() instanceof Player) {
            Player p = (Player) event.getDamager();

            //검 스킬
            pl.skill.swordSkill.skill_1(p);
            pl.skill.swordSkill.skill_2(p);
            pl.skill.swordSkill.skill_4(p);
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;

        if (event.getEntity().getShooter() instanceof Player && event.getHitEntity() != null) {
            Player p = ((Player) event.getEntity().getShooter()).getPlayer();

            //활 스킬
            pl.skill.bowSkill.skill_1(p);
            pl.skill.bowSkill.skill_2(p, event);
            pl.skill.bowSkill.skill_4(p);
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;

        Player p = event.getPlayer();

        pl.skill.hoeSkill.skill_1(p, event);

        if (event.getAction().isRightClick()) {
            //곡괭이 스킬
            pl.skill.pickaxeSkill.skill_3(p);
            //도끼 스킬
            pl.skill.axeSkill.skill_3(p, event);
            //검 스킬
            pl.skill.swordSkill.skill_3(p);
            //괭이 스킬
            pl.skill.hoeSkill.skill_3(p, event);

            if (p.getInventory().getItemInMainHand().getItemMeta() != null) {
                NamespacedKey tpkey = new NamespacedKey(CapitalismMinecraft.getPlugins(), "tp");
                PersistentDataContainer pdc = p.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();

                if (pdc.has(tpkey)) {
                    pl.shop.contentsStack.OpenTpGUI(p);
                }

                NamespacedKey enkey = new NamespacedKey(CapitalismMinecraft.getPlugins(), "en");

                if (pdc.has(enkey)) {
                    pl.shop.contentsStack.RandomEnchantment(p);
                }
            }

            if (event.getClickedBlock() != null) {
                Location loc = event.getClickedBlock().getLocation();
                CheckRegion cr = new CheckRegion(loc);
                if (!p.isOp() && cr.isBlockIsOtherRegionInIfOwner(p)) {
                    event.setCancelled(true);
                    p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&f해당 위치는 건차 지역 범위 내 입니다."));
                }
            }
        }
        else if (event.getAction().isLeftClick()) {
            //활 스킬
            pl.skill.bowSkill.skill_3(p);
            //괭이 스킬
            pl.skill.hoeSkill.skill_2(p, event);
        }
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;

        Player p = event.getPlayer();

        //낚싯대 스킬
        pl.skill.fishingRodSkill.skill_1(p, event);
        pl.skill.fishingRodSkill.skill_2(p, event);
        pl.skill.fishingRodSkill.skill_4(p, event);
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
    public void onPiston1(BlockPistonExtendEvent e) {
        CreateData cd = new CreateData();
        if (cd.isProtectPiston()) {
            Iterator var3 = e.getBlocks().iterator();

            while(var3.hasNext()) {
                Block block = (Block)var3.next();
                Location loc = block.getLocation();
                CheckRegion cr = new CheckRegion(loc);
                if (cr.isBlockIsOtherRegionIn()) {
                    e.setCancelled(true);
                }
            }

        }
    }

    @EventHandler
    public void onPiston2(BlockPistonRetractEvent e) {
        CreateData cd = new CreateData();
        if (cd.isProtectPiston()) {
            Iterator var3 = e.getBlocks().iterator();

            while(var3.hasNext()) {
                Block block = (Block)var3.next();
                Location loc = block.getLocation();
                CheckRegion cr = new CheckRegion(loc);
                if (cr.isBlockIsOtherRegionIn()) {
                    e.setCancelled(true);
                }
            }

        }
    }

    @EventHandler
    public void getEXP(PlayerExpChangeEvent event) {
        CapitalismMinecraft plugin = CapitalismMinecraft.instance;
        Player p = event.getPlayer();
        plugin.wallet.AddMoney(p, event.getAmount());
    }

    @EventHandler
    public void onBurn(BlockBurnEvent e) {
        CreateData cd = new CreateData();
        if (cd.isProtectFireSpread()) {
            CheckRegion cr = new CheckRegion(e.getBlock().getLocation());
            if (cr.isBlockIsOtherRegionIn()) {
                e.setCancelled(true);
            }

        }
    }

    @EventHandler
    public void onBlockSpread(BlockSpreadEvent e) {
        CreateData cd = new CreateData();
        if (cd.isProtectFireSpread()) {
            if (e.getSource().getType().equals(Material.FIRE)) {
                CheckRegion cr = new CheckRegion(e.getSource().getLocation());
                if (cr.isBlockIsOtherRegionIn()) {
                    e.setCancelled(true);
                }

            }
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        CreateData cd = new CreateData();
        boolean explode = cd.isProtectExplosion();
        if (explode) {
            Block[] var4 = (Block[])e.blockList().toArray(new Block[e.blockList().size()]);
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Block block = var4[var6];
                CheckRegion cr = new CheckRegion(block.getLocation());
                if (cr.isBlockIsOtherRegionIn()) {
                    e.setCancelled(true);
                    e.blockList().remove(block);
                }
            }

        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        Player p = e.getPlayer();

        //곡괭이 스킬
        pl.skill.pickaxeSkill.skill_4(p);

        //도끼 스킬
        pl.skill.axeSkill.skill_4(p);

        //낚싯대 스킬
        pl.skill.fishingRodSkill.skill_3(p);

        //괭이 스킬
        pl.skill.hoeSkill.skill_4(p);

        if (p.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            int x = p.getLocation().getBlockX(), z = p.getLocation().getBlockZ();
            x = x<0?-x:x;
            z = z<0?-z:z;

            if (x < 20 && z < 20) {
                if (!p.isOp()) p.setGameMode(GameMode.ADVENTURE);
            }
            else {
                if (!p.isOp()) p.setGameMode(GameMode.SURVIVAL);
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;

        Location loc = e.getBlock().getLocation();
        Player p = e.getPlayer();
        CheckRegion cr = new CheckRegion(loc);
        if (cr.isBlockIsInOutLine()) {
            e.setCancelled(true);
            p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&f해당 외각 블럭은 부술 수 없습니다."));
            return;
        } else {
            if (!p.isOp() && cr.isBlockIsOtherRegionInIfOwner(p)) {
                e.setCancelled(true);
                p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&f해당 위치는 건차 지역 범위 내 입니다."));
                return;
            }
        }

        //곡괭이 스킬
        pl.skill.pickaxeSkill.skill_1(p);
        pl.skill.pickaxeSkill.skill_2(p);

        for (Location t : pl.skill.pickaxeSkill.glasses) {
            if (loc.equals(t)) {
                e.setDropItems(false);
            }
        }

        //도끼 스킬
        pl.skill.axeSkill.skill_1(p);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Location loc = e.getBlock().getLocation();
        Player p = e.getPlayer();
        CheckRegion cr = new CheckRegion(loc);
        if (cr.isBlockIsInOutLine()) {
            e.setCancelled(true);
            p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&f해당 외각 블럭에 설치할 수 없습니다."));
        } else {
            if (!p.isOp() && cr.isBlockIsOtherRegionInIfOwner(p)) {
                e.setCancelled(true);
                p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&f해당 위치는 건차 지역 범위 내 입니다."));
            }

        }
    }

    @EventHandler
    public void onBreakItem(PlayerItemBreakEvent e) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        Player p = e.getPlayer();
        ItemStack stack = e.getBrokenItem();

        if (pl.skill.check_level(stack) > 0) {
            if (stack.getType().equals(Material.IRON_HOE)
            || stack.getType().equals(Material.STONE_HOE)
            || stack.getType().equals(Material.WOODEN_HOE)
            || stack.getType().equals(Material.GOLDEN_HOE)
            || stack.getType().equals(Material.DIAMOND_HOE)
            || stack.getType().equals(Material.NETHERITE_HOE)) { // 괭이
                
            }

            if (stack.getType().equals(Material.IRON_SWORD)
            || stack.getType().equals(Material.STONE_SWORD)
            || stack.getType().equals(Material.WOODEN_SWORD)
            || stack.getType().equals(Material.GOLDEN_SWORD)
            || stack.getType().equals(Material.DIAMOND_SWORD)
            || stack.getType().equals(Material.NETHERITE_SWORD)) { // 검
                
            }

            if (stack.getType().equals(Material.BOW) || stack.getType().equals(Material.CROSSBOW)) { //활, 쇠뇌
                
            }

            if (stack.getType().equals(Material.FISHING_ROD)) { //낚싯대
                
            }

            if (stack.getType().equals(Material.IRON_PICKAXE)
            || stack.getType().equals(Material.STONE_PICKAXE)
            || stack.getType().equals(Material.WOODEN_PICKAXE)
            || stack.getType().equals(Material.GOLDEN_PICKAXE)
            || stack.getType().equals(Material.DIAMOND_PICKAXE)
            || stack.getType().equals(Material.NETHERITE_PICKAXE)) { //곡괭이
                
            }

            if (stack.getType().equals(Material.IRON_AXE)
            || stack.getType().equals(Material.STONE_AXE)
            || stack.getType().equals(Material.WOODEN_AXE)
            || stack.getType().equals(Material.GOLDEN_AXE)
            || stack.getType().equals(Material.DIAMOND_AXE)
            || stack.getType().equals(Material.NETHERITE_AXE)) { //도끼
                
            }
        }
    }
}
