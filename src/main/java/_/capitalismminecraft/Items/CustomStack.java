package _.capitalismminecraft.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import _.capitalismminecraft.CapitalismMinecraft;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

public class CustomStack {
   public static ItemStack RegionProtecter(int size) {
      ItemStack is = new ItemStack(Material.BRICK);
      ItemMeta im = is.getItemMeta();
      im.setDisplayName(CapitalismMinecraft.ColorChat("&b&l건차 생성기"));
      List<String> lore = new ArrayList();
      lore.add(CapitalismMinecraft.ColorChat("&7&l사이즈 : &7" + size));
      im.setLore(lore);
      im.addEnchant(Enchantment.DURABILITY, 1, false);
      im.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
      im.getPersistentDataContainer().set(CapitalismMinecraft.key, PersistentDataType.INTEGER, size);
      is.setItemMeta(im);
      return is;
   }

   public static ItemStack Preventer() {
      ItemStack is = new ItemStack(Material.GOLDEN_AXE);
      ItemMeta im = is.getItemMeta();
      im.setDisplayName(CapitalismMinecraft.ColorChat("&e&lPreventer"));
      List<String> lore = new ArrayList();
      lore.add(CapitalismMinecraft.ColorChat("&7 └ &l좌클릭 시 첫 번째 포지션을 지정함."));
      lore.add(CapitalismMinecraft.ColorChat("&7 └ &l우클릭 시 두 번째 포지션을 지정함."));
      lore.add(CapitalismMinecraft.ColorChat("&7 └ &l포지션을 모두 지정했을 경우 명령어 사용."));
      im.setLore(lore);
      im.addEnchant(Enchantment.DURABILITY, 1, false);
      im.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
      im.getPersistentDataContainer().set(CapitalismMinecraft.key, PersistentDataType.STRING, "Preventer");
      is.setItemMeta(im);
      return is;
   }

   public static ItemStack nullglass() {
      ItemStack is = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
      ItemMeta im = is.getItemMeta();
      im.setDisplayName(CapitalismMinecraft.ColorChat("&f"));
      is.setItemMeta(im);
      return is;
   }

   public static ItemStack addplayer() {
      ItemStack is = new ItemStack(Material.BELL);
      ItemMeta im = is.getItemMeta();
      im.setDisplayName(CapitalismMinecraft.ColorChat("&e&l플레이어 추가"));
      is.setItemMeta(im);
      return is;
   }

   public static ItemStack removeregion() {
      ItemStack is = new ItemStack(Material.BARRIER);
      ItemMeta im = is.getItemMeta();
      im.setDisplayName(CapitalismMinecraft.ColorChat("&c&l삭제하기"));
      is.setItemMeta(im);
      return is;
   }

   public static ItemStack previous() {
      ItemStack is = new ItemStack(Material.RED_STAINED_GLASS);
      ItemMeta im = is.getItemMeta();
      im.setDisplayName(CapitalismMinecraft.ColorChat("&c&l뒤로가기"));
      is.setItemMeta(im);
      return is;
   }

   public static ItemStack addplayerskull(UUID uuid) {
      ItemStack is = new ItemStack(Material.PLAYER_HEAD);
      SkullMeta im = (SkullMeta)is.getItemMeta();
      OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
      im.setOwningPlayer(p);
      im.setDisplayName(CapitalismMinecraft.ColorChat("&b" + p.getName()));
      List<String> lore = new ArrayList();
      lore.add(CapitalismMinecraft.ColorChat("&7 └ &l좌클릭 시 플레이어 목록에 추가됨."));
      im.setLore(lore);
      im.getPersistentDataContainer().set(CapitalismMinecraft.key, PersistentDataType.STRING, String.valueOf(uuid));
      is.setItemMeta(im);
      return is;
   }

   public static ItemStack playerskull(UUID uuid) {
      ItemStack is = new ItemStack(Material.PLAYER_HEAD);
      SkullMeta im = (SkullMeta)is.getItemMeta();
      OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
      im.setOwningPlayer(p);
      im.setDisplayName(CapitalismMinecraft.ColorChat("&f" + p.getName()));
      List<String> lore = new ArrayList();
      lore.add(CapitalismMinecraft.ColorChat("&7 └ &l좌클릭 시 목록에서 제거됨."));
      im.setLore(lore);
      im.getPersistentDataContainer().set(CapitalismMinecraft.key, PersistentDataType.STRING, String.valueOf(uuid));
      is.setItemMeta(im);
      return is;
   }
}
