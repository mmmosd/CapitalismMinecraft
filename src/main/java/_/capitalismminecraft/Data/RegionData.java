package _.capitalismminecraft.Data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import _.capitalismminecraft.CapitalismMinecraft;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class RegionData {
   private final String region;

   public RegionData(String region) {
      this.region = region;
   }

   public boolean isExistRegion() {
      File file = new File(CapitalismMinecraft.getPlugins().getDataFolder() + "/Region/", this.region + ".yml");
      return file.exists() && file != null;
   }

   public File getRegionFile() {
      if (!this.isExistRegion()) {
         return null;
      } else {
         File file = new File(CapitalismMinecraft.getPlugins().getDataFolder() + "/Region/", this.region + ".yml");
         return file;
      }
   }

   public Integer getSize() {
      if (!this.isExistRegion()) {
         return null;
      } else {
         File file = this.getRegionFile();
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         int size = config.getInt("Region-Size");
         return size;
      }
   }

   public Integer getHeight() {
      if (!this.isExistRegion()) {
         return null;
      } else {
         File file = this.getRegionFile();
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         int height = config.getInt("Region-Height");
         return height;
      }
   }

   public Location getCenter() {
      if (!this.isExistRegion()) {
         return null;
      } else {
         File file = this.getRegionFile();
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         Location center = config.getLocation("Region-Center");
         return center;
      }
   }

   public Location getFirstPosition() {
      if (!this.isExistRegion()) {
         return null;
      } else {
         File file = this.getRegionFile();
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         Location pos = config.getLocation("Region-First-Position");
         return pos;
      }
   }

   public Location getSecondPosition() {
      if (!this.isExistRegion()) {
         return null;
      } else {
         File file = this.getRegionFile();
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         Location pos = config.getLocation("Region-Second-Position");
         return pos;
      }
   }

   public List<String> getOwners() {
      if (!this.isExistRegion()) {
         return null;
      } else {
         File file = this.getRegionFile();
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         List<String> list = (List<String>) config.getList("Region-Owner");
         return list != null && !list.isEmpty() ? list : null;
      }
   }

   public UUID getBuilder() {
      if (!this.isExistRegion()) {
         return null;
      } else {
         File file = this.getRegionFile();
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         String uuid = (String)config.get("Builder");
         return uuid == null ? null : UUID.fromString(uuid);
      }
   }

   public void addOwner(UUID uuid) {
      if (this.isExistRegion()) {
         File file = this.getRegionFile();
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         if (this.getOwners() == null) {
            List<String> list = new ArrayList();
            list.add(String.valueOf(uuid));
            config.set("Region-Owner", list);
         } else {
            List<String> list = this.getOwners();
            list.add(String.valueOf(uuid));
            config.set("Region-Owner", list);
         }

         try {
            config.save(file);
         } catch (IOException var6) {
            Bukkit.getServer().getConsoleSender().sendMessage(CapitalismMinecraft.ColorChat("&cCapitalismMinecraft:: 파일 생성 중 오류가 발생하였습니다. (setCenter - save)"));
            throw new RuntimeException(var6);
         }
      }
   }

   public void removeOwner(UUID uuid) {
      if (this.isExistRegion()) {
         File file = this.getRegionFile();
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         if (this.getOwners() != null) {
            List<String> list = this.getOwners();
            if (list.contains(String.valueOf(uuid))) {
               list.remove(String.valueOf(uuid));
               config.set("Region-Owner", list);

               try {
                  config.save(file);
               } catch (IOException var6) {
                  Bukkit.getServer().getConsoleSender().sendMessage(CapitalismMinecraft.ColorChat("&cCapitalismMinecraft:: 파일 생성 중 오류가 발생하였습니다. (setCenter - save)"));
                  throw new RuntimeException(var6);
               }
            }
         }
      }
   }
}
