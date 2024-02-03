package _.capitalismminecraft.Utils;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;

public class Cuboid {
   private final Location pos1;
   private final Location pos2;

   public Cuboid(Location pos1, Location pos2) {
      this.pos1 = pos1;
      this.pos2 = pos2;
   }

   public boolean isInRegion(Location loc) {
      int minX = Math.min(this.pos1.getBlockX(), this.pos2.getBlockX());
      int minY = Math.min(this.pos1.getBlockY(), this.pos2.getBlockY());
      int minZ = Math.min(this.pos1.getBlockZ(), this.pos2.getBlockZ());
      int maxX = Math.max(this.pos1.getBlockX(), this.pos2.getBlockX());
      int maxY = Math.max(this.pos1.getBlockY(), this.pos2.getBlockY());
      int maxZ = Math.max(this.pos1.getBlockZ(), this.pos2.getBlockZ());
      return loc.getBlockX() >= minX && loc.getBlockX() <= maxX && loc.getBlockY() >= minY && loc.getBlockY() <= maxY && loc.getBlockZ() >= minZ && loc.getBlockZ() <= maxZ;
   }

   public boolean isInRegionOther(Location loc, Location newpos1, Location newpos2) {
      int minX = Math.min(newpos1.getBlockX(), newpos2.getBlockX());
      int minY = Math.min(newpos1.getBlockY(), newpos2.getBlockY());
      int minZ = Math.min(newpos1.getBlockZ(), newpos2.getBlockZ());
      int maxX = Math.max(newpos1.getBlockX(), newpos2.getBlockX());
      int maxY = Math.max(newpos1.getBlockY(), newpos2.getBlockY());
      int maxZ = Math.max(newpos1.getBlockZ(), newpos2.getBlockZ());
      return loc.getBlockX() >= minX && loc.getBlockX() <= maxX && loc.getBlockY() >= minY && loc.getBlockY() <= maxY && loc.getBlockZ() >= minZ && loc.getBlockZ() <= maxZ;
   }

   public List<Location> getOutLine() {
      List<Location> list = new ArrayList();

      int y;
      Location loc1;
      for(y = this.pos2.getBlockX(); y <= this.pos1.getBlockX(); ++y) {
         loc1 = new Location(this.pos2.getWorld(), (double)y, (double)this.pos2.getBlockY(), (double)this.pos2.getBlockZ());
         if (!list.contains(loc1)) {
            list.add(loc1);
         }
      }

      for(y = this.pos2.getBlockZ(); y <= this.pos1.getBlockZ(); ++y) {
         loc1 = new Location(this.pos2.getWorld(), (double)this.pos2.getBlockX(), (double)this.pos2.getBlockY(), (double)y);
         if (!list.contains(loc1)) {
            list.add(loc1);
         }
      }

      for(y = this.pos1.getBlockX(); y >= this.pos2.getBlockX(); --y) {
         loc1 = new Location(this.pos2.getWorld(), (double)y, (double)this.pos2.getBlockY(), (double)this.pos1.getBlockZ());
         if (!list.contains(loc1)) {
            list.add(loc1);
         }
      }

      for(y = this.pos1.getBlockZ(); y >= this.pos2.getBlockZ(); --y) {
         loc1 = new Location(this.pos2.getWorld(), (double)this.pos1.getBlockX(), (double)this.pos2.getBlockY(), (double)y);
         if (!list.contains(loc1)) {
            list.add(loc1);
         }
      }

      for(y = this.pos2.getBlockX(); y <= this.pos1.getBlockX(); ++y) {
         loc1 = new Location(this.pos2.getWorld(), (double)y, (double)this.pos1.getBlockY(), (double)this.pos2.getBlockZ());
         if (!list.contains(loc1)) {
            list.add(loc1);
         }
      }

      for(y = this.pos2.getBlockZ(); y <= this.pos1.getBlockZ(); ++y) {
         loc1 = new Location(this.pos2.getWorld(), (double)this.pos2.getBlockX(), (double)this.pos1.getBlockY(), (double)y);
         if (!list.contains(loc1)) {
            list.add(loc1);
         }
      }

      for(y = this.pos1.getBlockX(); y >= this.pos2.getBlockX(); --y) {
         loc1 = new Location(this.pos2.getWorld(), (double)y, (double)this.pos1.getBlockY(), (double)this.pos1.getBlockZ());
         if (!list.contains(loc1)) {
            list.add(loc1);
         }
      }

      for(y = this.pos1.getBlockZ(); y >= this.pos2.getBlockZ(); --y) {
         loc1 = new Location(this.pos2.getWorld(), (double)this.pos1.getBlockX(), (double)this.pos1.getBlockY(), (double)y);
         if (!list.contains(loc1)) {
            list.add(loc1);
         }
      }

      for(y = this.pos2.getBlockY(); y <= this.pos1.getBlockY(); ++y) {
         loc1 = new Location(this.pos2.getWorld(), (double)this.pos1.getBlockX(), (double)y, (double)this.pos1.getBlockZ());
         if (!list.contains(loc1)) {
            list.add(loc1);
         }

         Location loc2 = new Location(this.pos2.getWorld(), (double)this.pos2.getBlockX(), (double)y, (double)this.pos1.getBlockZ());
         if (!list.contains(loc2)) {
            list.add(loc2);
         }

         Location loc3 = new Location(this.pos2.getWorld(), (double)this.pos1.getBlockX(), (double)y, (double)this.pos2.getBlockZ());
         if (!list.contains(loc3)) {
            list.add(loc3);
         }

         Location loc4 = new Location(this.pos2.getWorld(), (double)this.pos2.getBlockX(), (double)y, (double)this.pos2.getBlockZ());
         if (!list.contains(loc4)) {
            list.add(loc4);
         }
      }

      return list;
   }

