package _.capitalismminecraft;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class BowSkill {
    boolean check_item(ItemStack stack) {
        if (stack.getType().equals(Material.BOW) || stack.getType().equals(Material.CROSSBOW)) { //활, 쇠뇌
            return true;
        }
        
        return false;
    }

    public void skill_1(Player p) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 1) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 1));
            }
        }
    }

    public void skill_2(Player p, ProjectileHitEvent event) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 2) {
                event.getEntity().setFreezeTicks(420);
                event.getEntity().getWorld().spawnParticle(Particle.SNOW_SHOVEL, event.getEntity().getLocation(), 50, .2, 1.5, .2);
            }
        }
    }

    public void skill_3(Player p) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 3) {
                if (p.getCooldown(p.getInventory().getItemInMainHand().getType()) <= 0) {
                    Vector dir = p.getLocation().getDirection();
                    dir = new Vector(dir.getX()*-2, 0.5, dir.getZ()*-2);
                    p.setVelocity(dir);
                    p.setCooldown(item.getType(), 100);
                }
            }
        }
    }

    public void skill_4(Player p) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 4) {
                for (Entity t : p.getWorld().getNearbyEntities(p.getLocation(), 25, 25, 25)) {
                    if (!t.getLocation().equals(p.getLocation())) {
                        if (t.getFreezeTicks() > 140) {
                            ((LivingEntity) t).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 255));
                            Vector dir = t.getVelocity();
                            dir = new Vector(dir.getX()*-1, 1, dir.getZ()*-1);
                            t.setVelocity(dir);
                            t.getWorld().spawnParticle(Particle.SWEEP_ATTACK, t.getLocation(), 1);
                        }
                    }
                }
            }
        }
    }
}
