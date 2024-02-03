package _.capitalismminecraft.Commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import _.capitalismminecraft.CapitalismMinecraft;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import static _.capitalismminecraft.Utils.FileList.getPreventRegionFileList;

public class bpTab implements TabCompleter {
   private final CapitalismMinecraft plugin;

   public bpTab(CapitalismMinecraft plugin) {
      this.plugin = plugin;
   }

   public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
      List<String> completion = new ArrayList();
      String[] cmd = new String[]{"get", "size", "height", "amount", "preventer", "prevent", "preventloc"};
      String[] arglist = new String[]{"get", "prevent", "preventloc"};
      List<File> list = getPreventRegionFileList();
      List<String> nlist = new ArrayList();
      if (list != null) {
         Iterator var10 = list.iterator();

         while(var10.hasNext()) {
            File file = (File)var10.next();
            if (file.getName().endsWith(".yml")) {
               nlist.add(file.getName().replace(".yml", ""));
            }
         }
      }

      if (!sender.isOp()) {
         return Collections.EMPTY_LIST;
      } else {
         if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], Arrays.asList(cmd), completion);
         } else {
            if (args.length != 2) {
               return Collections.EMPTY_LIST;
            }

            if (!Arrays.asList(arglist).contains(args[0])) {
               return Collections.EMPTY_LIST;
            }

            if (args[0].equals("get")) {
               String[] num = new String[]{"1", "2", "3"};
               StringUtil.copyPartialMatches(args[1], Arrays.asList(num), completion);
            }

            if (args[0].equals("prevent") || args[0].equals("preventloc")) {
               if (list == null) {
                  return Collections.EMPTY_LIST;
               }

               StringUtil.copyPartialMatches(args[1], nlist, completion);
            }
         }

         Collections.sort(completion);
         return completion;
      }
   }
}
