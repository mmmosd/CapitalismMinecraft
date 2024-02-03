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
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import _.capitalismminecraft.Shop.ESItem;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;

public class Skill {
    public class CoolDown {
        Material mat;
        int time;

        public CoolDown(Material mat, int time) {
            this.mat = mat;
            this.time = time;
        }
    }

    public PickaxeSkill pickaxeSkill = new PickaxeSkill();
    public AxeSkill axeSkill = new AxeSkill();
    public HoeSkill hoeSkill = new HoeSkill();
    public SwordSkill swordSkill = new SwordSkill();
    public BowSkill bowSkill = new BowSkill();
    public FishingRodSkill fishingRodSkill = new FishingRodSkill();

    List<Integer> level = new ArrayList<Integer>();

    public HashMap<UUID, Integer> slot = new HashMap<UUID, Integer>();
    public HashMap<UUID, List<CoolDown>> cooldown = new HashMap<UUID, List<CoolDown>>();

    public void SaveLevel() {
        CapitalismMinecraft plugin = CapitalismMinecraft.instance;

        Bukkit.getScheduler().runTaskTimerAsynchronously(CapitalismMinecraft.instance, new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < level.size(); i++) {
                    plugin.getConfig().set("code|" + i, level.get(i)); // "code|(number)" : item (config)
                }
                plugin.getConfig().set("code_count", level.size());

                plugin.saveConfig();
            }
        }, 20 * 30, 20 * 30);
    }

    public void LoadLevel() {
        CapitalismMinecraft plugin = CapitalismMinecraft.instance;
        int count = plugin.getConfig().getInt("code_count");

        for (int i = 0; i < count; i++) {
            level.add(i, plugin.getConfig().getInt("code|" + i));
        }
    }

    public void OpenUpgradeGUI(Player p, ItemStack upgrade_stack, boolean can_upgrade) {
        Inventory inventory = Bukkit.createInventory(p.getInventory().getHolder(), 27, Component.text("강화"));

        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, CapitalismMinecraft.instance.menu.button_items.get(0));
        }

        ItemStack stack = new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
        ItemMeta meta = stack.getItemMeta();

        meta.displayName(Component.text(ChatColor.LIGHT_PURPLE + "강화하기"));
        List<Component> lores = new ArrayList<>();

        slot.putIfAbsent(p.getUniqueId(), 0);
        
        if (!can_upgrade) {
            lores.add(Component.text(ChatColor.RED + "강화 가능 아이템을 위에 올려두세요!"));
        }
        else {
            int level = check_level(upgrade_stack);

            if (level == -1) {
                lores.add(Component.text(ChatColor.RED + "error"));
            }

            if (level == 4) {
                lores.add(Component.text(ChatColor.RED + "이 아이템은 더 이상 강화할 수 없습니다."));
            }

            if (level == 0) {
                lores.add(Component.text(ChatColor.GOLD + "가격 : 1000🪙"));
            }
            else if (level == 1) {
                lores.add(Component.text(ChatColor.GOLD + "가격 : 5000🪙"));
            }
            else if (level == 2) {
                lores.add(Component.text(ChatColor.GOLD + "가격 : 10000🪙"));
            }
            else if (level == 3) {
                lores.add(Component.text(ChatColor.GOLD + "가격 : 30000🪙"));
            }

        }

        lores.add(Component.text(""));
        lores.add(Component.text(ChatColor.BLUE + "강화 가능 아이템"));
        lores.add(Component.text(""));
        lores.add(Component.text(ChatColor.GRAY + "검"));
        lores.add(Component.text(ChatColor.GRAY + "곡괭이"));
        lores.add(Component.text(ChatColor.GRAY + "괭이"));
        lores.add(Component.text(ChatColor.GRAY + "도끼"));
        lores.add(Component.text(ChatColor.GRAY + "낚싯대"));
        lores.add(Component.text(ChatColor.GRAY + "활, 쇠뇌"));
        stack.setItemMeta(meta);
        stack.lore(lores);
        stack.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        inventory.setItem(22, stack);
        inventory.setItem(13, upgrade_stack);

        inventory.setItem(26, CapitalismMinecraft.instance.menu.button_items.get(1));

        p.closeInventory();
        p.openInventory(inventory);
        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE, 1, 1);
    }

    public boolean update_inventory(Player p, ItemStack stack) {
        if (stack.getType().equals(Material.IRON_PICKAXE)
        || stack.getType().equals(Material.STONE_PICKAXE)
        || stack.getType().equals(Material.WOODEN_PICKAXE)
        || stack.getType().equals(Material.GOLDEN_PICKAXE)
        || stack.getType().equals(Material.DIAMOND_PICKAXE)
        || stack.getType().equals(Material.NETHERITE_PICKAXE)

        || stack.getType().equals(Material.IRON_HOE)
        || stack.getType().equals(Material.STONE_HOE)
        || stack.getType().equals(Material.WOODEN_HOE)
        || stack.getType().equals(Material.GOLDEN_HOE)
        || stack.getType().equals(Material.DIAMOND_HOE)
        || stack.getType().equals(Material.NETHERITE_HOE)
        
        || stack.getType().equals(Material.IRON_SWORD)
        || stack.getType().equals(Material.STONE_SWORD)
        || stack.getType().equals(Material.WOODEN_SWORD)
        || stack.getType().equals(Material.GOLDEN_SWORD)
        || stack.getType().equals(Material.DIAMOND_SWORD)
        || stack.getType().equals(Material.NETHERITE_SWORD)
        
        || stack.getType().equals(Material.IRON_AXE)
        || stack.getType().equals(Material.STONE_AXE)
        || stack.getType().equals(Material.WOODEN_AXE)
        || stack.getType().equals(Material.GOLDEN_AXE)
        || stack.getType().equals(Material.DIAMOND_AXE)
        || stack.getType().equals(Material.NETHERITE_AXE)
        || stack.getType().equals(Material.FISHING_ROD)
        
        || stack.getType().equals(Material.BOW) 
        || stack.getType().equals(Material.CROSSBOW)) { // 강화 가능 아이템
            OpenUpgradeGUI(p, stack, true);
            return true;
        }

        OpenUpgradeGUI(p, stack, false);
        return false;
    }

    public int check_level(ItemStack stack) {
        if (stack.getType().equals(Material.IRON_PICKAXE)
        || stack.getType().equals(Material.STONE_PICKAXE)
        || stack.getType().equals(Material.WOODEN_PICKAXE)
        || stack.getType().equals(Material.GOLDEN_PICKAXE)
        || stack.getType().equals(Material.DIAMOND_PICKAXE)
        || stack.getType().equals(Material.NETHERITE_PICKAXE)

        || stack.getType().equals(Material.IRON_HOE)
        || stack.getType().equals(Material.STONE_HOE)
        || stack.getType().equals(Material.WOODEN_HOE)
        || stack.getType().equals(Material.GOLDEN_HOE)
        || stack.getType().equals(Material.DIAMOND_HOE)
        || stack.getType().equals(Material.NETHERITE_HOE)
        
        || stack.getType().equals(Material.IRON_SWORD)
        || stack.getType().equals(Material.STONE_SWORD)
        || stack.getType().equals(Material.WOODEN_SWORD)
        || stack.getType().equals(Material.GOLDEN_SWORD)
        || stack.getType().equals(Material.DIAMOND_SWORD)
        || stack.getType().equals(Material.NETHERITE_SWORD)
        
        || stack.getType().equals(Material.IRON_AXE)
        || stack.getType().equals(Material.STONE_AXE)
        || stack.getType().equals(Material.WOODEN_AXE)
        || stack.getType().equals(Material.GOLDEN_AXE)
        || stack.getType().equals(Material.DIAMOND_AXE)
        || stack.getType().equals(Material.NETHERITE_AXE)
        || stack.getType().equals(Material.FISHING_ROD)
        
        || stack.getType().equals(Material.BOW) 
        || stack.getType().equals(Material.CROSSBOW)) { // 강화 가능 아이템
            NamespacedKey key = new NamespacedKey(CapitalismMinecraft.getPlugins(), "Skill");

            // if (!stack.hasItemMeta()) return -1;
            if (!stack.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) return 0; // 아이템에 코드가 없을 때

            int code = (Integer)stack.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
            return level.get(code);
        }

        return -1;
    }

    public void upgrade(Player p, ItemStack stack) {
        if (stack == null) {
            p.sendMessage(Component.text(ChatColor.RED + "강화 가능 아이템을 위에 올려두세요!"));
            p.playSound(p.getLocation(), Sound.BLOCK_CHAIN_PLACE, 1, 1);
            return;
        }

        NamespacedKey key = new NamespacedKey(CapitalismMinecraft.getPlugins(), "Skill");
        if (!stack.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) { // 아이템에 코드가 없을 때
            ItemMeta im = stack.getItemMeta();
            im.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, level.size());
            stack.setItemMeta(im);
            level.add(0);
        }

        int code = stack.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.INTEGER);

        level.set(code, level.get(code) + 1);

        List<Component> lores = new ArrayList<>();
        lores.add(Component.text(ChatColor.DARK_PURPLE + "[아이템 레벨 " + level.get(code) + "차]"));
        stack.lore(lores);

        p.getInventory().setItem(slot.get(p.getUniqueId()), stack);

        p.closeInventory();

        if (stack.getType().equals(Material.IRON_PICKAXE)
        || stack.getType().equals(Material.STONE_PICKAXE)
        || stack.getType().equals(Material.WOODEN_PICKAXE)
        || stack.getType().equals(Material.GOLDEN_PICKAXE)
        || stack.getType().equals(Material.DIAMOND_PICKAXE)
        || stack.getType().equals(Material.NETHERITE_PICKAXE)) { //곡괭이
            p.getServer().sendMessage(Component.text(ChatColor.LIGHT_PURPLE + p.getName() + ChatColor.YELLOW + "님이 [곡괭이 " + level.get(code) + "차 강화] 완료!"));
            p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
            return;
        }

        if (stack.getType().equals(Material.IRON_HOE)
        || stack.getType().equals(Material.STONE_HOE)
        || stack.getType().equals(Material.WOODEN_HOE)
        || stack.getType().equals(Material.GOLDEN_HOE)
        || stack.getType().equals(Material.DIAMOND_HOE)
        || stack.getType().equals(Material.NETHERITE_HOE)) { // 괭이
            p.getServer().sendMessage(Component.text(ChatColor.LIGHT_PURPLE + p.getName() + ChatColor.YELLOW + "님이 [괭이 " + level.get(code) + "차 강화] 완료!"));
            p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
            return;
        }
        
        if (stack.getType().equals(Material.IRON_SWORD)
        || stack.getType().equals(Material.STONE_SWORD)
        || stack.getType().equals(Material.WOODEN_SWORD)
        || stack.getType().equals(Material.GOLDEN_SWORD)
        || stack.getType().equals(Material.DIAMOND_SWORD)
        || stack.getType().equals(Material.NETHERITE_SWORD)) { // 검
            p.getServer().sendMessage(Component.text(ChatColor.LIGHT_PURPLE + p.getName() + ChatColor.YELLOW + "님이 [검 " + level.get(code) + "차 강화] 완료!"));
            p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
            return;
        }

        if (stack.getType().equals(Material.IRON_AXE)
        || stack.getType().equals(Material.STONE_AXE)
        || stack.getType().equals(Material.WOODEN_AXE)
        || stack.getType().equals(Material.GOLDEN_AXE)
        || stack.getType().equals(Material.DIAMOND_AXE)
        || stack.getType().equals(Material.NETHERITE_AXE)) { //도끼
            p.getServer().sendMessage(Component.text(ChatColor.LIGHT_PURPLE + p.getName() + ChatColor.YELLOW + "님이 [도끼 " + level.get(code) + "차 강화] 완료!"));
            p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
            return;
        }
        
        if (stack.getType().equals(Material.FISHING_ROD)) { //낚싯대
            p.getServer().sendMessage(Component.text(ChatColor.LIGHT_PURPLE + p.getName() + ChatColor.YELLOW + "님이 [낚싯대 " + level.get(code) + "차 강화] 완료!"));
            p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
            return;
        }
        
        if (stack.getType().equals(Material.BOW) || stack.getType().equals(Material.CROSSBOW)) { //활, 쇠뇌
            p.getServer().sendMessage(Component.text(ChatColor.LIGHT_PURPLE + p.getName() + ChatColor.YELLOW + "님이 [활 또는 쇠뇌 " + level.get(code) + "차 강화] 완료!"));
            p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
            return;
        }

        p.sendMessage(Component.text(ChatColor.RED + "이 아이템은 강화할 수 없습니다."));
        p.playSound(p.getLocation(), Sound.BLOCK_CHAIN_PLACE, 1, 1);
    }
}
