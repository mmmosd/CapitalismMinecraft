package _.capitalismminecraft.Utils;

import _.capitalismminecraft.CapitalismMinecraft;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileList {
   public static List<File> getRegionFileList() {
      File filepath = new File(CapitalismMinecraft.getPlugins().getDataFolder(), "/Region/");
      File[] filelist = filepath.listFiles();
      List<File> list = new ArrayList();
      if (filelist == null) {
         return null;
      } else {
         File[] var3 = filelist;
         int var4 = filelist.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            File file = var3[var5];
            list.add(file);
         }

         return list != null && !list.isEmpty() ? list : null;
      }
   }

   public static List<File> getPreventRegionFileList() {
      File filepath = new File(CapitalismMinecraft.getPlugins().getDataFolder(), "/Prevent/");
      File[] filelist = filepath.listFiles();
      List<File> list = new ArrayList();
      if (filelist == null) {
         return null;
      } else {
         File[] var3 = filelist;
         int var4 = filelist.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            File file = var3[var5];
            list.add(file);
         }

         return list != null && !list.isEmpty() ? list : null;
      }
   }
}
