package _.capitalismminecraft;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.kyori.adventure.text.event.HoverEvent.Action;

public class AxeSkill {
    boolean check_item(ItemStack stack) {
        if (stack.getType().equals(Material.IRON_AXE)
        || stack.getType().equals(Material.STONE_AXE)
        || stack.getType().equals(Material.WOODEN_AXE)
        || stack.getType().equals(Material.GOLDEN_AXE)
        || stack.getType().equals(Material.DIAMOND_AXE)
        || stack.getType().equals(Material.NETHERITE_AXE)) { //도끼
            return true;
        }
        
        return false;
    }

    public void skill_1(Player p) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 1) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 80, 1));
            }
        }
    }

    public void skill_2(Player p) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 2) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20, 2));
            }
        }
    }

    class dir {
        int x, y, z;

        public dir(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    dir[] Dir = {
        new dir(1, 0, 0),
        new dir(0, 1, 0),
        new dir(0, 0, 1),
        new dir(-1, 0, 0),
        new dir(0, -1, 0),
        new dir(0, 0, -1),
    };

    public void skill_3(Player p, PlayerInteractEvent event) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 3) {
                if (p.getCooldown(p.getInventory().getItemInMainHand().getType()) <= 0) {
                    if (event.getAction().isRightClick() && event.getClickedBlock() != null) {
                        Location loc = event.getClickedBlock().getLocation();
                        List<Location> visited = new ArrayList<Location>();
                        Queue<Location> q = new LinkedList<>();

                        if (p.getWorld().getBlockAt(loc).getType().equals(Material.OAK_LOG)
                        || p.getWorld().getBlockAt(loc).getType().equals(Material.BIRCH_LOG)
                        || p.getWorld().getBlockAt(loc).getType().equals(Material.ACACIA_LOG)
                        || p.getWorld().getBlockAt(loc).getType().equals(Material.CHERRY_LOG)
                        || p.getWorld().getBlockAt(loc).getType().equals(Material.JUNGLE_LOG)
                        || p.getWorld().getBlockAt(loc).getType().equals(Material.SPRUCE_LOG)
                        || p.getWorld().getBlockAt(loc).getType().equals(Material.DARK_OAK_LOG)
                        || p.getWorld().getBlockAt(loc).getType().equals(Material.MANGROVE_LOG)
                        || p.getWorld().getBlockAt(loc).getType().equals(Material.WARPED_STEM)
                        || p.getWorld().getBlockAt(loc).getType().equals(Material.CRIMSON_STEM)) {
                            q.add(loc);
                            visited.add(loc);
                            p.getWorld().getBlockAt(loc).breakNaturally(p.getActiveItem(), true);
                            p.setCooldown(item.getType(), 2400);
                        }

                        while (!q.isEmpty()) {
                            Location now = q.remove();

                            for (int i = 0; i < 6; i++) {
                                Location move = new Location(p.getWorld(), now.getBlockX() + Dir[i].x, now.getBlockY() + Dir[i].y, now.getBlockZ() + Dir[i].z);

                                if (visited.contains(move)) continue;

                                if (p.getWorld().getBlockAt(move).getType().equals(Material.OAK_LOG)
                                || p.getWorld().getBlockAt(move).getType().equals(Material.BIRCH_LOG)
                                || p.getWorld().getBlockAt(move).getType().equals(Material.ACACIA_LOG)
                                || p.getWorld().getBlockAt(move).getType().equals(Material.CHERRY_LOG)
                                || p.getWorld().getBlockAt(move).getType().equals(Material.JUNGLE_LOG)
                                || p.getWorld().getBlockAt(move).getType().equals(Material.SPRUCE_LOG)
                                || p.getWorld().getBlockAt(move).getType().equals(Material.DARK_OAK_LOG)
                                || p.getWorld().getBlockAt(move).getType().equals(Material.MANGROVE_LOG)
                                || p.getWorld().getBlockAt(move).getType().equals(Material.WARPED_STEM)
                                || p.getWorld().getBlockAt(move).getType().equals(Material.CRIMSON_STEM)) {
                                    q.add(move);
                                    visited.add(move);
                                    p.getWorld().getBlockAt(move).breakNaturally(p.getActiveItem(), true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void skill_4(Player p) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 4) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 1));
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20, 0));
            }
        }
    }
}
