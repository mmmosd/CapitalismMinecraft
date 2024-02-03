package _.capitalismminecraft;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FishingRodSkill {
    boolean check_item(ItemStack stack) {
        if (stack.getType().equals(Material.FISHING_ROD)) { //낚싯대
            return true;
        }
        
        return false;
    }

    public void skill_1(Player p, PlayerFishEvent event) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 1) {
                if (event.getState().equals(State.FISHING)) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 100, 2));
                }
            }
        }
    }

    public void skill_2(Player p, PlayerFishEvent event) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 2) {
                if (event.getState().equals(State.BITE)) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 20, 1));
                }
            }
        }
    }

    public void skill_3(Player p) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 3) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 20, 0));
                p.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 20, 1));
            }
        }
    }

    public void skill_4(Player p, PlayerFishEvent event) {
        CapitalismMinecraft pl = CapitalismMinecraft.instance;
        ItemStack item = p.getInventory().getItemInMainHand();

        if (check_item(item)) {
            if (pl.skill.check_level(item) >= 4) {
                if (event.getState().equals(State.CAUGHT_FISH)) {
                    Item drop = (Item) Bukkit.getServer().getWorld("world").spawnEntity(event.getHook().getLocation(), EntityType.DROPPED_ITEM);
                    drop.setItemStack(new ItemStack(pl.shop.button_items.get((int)(Math.random()*36)).getType()));
                    drop.setGlowing(true);
                }
            }
        }
    }
}
