package _.capitalismminecraft;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import net.kyori.adventure.text.Component;

public class Event implements Listener {
    CapitalismMinecraft plugin = CapitalismMinecraft.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        plugin.wallet.CreateWallet(p);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        Component clicked_item = event.getCurrentItem().getItemMeta().displayName();
        
        if (clicked_item.equals(plugin.menu.items.get(0).getItemMeta().displayName())) {
            event.setCancelled(true);
        }
        else if (clicked_item.equals(plugin.menu.items.get(1).getItemMeta().displayName())) {
            event.setCancelled(true);
        }
        else if (clicked_item.equals(plugin.menu.items.get(2).getItemMeta().displayName())) {
            event.setCancelled(true);
        }
        else if (clicked_item.equals(plugin.menu.items.get(3).getItemMeta().displayName())) {
            event.setCancelled(true);
        }
        else if (clicked_item.equals(plugin.menu.items.get(4).getItemMeta().displayName())) {
            event.setCancelled(true);
        }
        else if (clicked_item.equals(plugin.menu.items.get(5).getItemMeta().displayName())) {
            event.setCancelled(true);
        }
        else if (clicked_item.equals(plugin.menu.items.get(6).getItemMeta().displayName())) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onAdvancementDone(PlayerAdvancementDoneEvent event) {
        Player p = event.getPlayer();

        event.message(null);

        // event.getAdvancement()
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player p = event.getPlayer();
        plugin.wallet.SubMoney(p, 500);
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
        Player p = event.getPlayer();

        if (p.isSneaking()) {
            plugin.menu.OpenPlayerMenu(p);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
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
        Player p = event.getPlayer();
        plugin.wallet.AddMoney(p, event.getAmount());
    }
}