   public boolean isRegionInCuboid(Location newpos1, Location newpos2) {
      Location pos1up1 = this.pos1;
      Location pos1up2 = new Location(this.pos1.getWorld(), (double)this.pos2.getBlockX(), (double)this.pos1.getBlockY(), (double)this.pos1.getBlockZ());
      Location pos1up3 = new Location(this.pos1.getWorld(), (double)this.pos2.getBlockX(), (double)this.pos1.getBlockY(), (double)this.pos2.getBlockZ());
      Location pos1up4 = new Location(this.pos1.getWorld(), (double)this.pos1.getBlockX(), (double)this.pos1.getBlockY(), (double)this.pos2.getBlockZ());
      Location pos1down1 = this.pos2;
      Location pos1down2 = new Location(this.pos1.getWorld(), (double)this.pos2.getBlockX(), (double)this.pos2.getBlockY(), (double)this.pos1.getBlockZ());
      Location pos1down3 = new Location(this.pos1.getWorld(), (double)this.pos1.getBlockX(), (double)this.pos2.getBlockY(), (double)this.pos1.getBlockZ());
      Location pos1down4 = new Location(this.pos1.getWorld(), (double)this.pos1.getBlockX(), (double)this.pos2.getBlockY(), (double)this.pos2.getBlockZ());
      Location[] list = new Location[]{pos1up1, pos1up2, pos1up3, pos1up4, pos1down1, pos1down2, pos1down3, pos1down4};
      Location[] var12 = list;
      int var13 = list.length;

      Location loc;
      for(int var14 = 0; var14 < var13; ++var14) {
         loc = var12[var14];
         if (this.isInRegionOther(loc, newpos1, newpos2)) {
            return true;
         }
      }

      Location cpos1up2 = new Location(newpos1.getWorld(), (double)newpos2.getBlockX(), (double)newpos1.getBlockY(), (double)newpos1.getBlockZ());
      Location cpos1up3 = new Location(newpos1.getWorld(), (double)newpos2.getBlockX(), (double)newpos1.getBlockY(), (double)newpos2.getBlockZ());
      loc = new Location(newpos1.getWorld(), (double)newpos1.getBlockX(), (double)newpos1.getBlockY(), (double)newpos2.getBlockZ());
      Location cpos1down2 = new Location(newpos1.getWorld(), (double)newpos2.getBlockX(), (double)newpos2.getBlockY(), (double)newpos1.getBlockZ());
      Location cpos1down3 = new Location(newpos1.getWorld(), (double)newpos1.getBlockX(), (double)newpos2.getBlockY(), (double)newpos1.getBlockZ());
      Location cpos1down4 = new Location(newpos1.getWorld(), (double)newpos1.getBlockX(), (double)newpos2.getBlockY(), (double)newpos2.getBlockZ());
      Location[] clist = new Location[]{newpos1, cpos1up2, cpos1up3, loc, newpos2, cpos1down2, cpos1down3, cpos1down4};
      Location[] var21 = clist;
      int var22 = clist.length;

      for(int var23 = 0; var23 < var22; ++var23) {
         Location loc2 = var21[var23];
         if (this.isInRegion(loc2)) {
            return true;
         }
      }

      return false;
   }

