package _.capitalismminecraft.Data;

import _.capitalismminecraft.CapitalismMinecraft;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ArmorStand.LockType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class ArmorStandData {
   private final String region;

   public ArmorStandData(String region) {
      this.region = region;
   }

   public void createArmorStand(Location center, Player p) {
      Location centerloc = new Location(center.getWorld(), (double)center.getBlockX() + 0.5D, (double)(center.getBlockY() + 1), (double)center.getBlockZ() + 0.5D);
      ArmorStand armorStand = (ArmorStand)centerloc.getWorld().spawnEntity(centerloc, EntityType.ARMOR_STAND);
      armorStand.setVisible(false);
      armorStand.setGravity(false);
      armorStand.setVisualFire(false);
      ItemStack is = new ItemStack(Material.FURNACE);
      armorStand.setHelmet(is);
      armorStand.addEquipmentLock(EquipmentSlot.HEAD, LockType.ADDING_OR_CHANGING);
      armorStand.setPersistent(true);
      armorStand.getPersistentDataContainer().set(CapitalismMinecraft.key, PersistentDataType.STRING, this.region);
      armorStand.setCustomNameVisible(true);
      armorStand.setCustomName(CapitalismMinecraft.ColorChat("&b" + p.getName()));
   }
}
