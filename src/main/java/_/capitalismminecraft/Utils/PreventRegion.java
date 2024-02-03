package _.capitalismminecraft.Utils;

import java.io.File;
import java.io.IOException;

import _.capitalismminecraft.CapitalismMinecraft;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PreventRegion {
   private final String region;

   public PreventRegion(String region) {
      this.region = region;
   }

   public boolean isExistFile() {
      File filepath = new File(CapitalismMinecraft.getPlugins().getDataFolder() + "/Prevent", "");
      if (!filepath.exists()) {
         filepath.mkdir();
      }

      File file = new File(CapitalismMinecraft.getPlugins().getDataFolder() + "/Prevent/", this.region + ".yml");
      return file.exists();
   }

   public File getFile() {
      File filepath = new File(CapitalismMinecraft.getPlugins().getDataFolder() + "/Prevent", "");
      if (!filepath.exists()) {
         filepath.mkdir();
      }

      File file = new File(CapitalismMinecraft.getPlugins().getDataFolder() + "/Prevent/", this.region + ".yml");
      return file == null ? null : file;
   }

   public void createFile(Player p, Location pos1, Location pos2) {
      File filepath = new File(CapitalismMinecraft.getPlugins().getDataFolder() + "/Prevent", "");
      if (!filepath.exists()) {
         filepath.mkdir();
      }

      File file = new File(CapitalismMinecraft.getPlugins().getDataFolder() + "/Prevent/", this.region + ".yml");

      try {
         file.createNewFile();
      } catch (IOException var9) {
         Bukkit.getServer().getConsoleSender().sendMessage(CapitalismMinecraft.ColorChat("&cCapitalismMinecraft:: 파일 생성 중 오류가 발생하였습니다. (createRegionFile - createNewFile)"));
         throw new RuntimeException(var9);
      }

      YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
      config.createSection("Pos1");
      config.createSection("Pos2");
      config.set("Pos1", pos1);
      config.set("Pos2", pos2);

      try {
         config.save(file);
      } catch (IOException var8) {
         Bukkit.getServer().getConsoleSender().sendMessage(CapitalismMinecraft.ColorChat("&cCapitalismMinecraft:: 파일 생성 중 오류가 발생하였습니다. (createRegionFile - save)"));
         throw new RuntimeException(var8);
      }

      p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&e" + this.region + "&f 건차 방지 지역이 생성되었습니다."));
      p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
   }

   public void removeFile() {
      if (this.isExistFile()) {
         File file = this.getFile();
         file.delete();
      }
   }

   public Location getFirstPosition() {
      if (!this.isExistFile()) {
         return null;
      } else {
         File file = this.getFile();
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         return config.getLocation("Pos1") == null ? null : config.getLocation("Pos1");
      }
   }

   public Location getSecondPosition() {
      if (!this.isExistFile()) {
         return null;
      } else {
         File file = this.getFile();
         YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
         return config.getLocation("Pos2") == null ? null : config.getLocation("Pos2");
      }
   }
}
