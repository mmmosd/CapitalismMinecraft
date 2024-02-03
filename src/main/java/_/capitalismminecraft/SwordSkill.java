package _.capitalismminecraft;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class SwordSkill {
    boolean check_item(ItemStack stack) {
        if (stack.getType().equals(Material.IRON_SWORD)
        || stack.getType().equals(Material.STONE_SWORD)
        || stack.getType().equals(Material.WOODEN_SWORD)
        || stack.getType().equals(Material.GOLDEN_SWORD)
        || stack.getType().equals(Material.DIAMOND_SWORD)
        || stack.getType().equals(Material.NETHERITE_SWORD)) { // 검
            return true;
        }
        
        return false;
    }

    public void skill_1(Player p) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 1) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1, 6));
            }
        }
    }

    public void skill_2(Player p) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 2) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20, 0));
            }
        }
    }

    public void skill_3(Player p) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 3) {
                if (p.getCooldown(p.getInventory().getItemInMainHand().getType()) <= 0) {
                    Vector v = p.getLocation().getDirection();
                    p.setVelocity(new Vector(v.getX(), 0, v.getZ()));
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 1);
                    p.setCooldown(item.getType(), 80);
                }
            }
        }
    }

    public void skill_4(Player p) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 4) {
                if (p.getCooldown(p.getInventory().getItemInMainHand().getType()) <= 0) {
                    p.spawnParticle(Particle.LANDING_LAVA, p.getLocation(), 500, 5, 0, 5);
                    p.getWorld().playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 5, 1);
                    for (Entity t : p.getWorld().getNearbyEntities(p.getLocation(), 5, 5, 5)) {
                        if (!t.getLocation().equals(p.getLocation()) && !t.getType().equals(EntityType.EXPERIENCE_ORB) && !t.getType().equals(EntityType.DROPPED_ITEM)) {
                            p.attack(t);
                            t.getWorld().spawnParticle(Particle.SWEEP_ATTACK, t.getLocation(), 1);
                        }
                    }
                    p.setCooldown(item.getType(), 200);
                }
            }
        }
    }
}
