package _.capitalismminecraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;


public class PickaxeSkill {
    boolean check_item(ItemStack stack) {
        if (stack.getType().equals(Material.IRON_PICKAXE)
        || stack.getType().equals(Material.STONE_PICKAXE)
        || stack.getType().equals(Material.WOODEN_PICKAXE)
        || stack.getType().equals(Material.GOLDEN_PICKAXE)
        || stack.getType().equals(Material.DIAMOND_PICKAXE)
        || stack.getType().equals(Material.NETHERITE_PICKAXE)) { //곡괭이
            return true;
        }
        
        return false;
    }

    public void skill_1(Player p) { // block break event
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 1) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 0));
            }
        }
    }

    public void skill_2(Player p) { // block break event
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 2) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 1));
            }
        }
    }

    public void skill_3(Player p) { // click
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 3) {
                if (p.getCooldown(p.getInventory().getItemInMainHand().getType()) <= 0) {
                    Location loc = p.getLocation();
                    int x = loc.getBlockX(), y = loc.getBlockY(), z = loc.getBlockZ();

                    HashMap<Location, Material> blocks = new HashMap<Location, Material>();

                    for (int i = x - 2; i <= x + 2; i++) {
                        for (int j = y - 2; j <= y + 2; j++) {
                            for (int k = z - 2; k <= z + 2; k++) {
                                if (p.getWorld().getBlockAt(i, j, k).getType().equals(Material.STONE)
                                || p.getWorld().getBlockAt(i, j, k).getType().equals(Material.TUFF)
                                || p.getWorld().getBlockAt(i, j, k).getType().equals(Material.DEEPSLATE)
                                || p.getWorld().getBlockAt(i, j, k).getType().equals(Material.GRANITE)
                                || p.getWorld().getBlockAt(i, j, k).getType().equals(Material.DIORITE)
                                || p.getWorld().getBlockAt(i, j, k).getType().equals(Material.ANDESITE)
                                || p.getWorld().getBlockAt(i, j, k).getType().equals(Material.NETHERRACK)) {
                                    blocks.put(new Location(p.getWorld(), i, j, k), p.getWorld().getBlockAt(i, j, k).getType());
                                    p.getWorld().getBlockAt(i, j, k).setType(Material.LIGHT_GRAY_STAINED_GLASS);
                                }
                            }
                        }
                    }

                    p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1, 1);
                    p.setCooldown(item.getType(), 200);

                    new BukkitRunnable() {
                        public void run() {
                            for (Entry<Location, Material> info : blocks.entrySet()) {
                                info.getKey().getWorld().getBlockAt(info.getKey()).setType(info.getValue());
                            }
                        }
                    }.runTaskLater(pl, 20);
                }
            }
        }
    }

    public void skill_4(Player p) { // passive
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 4) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 400, 0));
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 0));
                p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20, 0));
            }
        }
    }
}
