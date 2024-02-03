package _.capitalismminecraft.Utils;

import java.util.Iterator;
import java.util.List;

import _.capitalismminecraft.Data.RegionData;
import org.bukkit.Location;
import org.bukkit.Material;

public class BuildRegion {
   private final String region;

   public BuildRegion(String region) {
      this.region = region;
   }

   public void BuildArea() {
      RegionData regionData = new RegionData(this.region);
      Location pos1 = regionData.getFirstPosition();
      Location pos2 = regionData.getSecondPosition();
      Cuboid cuboid = new Cuboid(pos1, pos2);
      List<Location> list = cuboid.getOutLine();
      Iterator var6 = list.iterator();

      while(var6.hasNext()) {
         Location loc = (Location)var6.next();
         loc.getBlock().setType(Material.RED_STAINED_GLASS);
      }

   }
}
