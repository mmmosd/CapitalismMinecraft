package _.capitalismminecraft.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import _.capitalismminecraft.CapitalismMinecraft;
import _.capitalismminecraft.Data.CreateData;
import _.capitalismminecraft.Data.PlayerData;
import _.capitalismminecraft.Data.RegionData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CreateRegion {
   private final String name;

   public CreateRegion(String name) {
      this.name = name;
   }

   public void createRegionFile(Player p, Location center, int large) {
      RegionData regiondata = new RegionData(this.name);
      if (!regiondata.isExistRegion()) {
         File filepath = new File(CapitalismMinecraft.getPlugins().getDataFolder() + "/Region", "");
         if (!filepath.exists()) {
            filepath.mkdir();
         }

         File file = new File(CapitalismMinecraft.getPlugins().getDataFolder() + "/Region/", this.name + ".yml");

         try {
            file.createNewFile();
         } catch (IOException var17) {
            Bukkit.getServer().getConsoleSender().sendMessage(CapitalismMinecraft.ColorChat("&cCapitalismMinecraft:: 파일 생성 중 오류가 발생하였습니다. (createRegionFile - createNewFile)"));
            throw new RuntimeException(var17);
         }

         CreateData createData = new CreateData();
         int size = createData.getSize() * large;
         int height = createData.getHeight() * large;
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         config.createSection("Builder");
         config.set("Builder", String.valueOf(p.getUniqueId()));
         config.createSection("Region-Center");
         config.set("Region-Center", center);
         config.createSection("Region-Height");
         config.set("Region-Height", height);
         config.createSection("Region-Size");
         config.set("Region-Size", size);
         config.createSection("Region-First-Position");
         Location pos1 = new Location(center.getWorld(), center.getX() + (double)size, center.getY() + (double)height, center.getZ() + (double)size);
         config.set("Region-First-Position", pos1);
         config.createSection("Region-Second-Position");
         Location pos2 = new Location(center.getWorld(), center.getX() - (double)size, center.getY(), center.getZ() - (double)size);
         config.set("Region-Second-Position", pos2);
         config.createSection("Region-Owner");
         List<String> list = new ArrayList();
         config.set("Region-Owner", list);
         PlayerData pd = new PlayerData(p.getUniqueId());
         pd.addRegion(this.name);
         pd.setAmount(pd.getRegionAmount() + 1);

         try {
            config.save(file);
         } catch (IOException var16) {
            Bukkit.getServer().getConsoleSender().sendMessage(CapitalismMinecraft.ColorChat("&cCapitalismMinecraft:: 파일 생성 중 오류가 발생하였습니다. (createRegionFile - save)"));
            throw new RuntimeException(var16);
         }
      }
   }

   public void removeRegionFile(UUID uuid) {
      RegionData regiondata = new RegionData(this.name);
      if (regiondata.isExistRegion()) {
         File file = regiondata.getRegionFile();
         file.delete();
         PlayerData pd = new PlayerData(uuid);
         pd.removeRegion(this.name);
         if (pd.getRegionAmount() - 1 >= 0) {
            pd.setAmount(pd.getRegionAmount() - 1);
         }

      }
   }
}
