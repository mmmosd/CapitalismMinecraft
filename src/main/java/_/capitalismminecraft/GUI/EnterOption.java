package _.capitalismminecraft.GUI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import _.capitalismminecraft.CapitalismMinecraft;
import _.capitalismminecraft.Data.RegionData;
import _.capitalismminecraft.Items.CustomStack;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class EnterOption {
   private final String region;

   public EnterOption(String region) {
      this.region = region;
   }

   public void reloadGUI(String value) {
      Iterator var2;
      Player p;
      ArmorStand armorStand;
      if (value.equals("Option")) {
         var2 = CapitalismMinecraft.optiongui.keySet().iterator();

         while(var2.hasNext()) {
            p = (Player)var2.next();
            CapitalismMinecraft.optiongui.remove(p);
            armorStand = (ArmorStand)CapitalismMinecraft.armorstandData.get(p);
            p.openInventory(this.OptionGUI());
            CapitalismMinecraft.armorstandData.put(p, armorStand);
            CapitalismMinecraft.optiongui.put(p, this.region);
         }
      } else if (value.equals("Add")) {
         var2 = CapitalismMinecraft.addgui.keySet().iterator();

         while(var2.hasNext()) {
            p = (Player)var2.next();
            CapitalismMinecraft.optiongui.remove(p);
            armorStand = (ArmorStand)CapitalismMinecraft.armorstandData.get(p);
            p.openInventory(this.AddGUI());
            CapitalismMinecraft.armorstandData.put(p, armorStand);
            CapitalismMinecraft.addgui.put(p, this.region);
         }
      }

   }

   public void openGUI(Player p, String value) {
      ArmorStand armorStand;
      if (value.equals("Option")) {
         if (!CapitalismMinecraft.addgui.containsKey(p)) {
            p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&f건차 옵션을 열었습니다."));
         }

         if (CapitalismMinecraft.armorstandData.containsKey(p)) {
            armorStand = (ArmorStand)CapitalismMinecraft.armorstandData.get(p);
            p.openInventory(this.OptionGUI());
            CapitalismMinecraft.armorstandData.put(p, armorStand);
            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
            CapitalismMinecraft.optiongui.put(p, this.region);
         } else {
            p.openInventory(this.OptionGUI());
            p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
            CapitalismMinecraft.optiongui.put(p, this.region);
         }
      } else if (value.equals("Add")) {
         armorStand = (ArmorStand)CapitalismMinecraft.armorstandData.get(p);
         p.openInventory(this.AddGUI());
         CapitalismMinecraft.armorstandData.put(p, armorStand);
         p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
         CapitalismMinecraft.addgui.put(p, this.region);
      } else {
         p.sendMessage(CapitalismMinecraft.ColorChat("&c잘못된 입력값입니다."));
      }

   }

   public Inventory OptionGUI() {
      Inventory inv = Bukkit.createInventory((InventoryHolder)null, 45, CapitalismMinecraft.ColorChat("&8&l건차 옵션"));
      RegionData data = new RegionData(this.region);
      List<String> list = data.getOwners();
      if (list != null && list.contains(String.valueOf(data.getBuilder()))) {
         list.remove(String.valueOf(data.getBuilder()));
      }

      int i;

      for (i = 0; i < 45; i++) {
         inv.setItem(i, CapitalismMinecraft.instance.menu.button_items.get(0));
      }

      for(i = 0; i < 9; ++i) {
         inv.setItem(i, CustomStack.nullglass());
      }

      for(i = 36; i < 45; ++i) {
         if (i == 36) {
            inv.setItem(i, CustomStack.addplayer());
         } else if (i == 44) {
            inv.setItem(i, CustomStack.removeregion());
         } else {
            inv.setItem(i, CustomStack.nullglass());
         }
      }

      for(i = 9; i < 36 && list != null && list.size() >= i - 8; ++i) {
         inv.setItem(i, CustomStack.playerskull(UUID.fromString((String)list.get(i - 9))));
      }

      return inv;
   }

   public Inventory AddGUI() {
      Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, CapitalismMinecraft.ColorChat("&8&l건차 플레이어 추가"));
      RegionData data = new RegionData(this.region);
      List<String> list = data.getOwners();
      List<UUID> plist = new ArrayList();
      OfflinePlayer[] var5 = Bukkit.getOfflinePlayers();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         OfflinePlayer p = var5[var7];
         if ((list == null || !list.contains(String.valueOf(p.getUniqueId()))) && !p.getUniqueId().equals(data.getBuilder())) {
            plist.add(p.getUniqueId());
         }
      }

      int i;

      for (i = 0; i < 54; i++) {
         inv.setItem(i, CapitalismMinecraft.instance.menu.button_items.get(0));
      }

      for(i = 0; i < 9; ++i) {
         inv.setItem(i, CustomStack.nullglass());
      }

      for(i = 45; i < 54; ++i) {
         if (i == 45) {
            inv.setItem(i, CustomStack.previous());
         } else {
            inv.setItem(i, CustomStack.nullglass());
         }
      }

      for(i = 9; i < 45 && plist != null && plist.size() >= i - 8; ++i) {
         inv.setItem(i, CustomStack.addplayerskull((UUID)plist.get(i - 9)));
      }

      return inv;
   }
}
