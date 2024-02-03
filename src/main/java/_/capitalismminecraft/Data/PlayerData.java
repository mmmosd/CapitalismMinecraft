package _.capitalismminecraft.Data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import _.capitalismminecraft.CapitalismMinecraft;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerData {
   private final UUID uuid;

   public PlayerData(UUID uuid) {
      this.uuid = uuid;
   }

   public boolean isExistPlayerData() {
      File file = new File(CapitalismMinecraft.getPlugins().getDataFolder() + "/player-data/", this.uuid + ".yml");
      return file.exists();
   }

   public File getPlayerDataFile() {
      if (!this.isExistPlayerData()) {
         return null;
      } else {
         File file = new File(CapitalismMinecraft.getPlugins().getDataFolder() + "/player-data/", this.uuid + ".yml");
         return file;
      }
   }

   public Integer getRegionAmount() {
      File file = this.getPlayerDataFile();
      if (file == null) {
         return null;
      } else {
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         int amount = config.getInt("Region-Amount");
         return amount < 0 ? null : amount;
      }
   }

   public List<String> getRegionList() {
      File file = this.getPlayerDataFile();
      if (file != null && file.exists()) {
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         List<String> list = config.getStringList("Region-List");
         return list != null && !list.isEmpty() ? list : null;
      } else {
         return null;
      }
   }

   public void addRegion(String region) {
      File file = this.getPlayerDataFile();
      if (file != null && file.exists()) {
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         Object list;
         if (this.getRegionList() == null) {
            list = new ArrayList();
            ((List)list).add(region);
         } else {
            list = this.getRegionList();
            if (((List)list).contains(region)) {
               return;
            }

            ((List)list).add(region);
         }

         config.set("Region-List", list);

         try {
            config.save(file);
         } catch (IOException var6) {
            Bukkit.getServer().getConsoleSender().sendMessage(CapitalismMinecraft.ColorChat("&cCapitalismMinecraft:: 파일 생성 중 오류가 발생하였습니다. (addRegion - save)"));
            throw new RuntimeException(var6);
         }
      }
   }

   public void removeRegion(String region) {
      File file = this.getPlayerDataFile();
      if (file != null && file.exists()) {
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         if (this.getRegionList() != null) {
            List<String> list = this.getRegionList();
            if (list.contains(region)) {
               list.remove(region);
               config.set("Region-List", list);

               try {
                  config.save(file);
               } catch (IOException var6) {
                  Bukkit.getServer().getConsoleSender().sendMessage(CapitalismMinecraft.ColorChat("&cCapitalismMinecraft:: 파일 생성 중 오류가 발생하였습니다. (addRegion - save)"));
                  throw new RuntimeException(var6);
               }
            }
         }
      }
   }

   public void setAmount(int amount) {
      File file = this.getPlayerDataFile();
      if (file != null && file.exists()) {
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         config.set("Region-Amount", amount);

         try {
            config.save(file);
         } catch (IOException var5) {
            Bukkit.getServer().getConsoleSender().sendMessage(CapitalismMinecraft.ColorChat("&cCapitalismMinecraft:: 파일 생성 중 오류가 발생하였습니다. (addRegion - save)"));
            throw new RuntimeException(var5);
         }
      }
   }
}
