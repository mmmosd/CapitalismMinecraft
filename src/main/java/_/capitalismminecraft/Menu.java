package _.capitalismminecraft;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
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
        Inventory inventory = Bukkit.createInventory(p.getInventory().getHolder(), 9, Component.text("메뉴"));

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, button_items.get(0));
        }

        inventory.setItem(4, button_items.get(9));
        inventory.setItem(8, button_items.get(10));

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

    public void MakeArmorStand(Location centerloc, int type) {
        centerloc = centerloc.toCenterLocation();
        if (centerloc.getNearbyEntities(centerloc.x(), centerloc.y(), centerloc.z()).size() > 0) {
            for (Entity e : centerloc.getNearbyEntities(centerloc.x(), centerloc.y(), centerloc.z())) {
                if (e instanceof ArmorStand) {
                    ArmorStand armorStand = (ArmorStand)e;
                    if (armorStand.getPersistentDataContainer().has(new NamespacedKey(CapitalismMinecraft.instance, "Menu"), PersistentDataType.STRING)) {
                        String name = armorStand.getPersistentDataContainer().get(new NamespacedKey(CapitalismMinecraft.instance, "Menu"), PersistentDataType.STRING);

                        if (name == null) break;

                        if (type == 1) {
                            if (name.equals("wood")) return;
                        }
                        else if (type == 2) {
                            if (name.equals("mineral")) return;
                        }
                        else if (type == 3) {
                            if (name.equals("food")) return;
                        }
                        else if (type == 4) {
                            if (name.equals("content")) return;
                        }
                        else if (type == 5) {
                            if (name.equals("exchange")) return;
                        }
                        else if (type == 6) {
                            if (name.equals("quest")) return;
                        }
                        else if (type == 7) {
                            if (name.equals("skill")) return;
                        }
                    }
                }
            }
        }

        ArmorStand armorStand = (ArmorStand)centerloc.getWorld().spawnEntity(centerloc, EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setVisualFire(false);
        armorStand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.setPersistent(true);

        if (type == 1) {
            ItemStack is = new ItemStack(Material.OAK_LOG);
            armorStand.setHelmet(is);
            armorStand.getPersistentDataContainer().set(new NamespacedKey(CapitalismMinecraft.instance, "Menu"), PersistentDataType.STRING, "wood");
            armorStand.setCustomNameVisible(true);
            armorStand.customName(Component.text("원목 상점"));
            centerloc.getWorld().getBlockAt(centerloc.clone().add(0, 1, 0)).setType(Material.OAK_LOG);
        }
        else if (type == 2) {
            ItemStack is = new ItemStack(Material.DEEPSLATE_DIAMOND_ORE);
            armorStand.setHelmet(is);
            armorStand.getPersistentDataContainer().set(new NamespacedKey(CapitalismMinecraft.instance, "Menu"), PersistentDataType.STRING, "mineral");
            armorStand.setCustomNameVisible(true);
            armorStand.customName(Component.text("광물 상점"));
        }
        else if (type == 3) {
            ItemStack is = new ItemStack(Material.HAY_BLOCK);
            armorStand.setHelmet(is);
            armorStand.getPersistentDataContainer().set(new NamespacedKey(CapitalismMinecraft.instance, "Menu"), PersistentDataType.STRING, "food");
            armorStand.setCustomNameVisible(true);
            armorStand.customName(Component.text("농수산물 상점"));
        }
        else if (type == 4) {
            ItemStack is = new ItemStack(Material.BEACON);
            armorStand.setHelmet(is);
            armorStand.getPersistentDataContainer().set(new NamespacedKey(CapitalismMinecraft.instance, "Menu"), PersistentDataType.STRING, "content");
            armorStand.setCustomNameVisible(true);
            armorStand.customName(Component.text("컨텐츠 상점"));
        }
        else if (type == 5) {
            ItemStack is = new ItemStack(Material.BARREL);
            armorStand.setHelmet(is);
            armorStand.getPersistentDataContainer().set(new NamespacedKey(CapitalismMinecraft.instance, "Menu"), PersistentDataType.STRING, "exchange");
            armorStand.setCustomNameVisible(true);
            armorStand.customName(Component.text("거래소"));
        }
        else if (type == 6) {
            ItemStack is = new ItemStack(Material.LECTERN);
            armorStand.setHelmet(is);
            armorStand.getPersistentDataContainer().set(new NamespacedKey(CapitalismMinecraft.instance, "Menu"), PersistentDataType.STRING, "quest");
            armorStand.setCustomNameVisible(true);
            armorStand.customName(Component.text("퀘스트"));
        }
        else if (type == 7) {
            ItemStack is = new ItemStack(Material.ENCHANTING_TABLE);
            armorStand.setHelmet(is);
            armorStand.getPersistentDataContainer().set(new NamespacedKey(CapitalismMinecraft.instance, "Menu"), PersistentDataType.STRING, "skill");
            armorStand.setCustomNameVisible(true);
            armorStand.customName(Component.text("강화"));
        }

        centerloc.getWorld().getBlockAt(centerloc.clone().add(0, 1, 0)).setType(Material.END_PORTAL_FRAME);
        centerloc.getWorld().getBlockAt(centerloc.clone().add(0, 2, 0)).setType(Material.LIGHT);
        centerloc.getWorld().getBlockAt(centerloc).setType(Material.BEDROCK);
    }
}
