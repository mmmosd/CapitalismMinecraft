package _.capitalismminecraft;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.kyori.adventure.text.Component;

public class HoeSkill {
    boolean check_item(ItemStack stack) {
        if (stack.getType().equals(Material.IRON_HOE)
        || stack.getType().equals(Material.STONE_HOE)
        || stack.getType().equals(Material.WOODEN_HOE)
        || stack.getType().equals(Material.GOLDEN_HOE)
        || stack.getType().equals(Material.DIAMOND_HOE)
        || stack.getType().equals(Material.NETHERITE_HOE)) { // 괭이
            return true;
        }
        
        return false;
    }

    public void skill_1(Player p, PlayerInteractEvent event) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 1) {
                if (event.getAction().isRightClick() && event.getClickedBlock() != null) {
                    if (event.getClickedBlock().getType().equals(Material.COARSE_DIRT)
                    || event.getClickedBlock().getType().equals(Material.DIRT)
                    || event.getClickedBlock().getType().equals(Material.ROOTED_DIRT)
                    || event.getClickedBlock().getType().equals(Material.GRASS_BLOCK)) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 1));
                    }
                }
            }
        }
    }

    public void skill_2(Player p, PlayerInteractEvent event) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 2) {
                if (event.getAction().isLeftClick() && event.getClickedBlock() != null) {
                    if (event.getClickedBlock().getType().equals(Material.WHEAT)
                    || event.getClickedBlock().getType().equals(Material.CARROTS)
                    || event.getClickedBlock().getType().equals(Material.POTATOES)
                    || event.getClickedBlock().getType().equals(Material.BEETROOTS)) {
                        BlockData data = event.getClickedBlock().getBlockData();

                        if (data instanceof Ageable) {
                            if (event.getClickedBlock().getType().equals(Material.WHEAT)
                            || event.getClickedBlock().getType().equals(Material.CARROTS)
                            || event.getClickedBlock().getType().equals(Material.POTATOES)) {
                                if (((Ageable) data).getAge() >= 7) {
                                    ExperienceOrb exp = (ExperienceOrb) Bukkit.getServer().getWorld("world").spawnEntity(event.getClickedBlock().getLocation(), EntityType.EXPERIENCE_ORB);
                                    exp.setExperience((int)(Math.random()*3 + 3));
                                    event.getClickedBlock().breakNaturally(p.getActiveItem(), true);
                                }
                            }

                            if (event.getClickedBlock().getType().equals(Material.BEETROOTS)) {
                                if (((Ageable) data).getAge() >= 3) {
                                    ExperienceOrb exp = (ExperienceOrb) Bukkit.getServer().getWorld("world").spawnEntity(event.getClickedBlock().getLocation(), EntityType.EXPERIENCE_ORB);
                                    exp.setExperience((int)(Math.random()*3 + 3));
                                    event.getClickedBlock().breakNaturally(p.getActiveItem(), true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void skill_3(Player p, PlayerInteractEvent event) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 3) {
                if (event.getAction().isRightClick() && event.getClickedBlock() != null) {
                    if (p.getCooldown(p.getInventory().getItemInMainHand().getType()) <= 0) {
                        if (event.getClickedBlock().getType().equals(Material.WHEAT)
                        || event.getClickedBlock().getType().equals(Material.CARROTS)
                        || event.getClickedBlock().getType().equals(Material.POTATOES)
                        || event.getClickedBlock().getType().equals(Material.BEETROOTS)) {
                            Location loc = event.getClickedBlock().getLocation();
                            int x = loc.getBlockX(), y = loc.getBlockY(), z = loc.getBlockZ();

                            if (p.getWorld().getBlockAt(x, y+1, z).getType().equals(Material.AIR)) {
                                p.getWorld().setType(x, y+1, z, Material.LIGHT);
                                p.setCooldown(item.getType(), 200);
                                p.playSound(loc, Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                                p.spawnParticle(Particle.END_ROD, loc.toCenterLocation(), 50, 0, 0, 0);
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
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20, 0));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 2));
            }
        }
    }
}
