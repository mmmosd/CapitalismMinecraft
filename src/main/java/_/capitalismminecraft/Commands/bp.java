package _.capitalismminecraft.Commands;

import _.capitalismminecraft.CapitalismMinecraft;
import _.capitalismminecraft.Data.CreateData;
import _.capitalismminecraft.Items.CustomStack;
import _.capitalismminecraft.Utils.PreventRegion;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class bp implements CommandExecutor {
   private final CapitalismMinecraft plugin;

   public bp(CapitalismMinecraft plugin) {
      this.plugin = plugin;
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (sender instanceof Player) {
         Player p = (Player)sender;
         int l = args.length;
         if (!p.isOp()) {
            p.sendMessage(CapitalismMinecraft.ColorChat("&c당신은 해당 명령어를 사용할 권한이 없습니다."));
            return false;
         }

         if (l == 0) {
            p.sendMessage(CapitalismMinecraft.ColorChat("&8---------------&b&lBuild&7&lProtecter&8---------------"));
            p.sendMessage(CapitalismMinecraft.ColorChat("&f/bp &7: 명령어를 안내합니다."));
            p.sendMessage(CapitalismMinecraft.ColorChat("&f/bp get <1/2/3> &7: 해당 사이즈의 건차 아이템을 지급합니다."));
            p.sendMessage(CapitalismMinecraft.ColorChat("&f/bp size <사이즈> &7: 건차 범위를 설정합니다."));
            p.sendMessage(CapitalismMinecraft.ColorChat("&f/bp height <높이> &7: 건차 높이를 설정합니다."));
            p.sendMessage(CapitalismMinecraft.ColorChat("&f/bp amount <개수> &7: 개인 당 가질 수 있는 건차 개수를 설정합니다."));
            p.sendMessage(CapitalismMinecraft.ColorChat("&f/bp preventer &7: 건차 방지 지역을 지정하는 아이템을 지급합니다."));
            p.sendMessage(CapitalismMinecraft.ColorChat("&f/bp prevent <이름> &7: 건차 방지 지역을 생성합니다."));
            p.sendMessage(CapitalismMinecraft.ColorChat("&7 └ 이미 생성된 지역을 한 번더 입력시 삭제됩니다."));
            p.sendMessage(CapitalismMinecraft.ColorChat("&f/bp preventloc <이름> &7: 해당 건차 방지 지역의 위치를 안내합니다."));
            p.sendMessage(CapitalismMinecraft.ColorChat("&8---------------------------------------------"));
         } else if (l == 1) {
            if (args[0].equals("get")) {
               p.sendMessage(CapitalismMinecraft.ColorChat("&c사용법 : /bp get <1/2/3>"));
            } else if (args[0].equals("size")) {
               p.sendMessage(CapitalismMinecraft.ColorChat("&c사용법 : /bp size <사이즈>"));
            } else if (args[0].equals("height")) {
               p.sendMessage(CapitalismMinecraft.ColorChat("&c사용법 : /bp height <높이>"));
            } else if (args[0].equals("amount")) {
               p.sendMessage(CapitalismMinecraft.ColorChat("&c사용법 : /bp amount <개수>"));
            } else if (args[0].equals("preventer")) {
               if (CapitalismMinecraft.isFullInventory(p)) {
                  p.getWorld().dropItemNaturally(p.getLocation(), CustomStack.Preventer());
               } else {
                  p.getInventory().addItem(new ItemStack[]{CustomStack.Preventer()});
               }

               p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&ePreventer &f아이템을 지급하였습니다."));
               p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
            } else if (args[0].equals("prevent")) {
               p.sendMessage(CapitalismMinecraft.ColorChat("&c사용법 : /bp prevent <이름>"));
            } else if (args[0].equals("preventloc")) {
               p.sendMessage(CapitalismMinecraft.ColorChat("&c사용법 : /bp preventloc <이름>"));
            } else {
               p.sendMessage(CapitalismMinecraft.ColorChat("&c잘못된 명령어입니다."));
            }
         } else if (l == 2) {
            int size;
            if (args[0].equals("get")) {
               if (CapitalismMinecraft.isNumberExeption(args[1])) {
                  p.sendMessage(CapitalismMinecraft.ColorChat("&c[두 번째 문자열]은 숫자가 아닙니다. 다시 시도해주십시오."));
                  return false;
               }

               size = Integer.parseInt(args[1]);
               if (size < 1 || size > 3) {
                  p.sendMessage(CapitalismMinecraft.ColorChat("&c사이즈는 1 이상, 3 이하여야 합니다."));
                  return false;
               }

               if (CapitalismMinecraft.isFullInventory(p)) {
                  p.getWorld().dropItemNaturally(p.getLocation(), CustomStack.RegionProtecter(size));
               } else {
                  p.getInventory().addItem(new ItemStack[]{CustomStack.RegionProtecter(size)});
               }

               p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&e" + size + " &f크기의 건차 아이템을 지급하였습니다."));
               p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
            } else {
               CreateData cd;
               if (args[0].equals("size")) {
                  if (CapitalismMinecraft.isNumberExeption(args[1])) {
                     p.sendMessage(CapitalismMinecraft.ColorChat("&c[두 번째 문자열]은 숫자가 아닙니다. 다시 시도해주십시오."));
                     return false;
                  }

                  size = Integer.parseInt(args[1]);
                  cd = new CreateData();
                  cd.setSize(size);
                  p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&f건차 사이즈를 &e" + size + " &f로 지정하였습니다."));
                  p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
               } else if (args[0].equals("height")) {
                  if (CapitalismMinecraft.isNumberExeption(args[1])) {
                     p.sendMessage(CapitalismMinecraft.ColorChat("&c[두 번째 문자열]은 숫자가 아닙니다. 다시 시도해주십시오."));
                     return false;
                  }

                  size = Integer.parseInt(args[1]);
                  cd = new CreateData();
                  cd.setHeight(size);
                  p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&f건차 높이를 &e" + size + " &f로 지정하였습니다."));
                  p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
               } else if (args[0].equals("amount")) {
                  if (CapitalismMinecraft.isNumberExeption(args[1])) {
                     p.sendMessage(CapitalismMinecraft.ColorChat("&c[두 번째 문자열]은 숫자가 아닙니다. 다시 시도해주십시오."));
                     return false;
                  }

                  size = Integer.parseInt(args[1]);
                  cd = new CreateData();
                  cd.setMaxAmount(size);
                  p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&f개인 건차 개수를 &e" + size + " &f로 지정하였습니다."));
                  p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
               } else if (args[0].equals("preventer")) {
                  p.sendMessage(CapitalismMinecraft.ColorChat("&c그 이상으로 입력할 수 없습니다."));
               } else {
                  Location pos2;
                  PreventRegion pr;
                  Location pos1;
                  if (args[0].equals("prevent")) {
                     pr = new PreventRegion(args[1]);
                     if (pr.isExistFile()) {
                        pr.removeFile();
                        p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&e" + args[1] + "&f 건차 방지 지역을 제거하였습니다."));
                        return false;
                     }

                     if (!CapitalismMinecraft.Pos1.containsKey(p)) {
                        p.sendMessage(CapitalismMinecraft.ColorChat("&c포지션 1을 먼저 지정해주십시오."));
                        return false;
                     }

                     if (!CapitalismMinecraft.Pos2.containsKey(p)) {
                        p.sendMessage(CapitalismMinecraft.ColorChat("&c포지션 2를 먼저 지정해주십시오."));
                        return false;
                     }

                     pos1 = (Location)CapitalismMinecraft.Pos1.get(p);
                     pos2 = (Location)CapitalismMinecraft.Pos2.get(p);
                     pr.createFile(p, pos1, pos2);
                     CapitalismMinecraft.Pos1.remove(p);
                     CapitalismMinecraft.Pos2.remove(p);
                  } else if (args[0].equals("preventloc")) {
                     pr = new PreventRegion(args[1]);
                     if (!pr.isExistFile()) {
                        p.sendMessage(CapitalismMinecraft.ColorChat("&c해당 이름의 건차 방지 지역은 존재하지 않습니다."));
                        return false;
                     }

                     pos1 = pr.getFirstPosition();
                     pos2 = pr.getSecondPosition();
                     p.sendMessage(CapitalismMinecraft.ColorChat(CapitalismMinecraft.bb + "&f포지션 1 :&e " + pos1.getBlockX() + ", " + pos1.getBlockY() + ", " + pos1.getBlockZ() + "\n" + CapitalismMinecraft.bb + "&f포지션 2 : &e" + pos2.getBlockX() + ", " + pos2.getBlockY() + ", " + pos2.getBlockZ()));
                  } else {
                     p.sendMessage(CapitalismMinecraft.ColorChat("&c잘못된 명령어입니다."));
                  }
               }
            }
         } else {
            p.sendMessage(CapitalismMinecraft.ColorChat("&c그 이상으로 입력할 수 없습니다."));
         }
      }

      return true;
   }
}
