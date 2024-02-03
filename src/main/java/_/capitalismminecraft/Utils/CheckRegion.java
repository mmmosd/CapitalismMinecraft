package _.capitalismminecraft.Utils;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import _.capitalismminecraft.Data.RegionData;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CheckRegion {
   private final Location checkLocation;

   public CheckRegion(Location checkLocation) {
      this.checkLocation = checkLocation;
   }

   public boolean isBlockIsOtherRegionIn() {
      List<File> filelist = FileList.getRegionFileList();
      if (filelist == null) {
         return false;
      } else {
         Iterator var2 = filelist.iterator();

         while(var2.hasNext()) {
            File file = (File)var2.next();
            if (file.getName().endsWith(".yml")) {
               String region = file.getName().replace(".yml", "");
               RegionData data = new RegionData(region);
               Location pos1 = data.getFirstPosition();
               Location pos2 = data.getSecondPosition();
               Cuboid cuboid = new Cuboid(pos1, pos2);
               if (cuboid.isInRegion(this.checkLocation)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean isBlockIsOtherRegionInIfOwner(Player p) {
      List<File> filelist = FileList.getRegionFileList();
      if (filelist == null) {
         return false;
      } else {
         boolean result = false;
         Iterator var4 = filelist.iterator();

         while(true) {
            RegionData data;
            Cuboid cuboid;
            do {
               do {
                  File file;
                  do {
                     if (!var4.hasNext()) {
                        return result;
                     }

                     file = (File)var4.next();
                  } while(!file.getName().endsWith(".yml"));

                  String region = file.getName().replace(".yml", "");
                  data = new RegionData(region);
                  Location pos1 = data.getFirstPosition();
                  Location pos2 = data.getSecondPosition();
                  cuboid = new Cuboid(pos1, pos2);
               } while(!cuboid.isInRegion(this.checkLocation));
            } while(data.getOwners() != null && data.getOwners().contains(String.valueOf(p.getUniqueId())));

            if (!data.getBuilder().equals(p.getUniqueId())) {
               result = true;
            }
         }
      }
   }

   public boolean isBlockIsInOutLine() {
      List<File> filelist = FileList.getRegionFileList();
      if (filelist == null) {
         return false;
      } else {
         Iterator var2 = filelist.iterator();

         while(var2.hasNext()) {
            File file = (File)var2.next();
            if (file.getName().endsWith(".yml")) {
               String region = file.getName().replace(".yml", "");
               RegionData data = new RegionData(region);
               Location pos1 = data.getFirstPosition();
               Location pos2 = data.getSecondPosition();
               Cuboid cuboid = new Cuboid(pos1, pos2);
               if (cuboid.getOutLine().contains(this.checkLocation)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean isCuboidIsOtherRegionIn(Location newpos1, Location newpos2) {
      List<File> filelist = FileList.getRegionFileList();
      if (filelist == null) {
         return false;
      } else {
         Iterator var4 = filelist.iterator();

         while(var4.hasNext()) {
            File file = (File)var4.next();
            if (file.getName().endsWith(".yml")) {
               String region = file.getName().replace(".yml", "");
               RegionData data = new RegionData(region);
               Location pos1 = data.getFirstPosition();
               Location pos2 = data.getSecondPosition();
               Cuboid cuboid = new Cuboid(pos1, pos2);
               if (cuboid.isRegionInCuboid(newpos1, newpos2)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean isRegionOutPreventCuboid(Location newpos1, Location newpos2) {
      List<File> filelist = FileList.getPreventRegionFileList();
      if (filelist == null) {
         return false;
      } else {
         boolean result = true;
         Iterator var5 = filelist.iterator();

         while(var5.hasNext()) {
            File file = (File)var5.next();
            if (file.getName().endsWith(".yml")) {
               String region = file.getName().replace(".yml", "");
               PreventRegion pr = new PreventRegion(region);
               Location pos1 = pr.getFirstPosition();
               Location pos2 = pr.getSecondPosition();
               Cuboid cuboid = new Cuboid(newpos1, newpos2);
               if (!cuboid.isRegionPreventOutCuboid(pos1, pos2)) {
                  result = false;
               }
            }
         }

         return result;
      }
   }

   public boolean isRegionInPreventCuboid(Location newpos1, Location newpos2) {
      List<File> filelist = FileList.getPreventRegionFileList();
      if (filelist == null) {
         return false;
      } else {
         boolean result = false;
         Iterator var5 = filelist.iterator();

         while(var5.hasNext()) {
            File file = (File)var5.next();
            if (file.getName().endsWith(".yml")) {
               String region = file.getName().replace(".yml", "");
               PreventRegion pr = new PreventRegion(region);
               Location pos1 = pr.getFirstPosition();
               Location pos2 = pr.getSecondPosition();
               Cuboid cuboid = new Cuboid(newpos1, newpos2);
               if (cuboid.isRegionPreventInCuboid(pos1, pos2)) {
                  result = true;
               }
            }
         }

         return result;
      }
   }
}
