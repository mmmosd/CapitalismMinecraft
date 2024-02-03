package _.capitalismminecraft.Build;

import java.util.Iterator;
import java.util.List;

import _.capitalismminecraft.CapitalismMinecraft;
import _.capitalismminecraft.Data.ArmorStandData;
import _.capitalismminecraft.Data.CreateData;
import _.capitalismminecraft.Data.PlayerData;
import _.capitalismminecraft.Data.RegionData;
import _.capitalismminecraft.Utils.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class CreateArea {
   public static boolean CreateNewArea(Player p, Location center, int large) {
      PlayerData pd = new PlayerData(p.getUniqueId());
      CreateData cd = new CreateData();
      CheckRegion cr = new CheckRegion(center);
      if (pd.getRegionAmount() >= cd.getMaxAmount()) {
         p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&f가질 수 있는 건차 한도를 넘었습니다."));
         return false;
      } else {
         Location pos1 = new Location(center.getWorld(), (double)(center.getBlockX() + cd.getSize()), (double)(center.getBlockY() + cd.getHeight()), (double)(center.getBlockZ() + cd.getSize()));
         Location pos2 = new Location(center.getWorld(), (double)(center.getBlockX() - cd.getSize()), (double)center.getBlockY(), (double)(center.getBlockZ() - cd.getSize()));
         if (cd.isPreventRegionCreate() && FileList.getPreventRegionFileList() != null) {
            if (cd.getPreventRegionInOut()) {
               if (!cr.isRegionInPreventCuboid(pos1, pos2)) {
                  p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&f해당 위치에서는 건차를 생성할 수 없습니다."));
                  return false;
               }
            } else if (!cr.isRegionOutPreventCuboid(pos1, pos2)) {
               p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&f해당 위치에서는 건차를 생성할 수 없습니다."));
               return false;
            }
         }

         if (!cr.isCuboidIsOtherRegionIn(pos1, pos2) && !cr.isBlockIsOtherRegionIn()) {
            int randombox = (int)(Math.random() * 100000.0D + 1.0D);
            String newname = String.valueOf(p.getUniqueId()) + randombox;
            RegionData rd = new RegionData(newname);
            if (rd.isExistRegion()) {
               randombox = (int)(Math.random() * 1000000.0D + 1.0D);
               newname = String.valueOf(p.getUniqueId()) + randombox;
            }

            CreateRegion createRegion = new CreateRegion(newname);
            createRegion.createRegionFile(p, center, large);
            BuildRegion br = new BuildRegion(newname);
            br.BuildArea();
            ArmorStandData asd = new ArmorStandData(newname);
            asd.createArmorStand(center, p);
            p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&f성공적으로 건차를 생성하였습니다."));
            p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
            return true;
         } else {
            p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&f해당 위치는 이미 다른 지역 범위 내 입니다."));
            return false;
         }
      }
   }

   public static void DeleteArea(Player p, ArmorStand armorStand, String value) {
      CreateRegion cr = new CreateRegion(value);
      RegionData rd = new RegionData(value);
      if (rd.isExistRegion()) {
         Location pos1 = rd.getFirstPosition();
         Location pos2 = rd.getSecondPosition();
         Cuboid cb = new Cuboid(pos1, pos2);
         List<Location> list = cb.getOutLine();
         Iterator var9 = list.iterator();

         while(var9.hasNext()) {
            Location loc = (Location)var9.next();
            loc.getBlock().setType(Material.AIR);
         }

         cr.removeRegionFile(rd.getBuilder());
         armorStand.remove();
         p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&f건차가 제거되었습니다."));
         p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
         p.closeInventory();
      }
   }
}