   public boolean isRegionPreventOutCuboid(Location newpos1, Location newpos2) {
      Location pos1up1 = this.pos1;
      Location pos1up2 = new Location(this.pos1.getWorld(), (double)this.pos2.getBlockX(), (double)this.pos1.getBlockY(), (double)this.pos1.getBlockZ());
      Location pos1up3 = new Location(this.pos1.getWorld(), (double)this.pos2.getBlockX(), (double)this.pos1.getBlockY(), (double)this.pos2.getBlockZ());
      Location pos1up4 = new Location(this.pos1.getWorld(), (double)this.pos1.getBlockX(), (double)this.pos1.getBlockY(), (double)this.pos2.getBlockZ());
      Location pos1down1 = this.pos2;
      Location pos1down2 = new Location(this.pos1.getWorld(), (double)this.pos2.getBlockX(), (double)this.pos2.getBlockY(), (double)this.pos1.getBlockZ());
      Location pos1down3 = new Location(this.pos1.getWorld(), (double)this.pos1.getBlockX(), (double)this.pos2.getBlockY(), (double)this.pos1.getBlockZ());
      Location pos1down4 = new Location(this.pos1.getWorld(), (double)this.pos1.getBlockX(), (double)this.pos2.getBlockY(), (double)this.pos2.getBlockZ());
      Location[] list = new Location[]{pos1up1, pos1up2, pos1up3, pos1up4, pos1down1, pos1down2, pos1down3, pos1down4};
      boolean result = true;
      Location[] var13 = list;
      int var14 = list.length;

      for(int var15 = 0; var15 < var14; ++var15) {
         Location loc = var13[var15];
         if (this.isInRegionOther(loc, newpos1, newpos2)) {
            result = false;
         }
      }

      return result;
   }

   public boolean isRegionPreventInCuboid(Location newpos1, Location newpos2) {
      Location pos1up1 = this.pos1;
      Location pos1up2 = new Location(this.pos1.getWorld(), (double)this.pos2.getBlockX(), (double)this.pos1.getBlockY(), (double)this.pos1.getBlockZ());
      Location pos1up3 = new Location(this.pos1.getWorld(), (double)this.pos2.getBlockX(), (double)this.pos1.getBlockY(), (double)this.pos2.getBlockZ());
      Location pos1up4 = new Location(this.pos1.getWorld(), (double)this.pos1.getBlockX(), (double)this.pos1.getBlockY(), (double)this.pos2.getBlockZ());
      Location pos1down1 = this.pos2;
      Location pos1down2 = new Location(this.pos1.getWorld(), (double)this.pos2.getBlockX(), (double)this.pos2.getBlockY(), (double)this.pos1.getBlockZ());
      Location pos1down3 = new Location(this.pos1.getWorld(), (double)this.pos1.getBlockX(), (double)this.pos2.getBlockY(), (double)this.pos1.getBlockZ());
      Location pos1down4 = new Location(this.pos1.getWorld(), (double)this.pos1.getBlockX(), (double)this.pos2.getBlockY(), (double)this.pos2.getBlockZ());
      Location[] list = new Location[]{pos1up1, pos1up2, pos1up3, pos1up4, pos1down1, pos1down2, pos1down3, pos1down4};
      boolean result = true;
      Location[] var13 = list;
      int var14 = list.length;

      for(int var15 = 0; var15 < var14; ++var15) {
         Location loc = var13[var15];
         if (!this.isInRegionOther(loc, newpos1, newpos2)) {
            result = false;
         }
      }

      return result;
   }
}
