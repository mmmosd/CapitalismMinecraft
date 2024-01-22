package _.capitalismminecraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;

public class Shop {
    CapitalismMinecraft plugin = CapitalismMinecraft.getInstance();

    HashMap<Material, Integer> Item = new HashMap<Material, Integer>();

    HashMap<Material, Integer> SellData = new HashMap<Material, Integer>();
    HashMap<Material, Integer> BuyData = new HashMap<Material, Integer>();

    public void OpenWoodShopGUI() {
        //원목만
    }

    public void OpenMineralShopGUI() {
        //광물 블럭, 주괴, (구리, 철, 금 등 주괴로 만들 수 있는 것은 주괴로만, 섬손 금지)
        // 단, 네더라이트는 파편, 주괴 모두 가능
    }

    public void OpenFoodShopGUI() {
        //먹었을 때 효과가 부여되지 않는 것만 (황금사과류 제외)
    }

    public void OpenPotionShopGUI() {
        //포션 ("시간 + 레벨"에 따라 가격 책정), 인첸트북 ("인첸트 레벨 + 인첸트 개수" 에 따라 가격 책정)
    }

    public void ResetData() {
        SellData.clear();
        BuyData.clear();
    }

    public void Sell(Material category, int price) {
        if (SellData.get(category) != null) {
            SellData.put(category, SellData.get(category) + price);
        }
        else {
            SellData.put(category, price);
        }
    }

    public void Buy(Material category, int price) {
        if (BuyData.get(category) != null) {
            BuyData.put(category, BuyData.get(category) + price);
        }
        else {
            BuyData.put(category, price);
        }
    }

    public void ResetPrice() {

    }

    public int Set_items_price(int before_price) {
        int result_price = before_price;
        return result_price;
    }
}
