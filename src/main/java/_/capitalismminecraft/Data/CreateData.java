package _.capitalismminecraft.Data;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import _.capitalismminecraft.CapitalismMinecraft;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CreateData {
   public void CreateNewDataFile() {
      File filepath = new File(CapitalismMinecraft.getPlugins().getDataFolder(), "/");
      if (!filepath.exists()) {
         filepath.mkdir();
      }

      File file = new File(CapitalismMinecraft.getPlugins().getDataFolder(), "MainConfig.yml");
      if (!file.exists()) {
         try {
            file.createNewFile();
         } catch (IOException var6) {
            Bukkit.getServer().getConsoleSender().sendMessage(CapitalismMinecraft.ColorChat("&cCapitalismMinecraft:: 파일 저장 중 오류가 발생하였습니다. (CreateNewDataFile() - createNewFile)"));
            throw new RuntimeException(var6);
         }

         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         config.createSection("Region-height");
         config.set("Region-height", 7);
         config.createSection("Region-size");
         config.set("Region-size", 10);
         config.createSection("Protect-explosion");
         config.set("Protect-explosion", true);
         config.createSection("Protect-fire-spread");
         config.set("Protect-fire-spread", true);
         config.createSection("Protect-piston");
         config.set("Protect-piston", true);
         config.createSection("Amount-per-person-can-have");
         config.set("Amount-per-person-can-have", 4);
         config.createSection("Prevent-Region-Create");
         config.set("Prevent-Region-Create", false);
         config.createSection("Prevent-Region-In-Out");
         config.set("Prevent-Region-In-Out", true);

         try {
            config.save(file);
         } catch (IOException var5) {
            Bukkit.getServer().getConsoleSender().sendMessage(CapitalismMinecraft.ColorChat("&cCapitalismMinecraft:: 파일 저장 중 오류가 발생하였습니다. (CreateNewDataFile() - save)"));
            throw new RuntimeException(var5);
         }
      }
   }

   public void CreatePlayerDataFile() {
      File filepath = new File(CapitalismMinecraft.getPlugins().getDataFolder(), "/player-data");
      if (!filepath.exists()) {
         filepath.mkdir();
      }

      Iterator var2 = Bukkit.getOnlinePlayers().iterator();

      while(var2.hasNext()) {
         Player p = (Player)var2.next();
         File file = new File(CapitalismMinecraft.getPlugins().getDataFolder() + "/player-data/", p.getUniqueId() + ".yml");
         if (!file.exists()) {
            try {
               file.createNewFile();
            } catch (IOException var8) {
               Bukkit.getServer().getConsoleSender().sendMessage(CapitalismMinecraft.ColorChat("&cCapitalismMinecraft:: 파일 생성 중 오류가 발생하였습니다. (CreatePlayerDataFile() - createNewFile)"));
               throw new RuntimeException(var8);
            }

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.createSection("Region-Amount");
            config.set("Region-Amount", 0);
            config.createSection("Region-List");

            try {
               config.save(file);
            } catch (IOException var7) {
               Bukkit.getServer().getConsoleSender().sendMessage(CapitalismMinecraft.ColorChat("&cCapitalismMinecraft:: 파일 저장 중 오류가 발생하였습니다. (CreatePlayerDataFile() - save)"));
               throw new RuntimeException(var7);
            }
         }
      }

   }

   public File getFile() {
      File file = new File(CapitalismMinecraft.getPlugins().getDataFolder(), "MainConfig.yml");
      return file.exists() && file != null ? file : null;
   }

   public Integer getSize() {
      File file = this.getFile();
      if (file != null && file.exists()) {
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         int size = config.getInt("Region-size");
         return size;
      } else {
         return null;
      }
   }

   public void setSize(int size) {
      File file = this.getFile();
      if (file != null && file.exists()) {
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         config.set("Region-size", size);

         try {
            config.save(file);
         } catch (IOException var5) {
            Bukkit.getServer().getConsoleSender().sendMessage(CapitalismMinecraft.ColorChat("&cCapitalismMinecraft:: 파일 저장 중 오류가 발생하였습니다. (setSize - save)"));
            throw new RuntimeException(var5);
         }
      }
   }

   public Integer getHeight() {
      File file = this.getFile();
      if (file != null && file.exists()) {
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         int height = config.getInt("Region-height");
         return height;
      } else {
         return null;
      }
   }

   public void setHeight(int height) {
      File file = this.getFile();
      if (file != null && file.exists()) {
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         config.set("Region-height", height);

         try {
            config.save(file);
         } catch (IOException var5) {
            Bukkit.getServer().getConsoleSender().sendMessage(CapitalismMinecraft.ColorChat("&cCapitalismMinecraft:: 파일 저장 중 오류가 발생하였습니다. (setHeight - save)"));
            throw new RuntimeException(var5);
         }
      }
   }

   public Integer getMaxAmount() {
      File file = this.getFile();
      if (file != null && file.exists()) {
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         int height = config.getInt("Amount-per-person-can-have");
         return height;
      } else {
         return null;
      }
   }

   public void setMaxAmount(int amount) {
      File file = this.getFile();
      if (file != null && file.exists()) {
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         config.set("Amount-per-person-can-have", amount);

         try {
            config.save(file);
         } catch (IOException var5) {
            Bukkit.getServer().getConsoleSender().sendMessage(CapitalismMinecraft.ColorChat("&cCapitalismMinecraft:: 파일 저장 중 오류가 발생하였습니다. (setAmount - save)"));
            throw new RuntimeException(var5);
         }
      }
   }

   public boolean isProtectExplosion() {
      File file = this.getFile();
      if (file != null && file.exists()) {
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         boolean bool = config.getBoolean("Protect-explosion");
         return bool;
      } else {
         return false;
      }
   }

   public boolean isProtectFireSpread() {
      File file = this.getFile();
      if (file != null && file.exists()) {
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         boolean bool = config.getBoolean("Protect-fire-spread");
         return bool;
      } else {
         return false;
      }
   }

   public boolean isProtectPiston() {
      File file = this.getFile();
      if (file != null && file.exists()) {
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         boolean bool = config.getBoolean("Protect-piston");
         return bool;
      } else {
         return false;
      }
   }

   public boolean isPreventRegionCreate() {
      File file = this.getFile();
      if (file != null && file.exists()) {
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         boolean bool = config.getBoolean("Prevent-Region-Create");
         return bool;
      } else {
         return false;
      }
   }

   public boolean getPreventRegionInOut() {
      File file = this.getFile();
      if (file != null && file.exists()) {
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         boolean bool = config.getBoolean("Prevent-Region-In-Out");
         return bool;
      } else {
         return false;
      }
   }
}
